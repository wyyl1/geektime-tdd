package com.wyyl1.geektimetdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            List<String> arguments = Arrays.stream(args).toList();
            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(it -> parseOption(arguments, it))
                    .toArray();

            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        return PARSERS.get(parameter.getType()).parse(arguments, "-" + parameter.getAnnotation(Option.class).value());
    }

    private static Map<Class<?>, OptionParser> PARSERS = Map.of(
            boolean.class, new BooleanOptionParser(),
            int.class, new SingleValueOptionParser<>(Integer::parseInt),
            String.class, new SingleValueOptionParser(String::valueOf));

}
