package com.wyyl1.geektimetdd.args;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser<T> implements OptionParser {

    Function<String, T> valueParser;

    public SingleValueOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, String flag) {
        int index = arguments.indexOf(flag);
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

}
