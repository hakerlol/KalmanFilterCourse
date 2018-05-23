package com.Feller;

public class KalmanFilterSimple1D {
    private double X0;  // predicted state
    private double P0;  // predicted covariance

    private double F;  // factor of real value to previous real value
    private double Q;  // measurement noise
    private double H;  // factor of measured value to real value
    private double R;  // environment noise

    public double State;
    private double Covariance;

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
