package com.cegeka.cabot.train;

import com.cegeka.cabot.algorithm.baseline.BaseLineAlgo;
import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.SimplexAlgoAdapter;
import org.apache.commons.lang3.tuple.Pair;

public class GenerateTrainingData {

    public static void main(String[] args) {
        System.out.println("Training Fase");

        BaseLineAlgo baseLineAlgo = new BaseLineAlgo();
        QLearningSimplexAlgo qLearningSimplexAlgo = new QLearningSimplexAlgo();
        SimplexAlgoAdapter qLearningAlgo = new SimplexAlgoAdapter(qLearningSimplexAlgo);

        Pair<Integer, Integer> totalScores = new Trainer(baseLineAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("Player 1: " + totalScores.getLeft());
        System.out.println("Player 2: " + totalScores.getRight());

        System.out.println();
        System.out.println("For reals");
        qLearningSimplexAlgo.setInLearningMode(false);

        totalScores = new Trainer(baseLineAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("Player 1: " + totalScores.getLeft());
        System.out.println("Player 2: " + totalScores.getRight());
    }
}
