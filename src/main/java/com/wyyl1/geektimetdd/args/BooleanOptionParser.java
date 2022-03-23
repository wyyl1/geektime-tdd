package com.wyyl1.geektimetdd.args;

import java.util.List;

class BooleanOptionParser implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        String flag = "-" + option.value();
        int index = arguments.indexOf(flag);
        if (index + 1 < arguments.size() &&
                !arguments.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return index != -1;
    }
}
