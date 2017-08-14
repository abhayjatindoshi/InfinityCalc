package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abhay-5228 on 15/07/17.
 */

public class ExpressionTokenizer {


    private static final String NO_NUMBER_AFTER_DECIMAL_POINT = "No number found after a decimal point";

    public static String[] tokenize(String expression) throws IllegalExpressionException {
        return tokenizeLevelTwo(tokenizeLevelOne(expression));
    }

    //identifies numbers
    private static List<String> tokenizeLevelOne(String expression){
        List<String> tokens = new LinkedList<>();
        char[] chars = expression.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if(ExpressionIdentifier.isNumber(chars[i])){
                do {
                    builder.append(chars[i]);
                }while(++i < chars.length && ExpressionIdentifier.isNumber(chars[i]));
                tokens.add(builder.toString());
                builder = new StringBuilder();
                i--;
            } else {
                tokens.add(String.valueOf(chars[i]));
            }
        }
        return tokens;
    }

    //identifies decimal points
    private static String[] tokenizeLevelTwo(List<String> l1Tokens) throws IllegalExpressionException {
        List<String> tokens = new LinkedList<>();
        for (int i = 0; i < l1Tokens.size(); i++) {
            if (ExpressionIdentifier.isNumber(l1Tokens.get(i)))
                tokens.add(l1Tokens.get(i));
            else if (ExpressionIdentifier.isDecimalPoint(l1Tokens.get(i))) {
                if (i+1 >= l1Tokens.size() || !ExpressionIdentifier.isNumber(l1Tokens.get(i + 1)))
                    throw new IllegalExpressionException(NO_NUMBER_AFTER_DECIMAL_POINT, i);
                String token = l1Tokens.get(i)+l1Tokens.get(i+1);
                if (ExpressionIdentifier.isNumber(tokens.get(tokens.size() - 1))){
                    token = tokens.get(tokens.size()-1)+token;
                    tokens.remove(tokens.size()-1);
                }
                tokens.add(token);
                i++;
            } else {
                tokens.add(l1Tokens.get(i));
            }
        }
        return tokens.toArray(new String[tokens.size()]);
    }



//    public static void main(String[] args) throws IllegalExpressionException {
////        String expression = "2131.23+1.234-0+(12)+34.1-1435÷214";
////        String expression = "1+2-3--√4+5+√5!)";
//        String expression = "(5)";
//        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression);
//        List<String> tokens = tokenizer.tokens;
//        Tag[] tags = tokenizer.tags;
//        for (int i = 0; i < tokens.size(); i++) {
//            System.out.println(tags[i]+" -> "+tokens.get(i));
//        }
//    }


}
