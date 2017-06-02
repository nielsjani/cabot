package com.cegeka.cabot.api;

import java.math.BigDecimal;

public class TotalScores {

    public int player1AantalWinsBegint = 0;
    public int player1AantalWinsBegintNiet = 0;
    public int player2AantalWinsBegint = 0;
    public int player2AantalWinsBegintNiet = 0;
    public int aantalDrawsPlayer1Begint = 0;
    public int aantalDrawsPlayer2Begint = 0;

    private String player1Name;
    private String player2Name;

    public TotalScores setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
        return this;
    }

    public TotalScores setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
        return this;
    }

    public void print() {
        System.out.println("And the winner is: " + getWinnerAndPercentage());
        System.out.println(player1Name + " # wins: " + (player1AantalWinsBegint + player1AantalWinsBegintNiet));
        System.out.println("\tbegint: " + player1AantalWinsBegint);
        System.out.println("\tbegint niet: " + player1AantalWinsBegintNiet);
        System.out.println(player2Name + " # wins: " + (player2AantalWinsBegint + player2AantalWinsBegintNiet));
        System.out.println("\tbegint: " + player2AantalWinsBegint);
        System.out.println("\tbegint niet: " + player2AantalWinsBegintNiet);
        System.out.println("# draws: " + (aantalDrawsPlayer1Begint + aantalDrawsPlayer2Begint));
        System.out.println("\t" + player1Name + " begint: " + aantalDrawsPlayer1Begint);
        System.out.println("\t" + player2Name + " begint: " + aantalDrawsPlayer2Begint);
    }

    private String getWinnerAndPercentage() {
        Double player1PctWins = ((double) (player1AantalWinsBegint + player1AantalWinsBegintNiet)) / getTotalNumberOfGames();
        Double pctDraws = ((double) (aantalDrawsPlayer1Begint + aantalDrawsPlayer2Begint)) / getTotalNumberOfGames();
        BigDecimal pct = BigDecimal.valueOf(player1PctWins * 100).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal pctReverse = BigDecimal.valueOf((1 - player1PctWins - pctDraws) * 100).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        if (player1PctWins >= 1.0d)
            return player1Name + " " + pct + "%";
        if (player1PctWins <= 1.0d)
            return player2Name + " " + pctReverse + "%";
        return "Gelijkstand!";
    }

    private int getTotalNumberOfGames() {
        return aantalDrawsPlayer1Begint + aantalDrawsPlayer2Begint + player1AantalWinsBegint + player1AantalWinsBegintNiet + player2AantalWinsBegint + player2AantalWinsBegintNiet;
    }

}
