package com.wyyl1.geektimetdd.args.exceptions;

/**
 * @author aoe
 * @date 2022/3/23
 */
public class IllegalOptionException extends RuntimeException {

    private final String parameter;

    public IllegalOptionException(String parameter) {
        super(parameter);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}