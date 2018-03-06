package com.company;

import com.company.com.company.KalmanFilter.Reader;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader("Фильтр");
        reader.pack();
        reader.setVisible(true);
        reader.setSize(500, 300);
        reader.setResizable(false);
        reader.setLocationRelativeTo(null);
    }
}
