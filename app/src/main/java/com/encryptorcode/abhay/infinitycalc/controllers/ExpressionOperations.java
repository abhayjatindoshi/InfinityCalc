package com.encryptorcode.abhay.infinitycalc.controllers;

import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ExpressionOperations {

    private static final BigDecimal TWO = new BigDecimal("2");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final String EXPONENT_LIMIT_CROSSED_MESSAGE = "Limit for exponents is 3 digits.";
    private static final String NOT_DECIMAL_MESSAGE = "Decimal number not supported for exponent calculations";
    private static final String FACTORIAL_LIMIT_CROSSED_MESSAGE = "Limit for factorial is upto 1000";
    private static Boolean infinityMode = false;

    public static String add(String num1, String num2){
        return new BigDecimal(num1).add(new BigDecimal(num2)).toPlainString();
    }

    public static String subtract(String num1, String num2){
        return new BigDecimal(num1).subtract(new BigDecimal(num2)).toPlainString();
    }

    public static String multiply(String num1, String num2){
        return new BigDecimal(num1).multiply(new BigDecimal(num2)).toPlainString();
    }

    public static String divide(String num1, String num2, int decimalPlaces){
        return new BigDecimal(num1).divide(new BigDecimal(num2), decimalPlaces, RoundingMode.HALF_UP).toPlainString();
    }

    public static String modulus(String num1, String num2){
        return new BigDecimal(num1).remainder(new BigDecimal(num2)).toPlainString();
    }

    public static String negate(String num){
        if(num.charAt(0) == '-') return num.substring(1);
        else return '-'+num;
    }

    public static String power(String num1, String num2) throws LimitCrossedException {
        try {
            return power(num1, Integer.parseInt(num2));
        }catch (NumberFormatException e){
            if(num2.contains("."))
                throw new LimitCrossedException(NOT_DECIMAL_MESSAGE);
            else
                throw new LimitCrossedException(EXPONENT_LIMIT_CROSSED_MESSAGE);
        }
    }

    //limit num2 upto 3 digits
    public static String power(String num1, int num2) throws LimitCrossedException {
        if(!infinityMode) {
            if (num1.length() > 999)
                throw new LimitCrossedException(EXPONENT_LIMIT_CROSSED_MESSAGE);
            if (num2 > 1000) throw new LimitCrossedException(EXPONENT_LIMIT_CROSSED_MESSAGE);
        }
        return new BigDecimal(num1).pow(num2).toPlainString();
    }

    //limit 1000
    public static String factorial(String num) throws LimitCrossedException {
        if(!infinityMode) {
            if (num.length() > 5 || Integer.parseInt(num) / 12001 > 0)
                throw new LimitCrossedException(FACTORIAL_LIMIT_CROSSED_MESSAGE);
        }
        BigDecimal decimal = new BigDecimal(num);
        return fac(decimal,decimal).toPlainString();
    }

    public static String squareRoot(String num, int decimalPlaces) {
        BigDecimal A = new BigDecimal(num);
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, decimalPlaces, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, decimalPlaces, BigDecimal.ROUND_HALF_UP);

        }
        return x1.toPlainString();
    }

    public static String percentage(String num, int decimalPlaces){
        BigDecimal a = new BigDecimal(num);
        return a.divide(HUNDRED,decimalPlaces,RoundingMode.HALF_UP).toPlainString();
    }

    private static BigDecimal fac(BigDecimal n, BigDecimal acc) {
        if (n.equals(BigDecimal.ONE)) {
            return acc;
        }
        BigDecimal lessOne = n.subtract(BigDecimal.ONE);
        return fac(lessOne, acc.multiply(lessOne));
    }

    public static String toBaseCode(String num, int base) throws IllegalExpressionException {
        if(num.contains(".")){
            throw new IllegalExpressionException("cannot convert fractional number to base coded");
        }
        return new BigInteger(num).toString(base);
    }

    public static void setInfinityMode(Boolean infinityMode){
        ExpressionOperations.infinityMode = infinityMode;
    }

}
