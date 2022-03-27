package com.wyyl1.geektimetdd.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.wyyl1.geektimetdd.args.exceptions.*;

class ArgsTest {

    @Test
    void should_parse_multi_options(){
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging);
        assertEquals(8080, options.port);
        assertEquals("/usr/logs", options.directory);
    }

    record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    @Test
    void should_throw_illegal_option_exception_if_annotation_not_present(){
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () ->
                Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("port", e.getParameter());
    }

    record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
    }
}