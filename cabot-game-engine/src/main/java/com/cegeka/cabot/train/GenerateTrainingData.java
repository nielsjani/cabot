package com.cegeka.cabot.train;

public class GenerateTrainingData {

    public static void main(String[] args) {
        new Trainer(new RandomMachineLearningInterface())
                .start(10);
    }
}
