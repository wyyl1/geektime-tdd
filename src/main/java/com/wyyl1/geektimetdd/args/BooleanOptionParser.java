package com.wyyl1.geektimetdd.args;

import java.util.List;

class BooleanOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> arguments, String flag) {
        return arguments.contains(flag);
    }
}
