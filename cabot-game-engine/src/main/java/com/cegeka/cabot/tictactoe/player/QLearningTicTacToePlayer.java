package com.cegeka.cabot.tictactoe.player;

import com.cegeka.cabot.algorithm.qlearning.QLearningSimplexAlgo;
import com.cegeka.cabot.api.AIGameStateConverter;
import com.cegeka.cabot.api.MachineLearningSimplexAlgo;
import com.cegeka.cabot.tictactoe.TicTacToePlayer;
import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameState;

import java.util.Set;

import static com.cegeka.cabot.tictactoe.domain.TicTacToeAction.ticTacToeAction;

public class QLearningTicTacToePlayer implements TicTacToePlayer {

    private final MachineLearningSimplexAlgo simplexAlgo = new QLearningSimplexAlgo();
    private final AIGameStateConverter<TicTacToeGameState, TicTacToeAction> aiGameStateConverter = new TicTacToeAIGameStateConverter();
    private String name = "QLearning";

    public QLearningTicTacToePlayer() {
    }

    public QLearningTicTacToePlayer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TicTacToeAction bepaalActieOmTeSpelen(TicTacToeGameState ticTacToeGameState, Set<TicTacToeAction> toegelatenActies) {
        Integer algoAction = simplexAlgo.bepaalActie(aiGameStateConverter.toStateValue(ticTacToeGameState), aiGameStateConverter.toActionValues(toegelatenActies));

        return toTicTacToeAction(algoAction);
    }

    private TicTacToeAction toTicTacToeAction(Integer algoAction) {
        if (algoAction < 10) return ticTacToeAction(0, algoAction);
        return ticTacToeAction(Integer.parseInt(algoAction.toString().substring(0,1)), Integer.parseInt(algoAction.toString().substring(1,2)));
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(TicTacToeGameState fromState, TicTacToeAction gespeeldeActieFromState, TicTacToeGameState toState, Set<TicTacToeAction> toegelatenActiesToState, int reward) {
        simplexAlgo.kenRewardToeVoorGekozenActie(
                aiGameStateConverter.toStateValue(fromState),
                aiGameStateConverter.toActionValue(gespeeldeActieFromState),
                aiGameStateConverter.toStateValue(toState),
                aiGameStateConverter.toActionValues(toegelatenActiesToState),
                reward
        );
    }

    public void setInLearningMode(boolean inLearningMode) {
        simplexAlgo.setInLearningMode(inLearningMode);
    }

}
