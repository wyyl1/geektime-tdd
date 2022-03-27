package com.wyyl1.geektimetdd.args.exceptions;

/**
 * @author aoe
 * @date 2022/3/23
 */
public class InsufficientArgumentsException extends RuntimeException {

    private final String option;

    public InsufficientArgumentsException(String option) {
        super(option);
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}