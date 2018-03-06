package com.company.com.company.KalmanFilter;

public class SimpleMovingAverage {

    public double[] Smooth(double[] input, int window) {
        double[] output = new double[input.length];
        output[0] = input[0];
        if (window == 3) {
            for (int i = 1; i < input.length - 2; i++) {
                output[i] = (input[i - 1] + input[i] + input[i + 1]) / window;
            }
        } else if (window == 5) {
            output[1] = (input[0] + input[1] + input[2]) / 3;
            for (int i = 2; i < input.length - 2; i++) {
                output[i] = (input[i - 2] + input[i - 1] + input[i]
                        + input[i + 1] + input[i + 2]) / window;
            }
            output[input.length - 2] = (input[input.length - 3] + input[input.length - 2] + input[input.length - 1]) / 3;
        } else if (window == 7) {
            output[1] = (input[0] + input[1] + input[2]) / 3;
            output[2] = (input[0] + input[1] + input[2] + input[3] + input[4]) / 5;
            for (int i = 3; i < input.length - 3; i++) {
                output[i] = (input[i - 3] + input[i - 2] + input[i - 1] + input[i]
                        + input[i + 1] + input[i + 2] + input[i + 3]) / window;
            }
            output[input.length - 3] = (input[input.length - 5] + input[input.length - 4] + input[input.length - 3] + input[input.length - 2] + input[input.length - 1]) / 5;
            output[input.length - 2] = (input[input.length - 3] + input[input.length - 2] + input[input.length - 1]) / 3;
        }

        output[input.length - 1] = input[input.length - 1];
        return output;
    }

}
