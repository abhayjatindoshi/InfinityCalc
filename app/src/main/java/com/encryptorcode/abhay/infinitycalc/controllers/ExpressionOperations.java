package com.encryptorcode.abhay.infinitycalc.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by abhay-5228 on 23/07/17.
 */

public class ExpressionOperations {

    private static final BigDecimal TWO = new BigDecimal("2");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final int DECIAMAL_PLACES = 2;

    public static String add(String num1, String num2){
        return new BigDecimal(num1).add(new BigDecimal(num2)).toString();
    }

    public static String subtract(String num1, String num2){
        return new BigDecimal(num1).subtract(new BigDecimal(num2)).toString();
    }

    public static String multiply(String num1, String num2){
        return new BigDecimal(num1).multiply(new BigDecimal(num2)).toString();
    }

    public static String divide(String num1, String num2){
        return divide(num1, num2, DECIAMAL_PLACES);
    }

    public static String divide(String num1, String num2, int decimalPlaces){
        return new BigDecimal(num1).divide(new BigDecimal(num2), decimalPlaces, RoundingMode.HALF_UP).toString();
    }

    public static String modulus(String num1, String num2){
        return new BigDecimal(num1).remainder(new BigDecimal(num2)).toString();
    }

    public static String negate(String num){
        if(num.charAt(0) == '-') return num.substring(1);
        else return '-'+num;
    }

    //limit num2 upto 5 digits
    public static String power(String num1, int num2){
        return new BigDecimal(num1).pow(num2).toString();
    }

    //limit 12000
    public static String factorial(String num){
        BigDecimal decimal = new BigDecimal(num);
        return fac(decimal,decimal).toString();
    }

    public static String squareRoot(String num){
        return squareRoot(num,DECIAMAL_PLACES);
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
        return x1.toString();
    }

    public static String percentage(String num1, String num2){
        return percentage(num1,num2,DECIAMAL_PLACES);
    }

    public static String percentage(String num1, String num2, int decimalPlaces){
        BigDecimal one = new BigDecimal(num1);
        BigDecimal two = new BigDecimal(num2);
        return one.multiply(two).divide(HUNDRED,decimalPlaces,RoundingMode.HALF_UP).toString();
    }

//    public static void main(String[] args) {
//        System.out.println(percentage("1000","300",0));
//    }

    private static BigDecimal fac(BigDecimal n, BigDecimal acc) {
        if (n.equals(BigDecimal.ONE)) {
            return acc;
        }
        BigDecimal lessOne = n.subtract(BigDecimal.ONE);
        return fac(lessOne, acc.multiply(lessOne));
    }

}
