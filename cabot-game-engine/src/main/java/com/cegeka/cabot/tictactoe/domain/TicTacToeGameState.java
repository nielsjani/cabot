package com.cegeka.cabot.tictactoe.domain;

import java.util.HashSet;
import java.util.Set;

public class TicTacToeGameState {

    Integer[][] playingField;

    private TicTacToeGameState() {
        playingField = new Integer[3][3];
    }

    public static TicTacToeGameState ticTacToeGameState() {
        return new TicTacToeGameState();
    }

    public Integer[][] getPlayingField() {
        return playingField;
    }

    public TicTacToeGameResult applyAction(TicTacToeAction action, int playerToken) {
        if (playingField[action.getRowColumnCoordinate().getLeft()][action.getRowColumnCoordinate().getRight()] != null) {
            throw new IllegalArgumentException("Illegal Action! Cell not free!");
        }
        playingField[action.getRowColumnCoordinate().getLeft()][action.getRowColumnCoordinate().getRight()] = playerToken;
        return isGameFinishedByAction(action);
    }

    private TicTacToeGameResult isGameFinishedByAction(TicTacToeAction action) {
        boolean isGameWon = isRowFinished(action.getRowColumnCoordinate().getLeft())
                || isColumnFinished(action.getRowColumnCoordinate().getRight())
                || (isLigtOpLinksBovenDiagonaal(action) && isLinksBovenDiagnaalFinished())
                || (isLigtOpLinksOnderDiagonaal(action) && isLinksOnderDiagnaalFinished());
        if (isGameWon) return TicTacToeGameResult.WINNER;
        if (getToegelatenActies().isEmpty()) return TicTacToeGameResult.DRAW;
        return TicTacToeGameResult.ONGOING;
    }

    private boolean isLigtOpLinksOnderDiagonaal(TicTacToeAction action) {
        return action.getRowColumnCoordinate().getLeft() + action.getRowColumnCoordinate().getRight() == 2;
    }

    private boolean isLigtOpLinksBovenDiagonaal(TicTacToeAction action) {
        return action.getRowColumnCoordinate().getRight() == action.getRowColumnCoordinate().getLeft();
    }

    private boolean isColumnFinished(Integer column) {
        return (playingField[0][column] == playingField[1][column]) && (playingField[1][column] == playingField[2][column]);
    }

    private boolean isRowFinished(Integer row) {
        return (playingField[row][0] == playingField[row][1]) && (playingField[row][1] == playingField[row][2]);
    }

    public boolean isLinksBovenDiagnaalFinished() {
        return (playingField[0][0] == playingField[1][1]) && (playingField[1][1] == playingField[2][2]);
    }

    public boolean isLinksOnderDiagnaalFinished() {
        return (playingField[2][0] == playingField[1][1]) && (playingField[1][1] == playingField[0][2]);
    }

    public Set<TicTacToeAction> getToegelatenActies() {
        Set<TicTacToeAction> toegelatenActies = new HashSet<>();
        for (int row=0; row < 3; row++) {
            for (int column=0; column < 3; column++) {
                if (playingField[row][column] == null) {
                    toegelatenActies.add(TicTacToeAction.ticTacToeAction(row, column));
                }
            }
        }
        return toegelatenActies;
    }

    public TicTacToeGameState clone() {
        TicTacToeGameState clone = new TicTacToeGameState();

        for (int row=0; row < 3; row++) {
            for (int column=0; column < 3; column++) {
                clone.playingField[row][column] = playingField[row][column];
            }
        }

        return clone;
    }

    public void printGameState() {
        System.out.println(" BOARD");
        System.out.println("--------");
        for (int row=0; row < 3; row++) {
            for (int column=0; column < 3; column++) {
                System.out.print(playingField[row][column] == null? "0 " : playingField[row][column] + " ");
            }
            System.out.println();
        }
    }

}
