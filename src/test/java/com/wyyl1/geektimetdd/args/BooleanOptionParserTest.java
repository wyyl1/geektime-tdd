package com.wyyl1.geektimetdd.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class BooleanOptionParserTest {

    @ParameterizedTest
    @CsvSource(value = {"-l t", "-l t f"})
    void should_not_accept_extra_arguments_for_boolean_option(String input){
        List<String> arguments = Arrays.stream(input.split(" ")).toList();
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(arguments, option("l")));
        assertEquals("l", e.getOption());
    }

    @Test
    void should_set_default_value_to_false_if_option_not_present(){
        assertFalse(new BooleanOptionParser().parse(asList(), option("l")));
    }

    @Test
    void should_set_default_value_to_true_if_option_present(){
        assertTrue(new BooleanOptionParser().parse(asList("-l"), option("l")));
    }

    static Option option(String value){
        return new Option(){

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}