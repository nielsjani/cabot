package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.TotalScores;
import com.cegeka.cabot.oorlogje.player.AIOorlogjePlayer;
import com.cegeka.cabot.oorlogje.player.OorlogjeAIGameStateConverter;
import com.cegeka.cabot.oorlogje.player.RandomOorlogjePlayer;

public class Battlefield {

    public static void main(String[] args) {
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
