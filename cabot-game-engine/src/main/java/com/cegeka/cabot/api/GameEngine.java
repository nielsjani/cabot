package com.cegeka.cabot.api;

public abstract class GameEngine<P extends Player> {

    private TotalScores totalScores = new TotalScores();
    protected int player1Punten = 0;
    protected int player2Punten = 0;

    public TotalScores start(int numberOfGames, P player1, P player2) {
        resetScores();
        for (int i = 0; i <= numberOfGames; i++) {
            boolean player1MochtBeginnen = playGame(player1, player2);
            if (player1Punten > player2Punten) {
                if (player1MochtBeginnen) {
                    totalScores.player1AantalWinsBegint++;
                } else {
                    totalScores.player1AantalWinsBegintNiet++;
                }
            } else {
                if (player1MochtBeginnen) {
                    totalScores.player2AantalWinsBegintNiet++;
                } else {
                    totalScores.player2AantalWinsBegint++;
                }
            }
            player1Punten = 0;
            player2Punten = 0;
        }
        return totalScores;
    }

    protected abstract boolean playGame(P player1, P player2);

    private void resetScores() {
        totalScores = new TotalScores();
        player1Punten = 0;
        player2Punten = 0;
    }


}
