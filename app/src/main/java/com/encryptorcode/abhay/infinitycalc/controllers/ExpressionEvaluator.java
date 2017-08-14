package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.EmptyExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ExpressionEvaluator {
    public static String evaluate(List<TokenTag> list,int round) throws LimitCrossedException, EmptyExpressionException {
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
                            number = leftUnaryOperation(number,unaryStack.pop(),round);
                        }
                        numberStack.add(number);
                    }
                    break;

                case LEFT_UNARY:
                    unaryStack.add(tokenTag.getToken());
                    break;

                case RIGHT_UNARY:
                    numberStack.add(rightUnaryOperation(numberStack.pop(),tokenTag.getToken(),round));
                    break;

                case BINARY:
                    numberStack.add(binaryOperation(numberStack.pop(),numberStack.pop(),tokenTag.getToken(),round));
                    break;
            }
        }
        try {
            return numberStack.pop();
        }catch (EmptyStackException e){
            throw new EmptyExpressionException("The expression is empty");
        }
    }

    static String leftUnaryOperation(String number, String operation, int round){
        switch(operation){
            case ExpressionIdentifier.squareRoot:
                return ExpressionOperations.squareRoot(number,round);
            case ExpressionIdentifier.negate:
                return ExpressionOperations.negate(number);
            default:
                return null;
        }
    }

    static String rightUnaryOperation(String number, String operation, int round) throws LimitCrossedException {
        switch (operation){
            case ExpressionIdentifier.factorial:
                return ExpressionOperations.factorial(number);
            case ExpressionIdentifier.percentage:
                return ExpressionOperations.percentage(number,round);
            default:
                return null;
        }
    }

    static String binaryOperation(String number2, String number1, String operation, int round) throws LimitCrossedException {
        switch(operation){
            case ExpressionIdentifier.add:
                return ExpressionOperations.add(number1,number2);
            case ExpressionIdentifier.subtract:
                return ExpressionOperations.subtract(number1,number2);
            case ExpressionIdentifier.multiply:
                return ExpressionOperations.multiply(number1,number2);
            case ExpressionIdentifier.divide:
                return ExpressionOperations.divide(number1,number2,round);
            case ExpressionIdentifier.modulus:
                return ExpressionOperations.modulus(number1,number2);
            case ExpressionIdentifier.power:
                return ExpressionOperations.power(number1,number2);
            default:
                return null;
        }
    }
}
