package com.encryptorcode.abhay.infinitycalc.utils;

import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionEvaluator;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionOperations;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionPostfixer;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionTagger;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionTokenizer;
import com.encryptorcode.abhay.infinitycalc.exceptions.EmptyExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;
import com.encryptorcode.abhay.infinitycalc.models.Tag;
import com.encryptorcode.abhay.infinitycalc.models.TokenTag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ControllerBinder {

    public static String eval(String expression, int round, boolean infinityMode) throws IllegalExpressionException, LimitCrossedException, EmptyExpressionException {
        ExpressionOperations.setInfinityMode(infinityMode);
        String[] tokens = ExpressionTokenizer.tokenize(expression);
        Tag[] tags = ExpressionTagger.tag(tokens);
        List<TokenTag> list = ExpressionPostfixer.postfix(TokenTag.asList(tokens,tags));
        String result = ExpressionEvaluator.evaluate(list,round);
        result = removeZeros(result);
        return result;
    }

    private static String removeZeros(String number){
        if(number.contains(".")) {
            int index;
            for (index = number.length() - 1; index >= 0; index--) {
                if(number.charAt(index) == '.'){
                    index--;
                    break;
                } else if (number.charAt(index) != '0') {
                    break;
                }
            }
            return number.substring(0, index + 1);
        } else {
            return number;
        }
    }
}
