package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;

/**
 * Created by abhay-5228 on 15/07/17.
 */

public class ExpressionIdentifier {


    public static final String add = "+";
    public static final String subtract = "-";
    public static final String multiply = "×";
    public static final String divide = "÷";
    public static final String modulus = "%";
    public static final String negate = "-";
    public static final String power = "^";
    public static final String percentage = "%";
    public static final String squareRoot = "√";
    public static final String factorial = "!";
    public static final String openBracketChar = "(";
    public static final String closeBracketChar = ")";


    private static String number = "0123456789";
    private static String decimalPoint = ".";
    private static String leftUnary =
                squareRoot+
                negate;
    private static String rightUnary =
                factorial+
                percentage;
    private static String binary =
                add+
                subtract+
                multiply+
                divide+
                modulus+
                power;
    private static String openBracket =
            openBracketChar;
    private static String closeBracket =
            closeBracketChar;

    public static boolean isNumber(char ch){
        return number.indexOf(ch) != -1;
    }

    public static boolean isDecimalPoint(char ch){
        return decimalPoint.indexOf(ch) != -1;
    }

    public static boolean isDecimalPoint(String str){
        return str.length() == 1 && isDecimalPoint(str.charAt(0));
    }

    public static boolean isNumber(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(!isNumber(chars[i])) return false;
        }
        return true;
    }

    public static boolean isDecimalNumber(String str){
        if(str.length() == 1) {
            return isNumber(str.charAt(0));
        }
        else {
            boolean decimalCheck = false;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if(isNumber(chars[i])) continue;
                if(isDecimalPoint(chars[i])){
                    if(decimalCheck) return false;
                    else decimalCheck = true;
                }
                if(i+1 == chars.length && isDecimalPoint(chars[i])) return false;
            }
        }
        return true;
    }

    public static boolean isLeftUnary(char ch){
        return leftUnary.indexOf(ch) != -1;
    }

    public static boolean isLeftUnary(String str) {
        return str.length() == 1 && isLeftUnary(str.charAt(0));
    }

    public static boolean isRightUnary(char ch){
        return rightUnary.indexOf(ch) != -1;
    }

    public static boolean isRightUnary(String str) {
        return str.length() == 1 && isRightUnary(str.charAt(0));
    }

    public static boolean isUnary(char ch){
        return isLeftUnary(ch) || isRightUnary(ch);
    }

    public static boolean isBinary(char ch){
        return binary.indexOf(ch) != -1;
    }

    public static boolean isBinary(String str){
        return str.length() == 1 && isBinary(str.charAt(0));
    }

    public static boolean isOperator(char ch){
        return isUnary(ch) || isBinary(ch);
    }

    public static boolean isOperator(String str){
        return str.length() == 1 && isOperator(str.charAt(0));
    }

    public static boolean isOpenBracket(char ch){
        return openBracket.indexOf(ch) != -1;
    }

    public static boolean isOpenBracket(String str){
        return str.length() == 1 && isOpenBracket(str.charAt(0));
    }

    public static boolean isCloseBracket(char ch){
        return closeBracket.indexOf(ch) != -1;
    }

    public static boolean isCloseBracket(String str){
        return str.length() == 1 && isCloseBracket(str.charAt(0));
    }

    public static boolean isBracket(char ch){
        return isOpenBracket(ch) || isCloseBracket(ch);
    }

    public static int priority(String str) throws IllegalExpressionException {
        switch (str){
            case ExpressionIdentifier.add:
            case ExpressionIdentifier.subtract:
                return 0;
            case ExpressionIdentifier.multiply:
            case ExpressionIdentifier.divide:
            case ExpressionIdentifier.modulus:
                return 1;
            case ExpressionIdentifier.power:
                return 2;
            default:
                throw new IllegalExpressionException("Priority not added for '"+str+"' token");
        }
    }
}
