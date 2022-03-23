package com.wyyl1.geektimetdd.args;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser<T> implements OptionParser<T> {

    private T defaultValue;
    private Function<String, T> valueParser;

    public SingleValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        String flag = "-" + option.value();
        int index = arguments.indexOf(flag);
        if (index == -1) {
            return defaultValue;
        }
        if (index + 1 == arguments.size() ||
                arguments.get(index + 1).startsWith("-")) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (index + 2 < arguments.size() &&
                !arguments.get(index + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return valueParser.apply(arguments.get(index + 1));
    }

}
