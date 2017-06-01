package com.cegeka.cabot.train;

import com.cegeka.cabot.algorithm.baseline.BaseLineAlgo;
import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.SimplexAlgoAdapter;

public class GenerateTrainingData {

    public static void main(String[] args) {
        System.out.println("Training Fase");

        BaseLineAlgo baseLineAlgo = new BaseLineAlgo();
        QLearningSimplexAlgo qLearningSimplexAlgo = new QLearningSimplexAlgo();
        QLearningSimplexAlgo qLearningSimplexAlgoEnhanced = new QLearningSimplexAlgo();
        SimplexAlgoAdapter qLearningAlgo = new SimplexAlgoAdapter(qLearningSimplexAlgo);
        SimplexAlgoAdapter qLearningAlgoEnhanced = new SimplexAlgoAdapter(qLearningSimplexAlgoEnhanced);

        TotalScores totalScores = new Trainer(baseLineAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("For reals");
        qLearningSimplexAlgo.setInLearningMode(false);

        totalScores = new Trainer(baseLineAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("Twin fight!");
        totalScores = new Trainer(qLearningAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("Enhanced Training");
        totalScores = new Trainer(qLearningAlgoEnhanced, qLearningAlgo)
                .start(100000);

        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);

        qLearningSimplexAlgoEnhanced.setInLearningMode(false);

        System.out.println();
        System.out.println("Enhanced Fight");
        totalScores = new Trainer(qLearningAlgoEnhanced, qLearningAlgo)
                .start(100000);

        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);


        System.out.println();
        System.out.println("Enhanced Fight versus random");
        totalScores = new Trainer(baseLineAlgo, qLearningAlgo)
                .start(100000);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

    }
}
