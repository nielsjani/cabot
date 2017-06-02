package com.cegeka.cabot.tictactoe.player;

import com.cegeka.cabot.tictactoe.TicTacToePlayer;
import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameState;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cegeka.cabot.tictactoe.domain.TicTacToeAction.ticTacToeAction;

public class HumanTicTacToePlayer implements TicTacToePlayer{
    @Override
    public String name() {
        return "Human";
    }

    @Override
    public TicTacToeAction bepaalActieOmTeSpelen(TicTacToeGameState ticTacToeGameState, Set<TicTacToeAction> toegelatenActies) {
        Integer[][] playingField = ticTacToeGameState.getPlayingField();
        printGameState(playingField);
        toonMogelijkeActies(toegelatenActies);
        int rijIndex = vraagRijIndex(toegelatenActies.stream().map(actie -> actie.getRowColumnCoordinate().getLeft()).collect(Collectors.toList()));
        int kolomIndex = vraagKolomIndex(toegelatenActies.stream().map(actie -> actie.getRowColumnCoordinate().getLeft()).collect(Collectors.toList()));
        return ticTacToeAction(rijIndex, kolomIndex);
    }

    private void toonMogelijkeActies(Set<TicTacToeAction> toegelatenActies) {
        System.out.println("Je kan kiezen uit de volgende acties (rij, kolom): ");
        toegelatenActies.forEach(actie -> {
            System.out.print(String.format("(%s, %s) ", actie.getRowColumnCoordinate().getLeft(), actie.getRowColumnCoordinate().getRight()));
            });
        System.out.println();
    }

    private int vraagKolomIndex(List<Integer> mogelijkeKolomIndexen) {
        System.out.println("Geef de index van de KOLOM: ");
        Scanner scanner = new Scanner(System.in);
        int kolomIndex = scanner.nextInt();
        while (!mogelijkeKolomIndexen.contains(kolomIndex)) {
            System.out.println("Deze kolomindex kan je niet kiezen, probeer opnieuw: ");
            kolomIndex = scanner.nextInt();
        }
        return kolomIndex;
    }

    private int vraagRijIndex(List<Integer> mogelijkeRijIndexen) {
        System.out.println("Geef de index van de RIJ: ");
        Scanner scanner = new Scanner(System.in);
        int rijIndex = scanner.nextInt();
        while (!mogelijkeRijIndexen.contains(rijIndex)) {
            System.out.println("Deze rijindex kan je niet kiezen, probeer opnieuw: ");
            rijIndex = scanner.nextInt();
        }
        return rijIndex;
    }

    public void printGameState(Integer[][] playingField) {
        System.out.println(" BOARD");
        System.out.println("--------");
        for (int row=0; row < 3; row++) {
            for (int column=0; column < 3; column++) {
                System.out.print(playingField[row][column] == null? "* " : mapToCircleOrX(playingField[row][column]) + " ");
            }
            System.out.println();
        }
    }

    private String mapToCircleOrX(Integer value) {
        if (value == 1) {
            return "O";
        } else {
            return "X";
        }
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(TicTacToeGameState fromState, TicTacToeAction gespeeldeActieFromState, TicTacToeGameState toState, Set<TicTacToeAction> toegelatenActiesToState, int reward) {

    }
}
