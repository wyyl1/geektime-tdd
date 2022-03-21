package com.wyyl1.geektimetdd.args;

import java.util.List;

interface OptionParser {
    Object parse(List<String> arguments, String flag);
}
