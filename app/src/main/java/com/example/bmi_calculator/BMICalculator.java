package com.example.bmi_calculator;

public class BMICalculator {

    public static double calculate(float weight, float height){
        return weight / Math.pow(height / 100, 2);
    }

}
