package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.TotalScores;
import com.cegeka.cabot.oorlogje.player.*;

public class Battlefield {

    public static void main(String[] args) {
        // demoMachineLearning();
        // humanVSRandom();
         humanVSEnhanced();
    }

    private static void humanVSEnhanced() {
        System.out.println("Training Fase");
        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        QLearningSimplexAlgo qLearningSimplexAlgo = new QLearningSimplexAlgo();
        QLearningSimplexAlgo qLearningSimplexAlgoEnhanced = new QLearningSimplexAlgo();

        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgo = new AIOorlogjePlayer(qLearningSimplexAlgo, new OorlogjeAIGameStateConverter());
        AIOorlogjePlayer qLearningAlgoEnhanced = new AIOorlogjePlayer(qLearningSimplexAlgoEnhanced, new OorlogjeAIGameStateConverter());

        oorlogjeGameEngine
                .start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println();

        qLearningSimplexAlgoEnhanced.setInLearningMode(true);
        System.out.println();
        System.out.println("Enhanced Training");
        oorlogjeGameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);

        qLearningSimplexAlgoEnhanced.setInLearningMode(false);

        HumanOorlogjePlayer humanOorlogjePlayer = new HumanOorlogjePlayer();

        System.out.println();
        System.out.println("Enhanced versus HUMAN PLAYER!!!");
        System.out.println("GAME FASE");

        for (int i = 0; i < 5; i ++) {
            speelSpelletje(oorlogjeGameEngine, qLearningAlgoEnhanced, humanOorlogjePlayer);
        }
    }

    private static void speelSpelletje(OorlogjeGameEngine oorlogjeGameEngine, OorlogjePlayer aiPlayer, HumanOorlogjePlayer humanOorlogjePlayer) {
        TotalScores totalScores = oorlogjeGameEngine.start (0, humanOorlogjePlayer, aiPlayer);

        if (totalScores.player1AantalWinsBegint == 0 && totalScores.player1AantalWinsBegintNiet == 0) {
            System.out.println("You lost the game!");
        } else {
            System.out.println("You won the game!");
        }
    }

    private static void humanVSRandom() {
        HumanOorlogjePlayer humanOorlogjePlayer = new HumanOorlogjePlayer();
        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();

        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        System.out.println();
        System.out.println("Random versus HUMAN PLAYER!!!");
        System.out.println("Now it's your time to shine...");

        for (int i = 0; i < 5; i ++) {
            speelSpelletje(oorlogjeGameEngine, randomOorlogjePlayer, humanOorlogjePlayer);
        }
    }

    private static void demoMachineLearning() {
        System.out.println("Training Fase");

        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        QLearningSimplexAlgo qLearningSimplexAlgo = new QLearningSimplexAlgo();
        QLearningSimplexAlgo qLearningSimplexAlgoEnhanced = new QLearningSimplexAlgo();

        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgo = new AIOorlogjePlayer(qLearningSimplexAlgo, new OorlogjeAIGameStateConverter());
        AIOorlogjePlayer qLearningAlgoEnhanced = new AIOorlogjePlayer(qLearningSimplexAlgoEnhanced, new OorlogjeAIGameStateConverter());

        TotalScores totalScores = oorlogjeGameEngine
                .start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("For reals");
        qLearningSimplexAlgo.setInLearningMode(false);

        totalScores = oorlogjeGameEngine.start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("Twin fight!");
        totalScores = oorlogjeGameEngine.start(100000, qLearningAlgo, qLearningAlgo);

        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("Enhanced Training");
        totalScores = oorlogjeGameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);

        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);

        qLearningSimplexAlgoEnhanced.setInLearningMode(false);

        System.out.println();
        System.out.println("Enhanced Fight");
        totalScores = oorlogjeGameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);

        System.out.println("QLearning Enhanced # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("QLearning Enhanced # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("Enhanced Fight versus random");
        totalScores = oorlogjeGameEngine.start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);
    }
}
