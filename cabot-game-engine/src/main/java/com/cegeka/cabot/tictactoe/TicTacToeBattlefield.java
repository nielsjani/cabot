package com.cegeka.cabot.tictactoe;

import com.cegeka.cabot.api.TotalScores;
import com.cegeka.cabot.oorlogje.player.HumanOorlogjePlayer;
import com.cegeka.cabot.tictactoe.player.HumanTicTacToePlayer;
import com.cegeka.cabot.tictactoe.player.QLearningTicTacToePlayer;
import com.cegeka.cabot.tictactoe.player.RandomTicTacToePlayer;

public class TicTacToeBattlefield {

    public static void main(String[] args) {
        //demoMachineLearning();
        //HumanVsRandom();
        HumanVsAI();
    }

    private static void HumanVsAI() {

        TicTacToeGameEngine gameEngine = new TicTacToeGameEngine();

        HumanTicTacToePlayer humanTicTacToePlayer = new HumanTicTacToePlayer();
        QLearningTicTacToePlayer qLearningAlgo = doTrainingFase(gameEngine);

        System.out.println("HUMAN VS AI");
        qLearningAlgo.setInLearningMode(false);

        TotalScores totalScores = gameEngine.start(0, new HumanTicTacToePlayer(), qLearningAlgo);

        totalScores.print();
    }

    private static QLearningTicTacToePlayer doTrainingFase(TicTacToeGameEngine gameEngine) {
        QLearningTicTacToePlayer qLearningAlgo = new QLearningTicTacToePlayer();
        System.out.println("Training Fase");
        RandomTicTacToePlayer randomTicTacToePlayer = new RandomTicTacToePlayer();
        gameEngine.start(100000, randomTicTacToePlayer, qLearningAlgo);

        return qLearningAlgo;
    }

    private static void HumanVsRandom() {

    }

    private static void demoMachineLearning() {
        System.out.println("Training Fase");

        TicTacToeGameEngine gameEngine = new TicTacToeGameEngine();

        RandomTicTacToePlayer randomTicTacToePlayer = new RandomTicTacToePlayer();
        QLearningTicTacToePlayer qLearningAlgo = new QLearningTicTacToePlayer("QLearning");
        QLearningTicTacToePlayer qLearningAlgoEnhanced = new QLearningTicTacToePlayer("QLearningEnhanced");

        TotalScores totalScores = gameEngine
                .start(100000, randomTicTacToePlayer, qLearningAlgo);

        totalScores.print();

        System.out.println();
        System.out.println("For reals");
        qLearningAlgo.setInLearningMode(false);

        totalScores = gameEngine.start(10000, randomTicTacToePlayer, qLearningAlgo);

        totalScores.print();

        System.out.println();
        System.out.println("Twin fight!");
        totalScores = gameEngine.start(100000, qLearningAlgo, qLearningAlgo);
        totalScores.print();

        System.out.println();
        System.out.println("Enhanced Training");
        totalScores = gameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);
        totalScores.print();

        qLearningAlgoEnhanced.setInLearningMode(false);

        System.out.println();
        System.out.println("Enhanced Fight");
        totalScores = gameEngine.start(100000, qLearningAlgoEnhanced, qLearningAlgo);
        totalScores.print();

        System.out.println();
        System.out.println("Enhanced Fight versus random");
        totalScores = gameEngine.start(100000, randomTicTacToePlayer, qLearningAlgoEnhanced);
        totalScores.print();
    }
}
