package com.encryptorcode.abhay.infinitycalc.exceptions;

/**
 * Created by abhay-5228 on 15/07/17.
 */

public class IllegalExpressionException extends Exception {

    private String message;
    private int position;

    public IllegalExpressionException(int position) {
        this("",position);
    }

    public IllegalExpressionException(String message){
        this(message,-1);
    }

    public IllegalExpressionException(String message, int position) {
        this.message = message;
        this.position = position;
    }

    @Override
    public String getMessage() {
        return "["+position+"] "+message;
    }

    public int getPosition(){
        return position;
    }

    @Override
    public String toString() {
        return "["+position+"] "+message;
    }
}
