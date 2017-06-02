package com.cegeka.cabot.tictactoe;

import com.cegeka.cabot.api.TotalScores;
import com.cegeka.cabot.tictactoe.player.QLearningTicTacToePlayer;
import com.cegeka.cabot.tictactoe.player.RandomTicTacToePlayer;

public class TicTacToeBattlefield {

    public static void main(String[] args) {
        System.out.println("Training Fase");

        TicTacToeGameEngine gameEngine = new TicTacToeGameEngine();

        RandomTicTacToePlayer randomTicTacToePlayer = new RandomTicTacToePlayer();
        QLearningTicTacToePlayer qLearningAlgo = new QLearningTicTacToePlayer();
        QLearningTicTacToePlayer qLearningAlgoEnhanced = new QLearningTicTacToePlayer();

        TotalScores totalScores = gameEngine
                .start(100000, randomTicTacToePlayer, qLearningAlgo);

        totalScores.print();

        System.out.println();
        System.out.println("For reals");
        qLearningAlgo.setInLearningMode(false);

        totalScores = gameEngine.start(1000, randomTicTacToePlayer, qLearningAlgo);

        totalScores.print();

//        System.out.println();
//        System.out.println("Twin fight!");
//        totalScores = gameEngine.start(100000, qLearningAlgo, qLearningAlgo);
//
//        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
//        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
//        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
//        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);
//
//        System.out.println();
//        System.out.println("Enhanced Training");
//        totalScores = gameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);
//
//        System.out.println("QLearning 1 # wins (begint): " + totalScores.player1AantalWinsBegint);
//        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
//        System.out.println("QLearning 1 # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
//        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);
//
//        qLearningAlgoEnhanced.setInLearningMode(false);
//
//        System.out.println();
//        System.out.println("Enhanced Fight");
//        totalScores = gameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);
//
//        System.out.println("QLearning Enhanced # wins (begint): " + totalScores.player1AantalWinsBegint);
//        System.out.println("QLearning 2 # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
//        System.out.println("QLearning Enhanced # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
//        System.out.println("QLearning 2 # wins (begint): " + totalScores.player2AantalWinsBegint);
//
//        System.out.println();
//        System.out.println("Enhanced Fight versus random");
//        totalScores = gameEngine.start(100000, randomTicTacToePlayer, qLearningAlgo);
//
//        System.out.println("Random # wins (begint): " + totalScores.player1AantalWinsBegint);
//        System.out.println("QLearning # wins (begint niet): " + totalScores.player2AantalWinsBegintNiet);
//        System.out.println("Random # wins (begint niet): " + totalScores.player1AantalWinsBegintNiet);
//        System.out.println("QLearning # wins (begint): " + totalScores.player2AantalWinsBegint);

    }
}
