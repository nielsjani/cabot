package com.cegeka.cabot.api;

public abstract class GameEngine<P extends Player> {

    private TotalScores totalScores = new TotalScores();

    public TotalScores start(int numberOfGames, P player1, P player2) {
        resetScores();
        totalScores.setPlayer1Name(player1.name());
        totalScores.setPlayer2Name(player2.name());
        for (int i = 0; i <= numberOfGames; i++) {
            GameResult gameResult = playGame(player1, player2);
            if (gameResult.getPlayer1Punten() > gameResult.getPlayer2Punten()) {
                if (gameResult.isPlayer1MochtBeginnen()) {
                    totalScores.player1AantalWinsBegint++;
                } else {
                    totalScores.player1AantalWinsBegintNiet++;
                }
            } else if (gameResult.getPlayer2Punten() > gameResult.getPlayer1Punten()) {
                if (gameResult.isPlayer1MochtBeginnen()) {
                    totalScores.player2AantalWinsBegintNiet++;
                } else {
                    totalScores.player2AantalWinsBegint++;
                }
            } else {
                if (gameResult.isPlayer1MochtBeginnen()) {
                    totalScores.aantalDrawsPlayer1Begint++;
                } else {
                    totalScores.aantalDrawsPlayer2Begint++;
                }
            }
        }
        return totalScores;
    }

    protected abstract GameResult playGame(P player1, P player2);

    private void resetScores() {
        totalScores = new TotalScores();
    }

    protected class GameResult {

        private int player1Punten = 0;
        private int player2Punten = 0;
        private final boolean player1MochtBeginnen;

        public GameResult(boolean player1MochtBeginnen) {
            this.player1MochtBeginnen = player1MochtBeginnen;
        }

        public int getPlayer1Punten() {
            return player1Punten;
        }

        public int getPlayer2Punten() {
            return player2Punten;
        }

        public boolean isPlayer1MochtBeginnen() {
            return player1MochtBeginnen;
        }

        public void addPlayer1Punten(int punten) {
            this.player1Punten+=punten;
        }

        public void addPlayer2Punten(int punten) {
            this.player2Punten+=punten;
        }

    }

}
