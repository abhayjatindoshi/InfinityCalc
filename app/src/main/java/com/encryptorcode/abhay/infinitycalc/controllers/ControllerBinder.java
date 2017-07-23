package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.List;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ControllerBinder {
    public static String eval(String expression) throws IllegalExpressionException {
        String[] tokens = ExpressionTokenizer.tokenize(expression);
        Tag[] tags = ExpressionTagger.tag(tokens);
        List<TokenTag> list = ExpressionPostfixer.postfix(TokenTag.asList(tokens,tags));
        return ExpressionEvaluator.evaluate(list);
    }
}
