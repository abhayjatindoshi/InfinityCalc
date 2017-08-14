package com.encryptorcode.abhay.infinitycalc.utils;

import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionEvaluator;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionPostfixer;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionTagger;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionTokenizer;
import com.encryptorcode.abhay.infinitycalc.exceptions.EmptyExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.List;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ControllerBinder {
    public static String eval(String expression, int round) throws IllegalExpressionException, LimitCrossedException, EmptyExpressionException {
        String[] tokens = ExpressionTokenizer.tokenize(expression);
        Tag[] tags = ExpressionTagger.tag(tokens);
        List<TokenTag> list = ExpressionPostfixer.postfix(TokenTag.asList(tokens,tags));
        return ExpressionEvaluator.evaluate(list,round);
    }
}
