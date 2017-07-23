package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.util.List;

/**
 * Created by abhay-5228 on 22/07/17.
 */

public class Main {
    public static void main(String[] args) throws IllegalExpressionException {
//        String tokens[] = ExpressionTokenizer.tokenize("1+2-2×10+(1+1)×3^2");
//        String tokens[] = ExpressionTokenizer.tokenize("1+2×3");
        String tokens[] = ExpressionTokenizer.tokenize("5!");
        Tag[] tags = ExpressionTagger.tag(tokens);
        List<TokenTag> list = TokenTag.asList(tokens,tags);
        List<TokenTag> postfix = ExpressionPostfixer.postfix(list);
        System.out.println();
        System.out.println("solution: "+ExpressionEvaluator.evaluate(postfix));
    }
}
