package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by abhay-5228 on 22/07/17.
 */

public class ExpressionPostfixer {
    public static List<TokenTag> postfix(List<TokenTag> list) throws IllegalExpressionException{
        List<TokenTag> postfix = new LinkedList<>();
        Stack<TokenTag> stack = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            switch(list.get(i).getTag()){
                case NUMBER:
                case LEFT_UNARY:
                case RIGHT_UNARY:
                    postfix.add(list.get(i));
                    break;
                case BINARY:
                    while(!stack.empty() && stack.lastElement().getTag() == Tag.BINARY && ExpressionIdentifier.priority(list.get(i).getToken()) <= ExpressionIdentifier.priority(stack.lastElement().getToken())){
                            postfix.add(stack.pop());
                    }
                    stack.add(list.get(i));
                    break;
                case OPEN_BRACKET:
                    stack.add(list.get(i));
                    break;
                case CLOSE_BRACKET:
                    while(stack.lastElement().getTag() != Tag.OPEN_BRACKET){
                        postfix.add(stack.pop());
                    }
                    stack.pop();
                    break;
            }
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            postfix.add(stack.get(i));
        }
        stack.empty();
        return postfix;
    }
}
