package com.cegeka.cabot.tictactoe.domain;

public enum TicTacToeGameResult {

    ONGOING(false),
    DRAW(true),
    WINNER(true);

    private boolean finishedState;

    TicTacToeGameResult(boolean finishedState) {
        this.finishedState = finishedState;
    }

    public boolean isFinishedState() {
        return finishedState;
    }
}
