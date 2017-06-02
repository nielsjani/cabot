package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.api.TotalScores;
import com.cegeka.cabot.oorlogje.player.AIOorlogjePlayer;
import com.cegeka.cabot.oorlogje.player.HumanOorlogjePlayer;
import com.cegeka.cabot.oorlogje.player.RandomOorlogjePlayer;

public class Battlefield {

    public static void main(String[] args) {
//         demoMachineLearning();
        // humanVSRandom();
        humanVSEnhanced();
//        trainMe(new AIOorlogjePlayer("TrainedBot"));
    }

    private static void humanVSEnhanced() {
        System.out.println("Training Fase");
        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgo = new AIOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgoEnhanced = new AIOorlogjePlayer();

        oorlogjeGameEngine
                .start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println();

        qLearningAlgoEnhanced.setInLearningMode(true);
        System.out.println();
        System.out.println("Enhanced Training");
        oorlogjeGameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);

        qLearningAlgoEnhanced.setInLearningMode(false);

        HumanOorlogjePlayer humanOorlogjePlayer = new HumanOorlogjePlayer();

        System.out.println();
        System.out.println("Enhanced versus HUMAN PLAYER!!!");
        System.out.println("GAME FASE");

        for (int i = 0; i < 5; i ++) {
            speelSpelletje(oorlogjeGameEngine, qLearningAlgoEnhanced, humanOorlogjePlayer);
        }
    }

    private static void speelSpelletje(OorlogjeGameEngine oorlogjeGameEngine, AIOorlogjePlayer qLearningAlgoEnhanced, HumanOorlogjePlayer humanOorlogjePlayer) {
        TotalScores totalScores = oorlogjeGameEngine.start (0, humanOorlogjePlayer, qLearningAlgoEnhanced);

        if (totalScores.player1AantalWinsBegint == 0 && totalScores.player1AantalWinsBegintNiet == 0) {
            System.out.println("You lost the game!");
        } else {
            System.out.println("You won the game!");
        }
    }

    private static void humanVSRandom() {
        HumanOorlogjePlayer humanOorlogjePlayer = new HumanOorlogjePlayer();
        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();

        System.out.println();
        System.out.println("Random versus HUMAN PLAYER!!!");
        System.out.println("Now it's your time to shine...");
        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();
        TotalScores totalScores = oorlogjeGameEngine.start (1, humanOorlogjePlayer, randomOorlogjePlayer);

        System.out.println("Human # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("Random # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Human # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("Random # wins (begint): " + totalScores.player2AantalWinsBegint);
    }

    private static void demoMachineLearning() {
        System.out.println("Training Fase");

        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgo = new AIOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgoEnhanced = new AIOorlogjePlayer();

        TotalScores totalScores = oorlogjeGameEngine
                .start(100000, randomOorlogjePlayer, qLearningAlgo);

        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

        System.out.println();
        System.out.println("For reals");
        qLearningAlgo.setInLearningMode(false);

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

        qLearningAlgoEnhanced.setInLearningMode(false);

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

    public static void trainMe(AIOorlogjePlayer aiOorlogjePlayer) {
        aiOorlogjePlayer.setInLearningMode(true);

        OorlogjeGameEngine oorlogjeGameEngine = new OorlogjeGameEngine();

        RandomOorlogjePlayer randomOorlogjePlayer = new RandomOorlogjePlayer();
        AIOorlogjePlayer qLearningAlgo = new AIOorlogjePlayer();

        oorlogjeGameEngine
                .start(100000, randomOorlogjePlayer, qLearningAlgo);

        qLearningAlgo.setInLearningMode(false);

//        oorlogjeGameEngine.start(100000, randomOorlogjePlayer, aiOorlogjePlayer);
        oorlogjeGameEngine.start(100000, qLearningAlgo, aiOorlogjePlayer);

        aiOorlogjePlayer.setInLearningMode(false);

        TotalScores totalScores = oorlogjeGameEngine.start(100000, randomOorlogjePlayer, aiOorlogjePlayer);
        totalScores.print();
        totalScores = oorlogjeGameEngine.start(100000, qLearningAlgo, aiOorlogjePlayer);
        totalScores.print();

        System.out.println("Training player " + aiOorlogjePlayer.name());
        System.out.println("Score tegen " + randomOorlogjePlayer.name() + ":");
        System.out.println("Score tegen " + qLearningAlgo.name() + ":");
    }

}
