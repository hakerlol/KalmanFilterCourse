package com.company.com.company.KalmanFilter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class KalmanFilterSimple1D {
    public double X0;  // predicted state
    public double P0;  // predicted covariance

    public double F;  // factor of real value to previous real value
    public double Q;  // measurement noise
    public double H;  // factor of measured value to real value
    public double R;  // environment noise

    public double State;
    public double Covariance;

    public KalmanFilterSimple1D(double q, double r, double f, double h) {
        Q = q;
        R = r;
        F = f;
        H = h;
    }

    public void SetState(double state, double covariance) {
        State = state;
        Covariance = covariance;
    }

    public void Correct(double data) {
        //time update - prediction
        X0 = F * State;
        P0 = F * Covariance * F + Q;

        //measurement update - correction
        double K = H * P0 / (H * P0 * H + R);
        State = X0 + K * (data - H * X0);
        Covariance = (1 - K * H) * P0;
    }
}



