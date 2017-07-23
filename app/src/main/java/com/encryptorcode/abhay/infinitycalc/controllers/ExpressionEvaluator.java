package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.List;
import java.util.Stack;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ExpressionEvaluator {
    public static String evaluate(List<TokenTag> list){
        Stack<String> numberStack = new Stack<>();
        Stack<String> unaryStack = new Stack<>();
        for (TokenTag tokenTag : list) {
            switch(tokenTag.getTag()){
                case NUMBER:
                    if(unaryStack.empty())
                        numberStack.add(tokenTag.getToken());
                    else{
                        String number = tokenTag.getToken();
                        while(!unaryStack.empty()){
                            number = leftUnaryOperation(number,unaryStack.pop());
                        }
                        numberStack.add(number);
                    }
                    break;

                case LEFT_UNARY:
                    unaryStack.add(tokenTag.getToken());
                    break;

                case RIGHT_UNARY:
                    if(tokenTag.getToken().equals("%"))
                        numberStack.add(rightUnaryOperation(numberStack.pop(),numberStack.peek(),tokenTag.getToken()));
                    else
                        numberStack.add(rightUnaryOperation(numberStack.pop(),null,tokenTag.getToken()));
                    break;

                case BINARY:
                    numberStack.add(binaryOperation(numberStack.pop(),numberStack.pop(),tokenTag.getToken()));
                    break;
            }
        }
        return numberStack.pop();
    }

    static String leftUnaryOperation(String number, String operation){
        switch(operation){
            case ExpressionIdentifier.squareRoot:
                return ExpressionOperations.squareRoot(number);
            case ExpressionIdentifier.negate:
                return ExpressionOperations.negate(number);
            default:
                return null;
        }
    }

    static String rightUnaryOperation(String number, String totalSum, String operation){
        switch (operation){
            case ExpressionIdentifier.factorial:
                return ExpressionOperations.factorial(number);
            case ExpressionIdentifier.percentage:
                return ExpressionOperations.percentage(totalSum,number);
            default:
                return null;
        }
    }

    static String binaryOperation(String number2, String number1, String operation){
        switch(operation){
            case ExpressionIdentifier.add:
                return ExpressionOperations.add(number1,number2);
            case ExpressionIdentifier.subtract:
                return ExpressionOperations.subtract(number1,number2);
            case ExpressionIdentifier.multiply:
                return ExpressionOperations.multiply(number1,number2);
            case ExpressionIdentifier.divide:
                return ExpressionOperations.divide(number1,number2);
            case ExpressionIdentifier.modulus:
                return ExpressionOperations.modulus(number1,number2);
            case ExpressionIdentifier.power:
                return ExpressionOperations.power(number1,Integer.valueOf(number2));
            default:
                return null;
        }
    }
}
