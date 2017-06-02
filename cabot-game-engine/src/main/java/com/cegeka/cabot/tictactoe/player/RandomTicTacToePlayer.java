package com.cegeka.cabot.tictactoe.player;

import com.cegeka.cabot.tictactoe.TicTacToePlayer;
import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameState;

import java.util.Random;
import java.util.Set;

public class RandomTicTacToePlayer implements TicTacToePlayer {

    @Override
    public String name() {
        return "Random";
    }

    @Override
    public TicTacToeAction bepaalActieOmTeSpelen(TicTacToeGameState ticTacToeGameState, Set<TicTacToeAction> toegelatenActies) {
        return toegelatenActies.toArray(new TicTacToeAction[toegelatenActies.size()])[new Random().nextInt(toegelatenActies.size())];
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(TicTacToeGameState fromState, TicTacToeAction gespeeldeActieFromState, TicTacToeGameState toState, Set<TicTacToeAction> toegelatenActiesToState, int reward) {

    }

}
