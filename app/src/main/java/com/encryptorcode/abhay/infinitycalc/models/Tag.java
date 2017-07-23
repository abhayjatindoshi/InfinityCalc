package com.encryptorcode.abhay.infinitycalc.models;

/**
 * Created by abhay-5228 on 22/07/17.
 */

public enum Tag{
    FIRST(-2,"First"),
    LAST(-1,"Last"),
    NUMBER(0, "Number"),
    LEFT_UNARY(1, "Left unary"),
    RIGHT_UNARY(2, "Right unary"),
    BINARY(3, "Binary"),
    OPEN_BRACKET(4, "Open bracket"),
    CLOSE_BRACKET(5, "Close bracket"),
    OPERATOR(6, "Operator");

    int index;
    String tag;

    Tag(int index, String tag){
        this.index = index;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return tag;
    }
}
