package com.cegeka.cabot.tictactoe.domain;

import org.apache.commons.lang3.tuple.Pair;

public class TicTacToeAction {

    private Pair<Integer, Integer> rowColumnCoordinate;

    private TicTacToeAction(Pair<Integer, Integer> rowColumnCoordinate) {
        this.rowColumnCoordinate = rowColumnCoordinate;
    }

    public static TicTacToeAction ticTacToeAction(int rowIndex, int columnIndex) {
        return new TicTacToeAction(Pair.of(rowIndex, columnIndex));
    }

    public Pair<Integer, Integer> getRowColumnCoordinate() {
        return rowColumnCoordinate;
    }

    @Override
    public String toString() {
        return rowColumnCoordinate.getLeft() + "-" + rowColumnCoordinate.getRight();
    }
}
