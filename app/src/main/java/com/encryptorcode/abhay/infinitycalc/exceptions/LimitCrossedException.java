package com.encryptorcode.abhay.infinitycalc.exceptions;

/**
 * Created by abhay-5228 on 05/08/17.
 */

public class LimitCrossedException extends Exception {
    private String message;
    private int position;

    public LimitCrossedException(int position) {
        this("",position);
    }

    public LimitCrossedException(String message){
        this(message,-1);
    }

    public LimitCrossedException(String message, int position) {
        this.message = message;
        this.position = position;
    }

    @Override
    public String getMessage() {
        return (position == -1 ? "" : "["+position+"] ")+message;
    }

    public int getPosition(){
        return position;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
