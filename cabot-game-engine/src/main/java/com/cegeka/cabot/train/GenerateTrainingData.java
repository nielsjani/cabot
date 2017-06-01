package com.cegeka.cabot.train;

import com.cegeka.cabot.algorithm.baseline.BaseLineAlgo;

public class GenerateTrainingData {

    public static void main(String[] args) {
        new Trainer(new BaseLineAlgo())
                .start(1);
    }
}
