package com.wyyl1.geektimetdd.args;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.wyyl1.geektimetdd.args.OptionParsersTest.BooleanOptionParser.option;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import com.wyyl1.geektimetdd.args.exceptions.*;

class OptionParsersTest {

    @Nested
    class UnaryOptionParser {
        @Test
        void should_not_accept_extra_argument_for_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> OptionParsers.unary(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("p")));
            assertEquals("p", e.getOption());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-p -l", "-p"})
        void should_not_accept_insufficient_argument_for_single_valued_option(String input) {
            List<String> arguments = asList(input.split(" "));
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                    () -> OptionParsers.unary(0, Integer::parseInt).parse(arguments, option("p")));
            assertEquals("p", e.getOption());
        }

        @Test
        void should_set_default_value_to_0_for_single_option() {
            Function<String, Object> whatever = (it) -> null;
            Object defaultValue = new Object();

            assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(asList(), option("p")));
        }

        @Test
        void should_not_accept_extra_argument_for_string_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
                    OptionParsers.unary("", String::valueOf).parse(asList("-d", "/usr/logs", "/usr/vars"), option("d")));
            assertEquals("d", e.getOption());
        }

        @Test
        void should_parse_value_if_flag_present() {
            Object parsed = new Object();
            Function<String, Object> parse = (it) -> parsed;
            Object whatever = new Object();
            assertSame(parsed, OptionParsers.unary(whatever, parse).parse(asList("-p", "8080"), option("p")));
        }
    }

    @Nested
    class BooleanOptionParser {

        @ParameterizedTest
        @CsvSource(value = {"-l t", "-l t f"})
        void should_not_accept_extra_arguments_for_boolean_option(String input){
            List<String> arguments = Arrays.stream(input.split(" ")).toList();
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(arguments, option("l")));
            assertEquals("l", e.getOption());
        }

        @Test
        void should_set_default_value_to_false_if_option_not_present(){
            assertFalse(OptionParsers.bool().parse(asList(), option("l")));
        }

        @Test
        void should_set_default_value_to_true_if_option_present(){
            assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
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

    @Nested
    class ListOptionParser{

        @Test
        void should_parse_list_value(){
            String[] value = OptionParsers.list(String::valueOf)
                    .parse(asList("-g", "this", "is"), option("g"));
            assertEquals(new String[]{"this", "is"}, value);
        }
    }
}