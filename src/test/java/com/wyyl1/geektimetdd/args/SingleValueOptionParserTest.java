package com.wyyl1.geektimetdd.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.function.Function;

import static com.wyyl1.geektimetdd.args.BooleanOptionParserTest.option;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class SingleValueOptionParserTest {

    @Test
    void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p")));
        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    void should_not_accept_insufficient_argument_for_single_valued_option(String input){
        List<String> arguments = asList(input.split(" "));
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValueOptionParser<>(0, Integer::parseInt).parse(arguments, option("p")));
        assertEquals("p", e.getOption());
    }

    @Test
    void should_set_default_value_to_0_for_single_option(){
        Function<String, Object> whatever = (it) -> null;
        Object defaultValue = new Object();

        assertSame(defaultValue, new SingleValueOptionParser<>(defaultValue, whatever).parse(asList(), option("p")));
    }

    @Test
    void should_not_accept_extra_argument_for_string_single_valued_option(){
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                new SingleValueOptionParser<>("", String::valueOf).parse(asList("-d", "/usr/logs", "/usr/vars"), option("d")));
        assertEquals("d", e.getOption());
    }

    @Test
    void should_parse_value_if_flag_present(){
        Object parsed = new Object();
        Function<String, Object> parse = (it) -> parsed;
        Object whatever = new Object();
        assertSame(parsed, new SingleValueOptionParser<>(whatever, parse).parse(asList("-p", "8080"), option("p")));
    }
}