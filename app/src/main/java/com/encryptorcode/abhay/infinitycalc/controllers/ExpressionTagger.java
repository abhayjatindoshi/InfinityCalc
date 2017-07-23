package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;

/**
 * Created by abhay-5228 on 22/07/17.
 */

public class ExpressionTagger {

    private static final String UNKNOWN_TOKEN_FOUND = "Unknown token found";
    private static final String OPERATOR_NOT_IN_VALID_POSITION = "Operator is not in a valid position";
    private static final String TAG_VERIFICATION_FAILED = "Something is placed in an invalid position";
    private static final String INVALID_BRACKETS = "Please check the brackets";

    // Order -> number, left unary, right unary, binary, open bracket, close bracket
    private static final boolean[][] left={
            {false,true,false,true,true,false},
            {false,true,false,true,true,false},
            {true,false,true,false,false,true},
            {true,false,true,false,false,true},
            {false,true,false,true,true,false},
            {true,false,true,false,false,true}
    };

    //added right operator as default true
    private static final boolean[][] right={
            {false,false,true,true,false,true,true},
            {true,true,false,false,true,false,true},
            {false,false,true,true,false,true,true},
            {true,true,false,false,true,false,true},
            {true,false,true,false,true,false,true},
            {false,false,true,true,false,true,true}
    };
    private static final boolean[] first={
            true,
            true,
            false,
            false,
            true,
            false
    };
    private static final boolean[] last={
            true,
            false,
            true,
            false,
            false,
            true
    };
    
    public static Tag[] tag(String[] tokens) throws IllegalExpressionException {
        Tag[] tags = identifyTokens(tokens);
        identifyOperators(tokens,tags);
        verifyTags(tags);
        verifyBrackets(tags);
        return tags;
    }

    //identifies each tokens and returns tags
    private static Tag[] identifyTokens(String[] tokens) throws IllegalExpressionException {
        Tag[] tags = new Tag[tokens.length];
        for(int i = 0; i < tokens.length ; i++){
            if(ExpressionIdentifier.isDecimalNumber(tokens[i]))
                tags[i] = Tag.NUMBER;
            else if (ExpressionIdentifier.isOpenBracket(tokens[i]))
                tags[i] = Tag.OPEN_BRACKET;
            else if(ExpressionIdentifier.isCloseBracket(tokens[i]))
                tags[i] = Tag.CLOSE_BRACKET;
            else if(ExpressionIdentifier.isOperator(tokens[i]))
                tags[i] = Tag.OPERATOR;
            else
                throw new IllegalExpressionException(UNKNOWN_TOKEN_FOUND+" '"+tokens[i]+'\'',i);
        }
        return tags;
    }

    //changes all operator tags to thier respective operator tags
    private static void identifyOperators(String[] tokens, Tag[] tags) throws IllegalExpressionException {
        for (int i = 0; i < tokens.length; i++) {
            if(tags[i] == Tag.OPERATOR) {
                if(i == 0)
                    tags[i] = getOperatorTag(Tag.FIRST,tags[i+1],tokens[i]);
                else if(i == tokens.length-1)
                    tags[i] = getOperatorTag(tags[i-1],Tag.LAST,tokens[i]);
                else
                    tags[i] = getOperatorTag(tags[i-1],tags[i+1],tokens[i]);
            }
        }
    }

    private static void verifyTags(Tag[] tags) throws IllegalExpressionException {
        if(tags.length == 1) return;
        for (int i = 0; i < tags.length; i++) {
            if(i == 0){
                if(!verify(Tag.FIRST,tags[i],tags[i+1]))
                    throw new IllegalExpressionException(TAG_VERIFICATION_FAILED,i);
            } else if(i == tags.length-1){
                if(!verify(tags[i-1],tags[i],Tag.LAST))
                    throw new IllegalExpressionException(TAG_VERIFICATION_FAILED,i);
            } else {
                if(!verify(tags[i-1],tags[i],tags[i+1]))
                    throw new IllegalExpressionException(TAG_VERIFICATION_FAILED,i);
            }
        }
    }

    private static void verifyBrackets(Tag[] tags) throws IllegalExpressionException{
        int count = 0;
        for (Tag tag : tags) {
            if(count < 0) throw new IllegalExpressionException(INVALID_BRACKETS);
            if(tag == Tag.OPEN_BRACKET) count++;
            else if(tag == Tag.CLOSE_BRACKET) count--;
        }
        if(count != 0) throw new IllegalExpressionException(INVALID_BRACKETS);
    }

    private static boolean verify(Tag left, Tag me, Tag right){
        boolean[] inLeft = ExpressionTagger.left[me.getIndex()];
        boolean[] inRight = ExpressionTagger.right[me.getIndex()];

        if(left == Tag.FIRST)
            return first[me.getIndex()] && inRight[right.getIndex()];
        if(right == Tag.LAST)
            return last[me.getIndex()] &&
                    inLeft[left.getIndex()];

        return inLeft[left.getIndex()] && inRight[right.getIndex()];
    }

    private static Tag getOperatorTag(Tag left, Tag right, String token) throws IllegalExpressionException {
        if(ExpressionIdentifier.isBinary(token) && verify(left,Tag.BINARY,right)) return Tag.BINARY;
        if(ExpressionIdentifier.isLeftUnary(token) && verify(left,Tag.LEFT_UNARY,right)) return Tag.LEFT_UNARY;
        if(ExpressionIdentifier.isRightUnary(token) && verify(left,Tag.RIGHT_UNARY,right)) return Tag.RIGHT_UNARY;
        throw new IllegalExpressionException(OPERATOR_NOT_IN_VALID_POSITION);
    }

    
}
