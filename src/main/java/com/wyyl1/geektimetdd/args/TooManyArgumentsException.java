package com.wyyl1.geektimetdd.args;

/**
 * @author aoe
 * @date 2022/3/23
 */
public class TooManyArgumentsException extends RuntimeException {

    private final String option;

    public TooManyArgumentsException(String option) {
        super(option);
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
