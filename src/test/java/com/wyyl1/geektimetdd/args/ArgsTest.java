package com.wyyl1.geektimetdd.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

    @Test
    void should_set_boolean_option_to_return_true_if_flag_present(){
        BooleanOption option = Args.parse(BooleanOption.class, "-l");

        assertTrue(option.logging);
    }

    @Test
    void should_set_boolean_option_to_return_false_if_flag_not_present(){
        BooleanOption option = Args.parse(BooleanOption.class);

        assertFalse(option.logging);
    }

    record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port);
    }

    record IntOption(@Option("p") int port) {
    }

    @Test
    void should_parse_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory);
    }

    record StringOption(@Option("d") String directory) {

    }

    @Test
    void should_parse_multi_options(){
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging);
        assertEquals(8080, options.port);
        assertEquals("/usr/logs", options.directory);
    }

    record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }
}