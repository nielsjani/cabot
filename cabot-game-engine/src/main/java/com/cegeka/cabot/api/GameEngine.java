package com.cegeka.cabot.api;

public interface GameEngine<P extends Player> {

    TotalScores start(int numberOfGames, P player1, P player2);

}
