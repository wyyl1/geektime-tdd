package com.wyyl1.geektimetdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

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
        Option option = parameter.getAnnotation(Option.class);
        Object value = null;

        String flag = "-" + option.value();
        if (parameter.getType() == boolean.class) {
            value = arguments.contains(flag);
        }
        if (parameter.getType() == int.class) {
            int index = arguments.indexOf(flag);
            value = Integer.parseInt(arguments.get(index + 1));
        }
        if (parameter.getType() == String.class) {
            int index = arguments.indexOf(flag);
            value = arguments.get(index + 1);
        }
        return value;
    }
}
