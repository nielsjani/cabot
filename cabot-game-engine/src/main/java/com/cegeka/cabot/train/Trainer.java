package com.cegeka.cabot.train;

import com.cegeka.cabot.api.GameEngineInterface;
import com.cegeka.cabot.api.MachineLearningAlgo;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;

public class Trainer {

    private TotalScores totalScores = new TotalScores();
    private int player1Punten = 0;
    private int player2Punten = 0;
    private MachineLearningAlgo player1;
    private MachineLearningAlgo player2;

    public Trainer(MachineLearningAlgo player1, MachineLearningAlgo player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public TotalScores start(int numberOfGames) {
        for (int i = 0; i <= numberOfGames; i++) {
//            System.out.println("Start playing game " + i);
            boolean player1MochtBeginnen = playGame();
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
//            System.out.println("Ended playing game " + i);
        }
        return totalScores;
    }

    private boolean playGame() {
        StartSituatie startSituatie = new GameEngineInterface().getStartSituatie();

        OorlogjeInterface oorlogjeInterfacePlayer1 = new OorlogjeInterface(player1);
        OorlogjeInterface oorlogjeInterfacePlayer2 = new OorlogjeInterface(player2);

        Beurt player1Beurt = Beurt.beurt()
                .withIkBegin(startSituatie.isMoetPlayer1Beginnen())
                .withHandkaarten(startSituatie.getPlayer1KaartenAsKaart());
        Beurt player2Beurt = Beurt.beurt()
                .withIkBegin(!startSituatie.isMoetPlayer1Beginnen())
                .withHandkaarten(startSituatie.getPlayer2KaartenAsKaart());
        while (player1Punten < 3 && player2Punten < 3) {
            performTurn(oorlogjeInterfacePlayer1, oorlogjeInterfacePlayer2, player1Beurt, player2Beurt);
        }
        return startSituatie.isMoetPlayer1Beginnen();
    }

    private void performTurn(OorlogjeInterface oorlogjeInterfacePlayer1, OorlogjeInterface oorlogjeInterfacePlayer2, Beurt player1Beurt, Beurt player2Beurt) {
        Beurt player1StateVoorBeurt;
        Beurt player2StateVoorBeurt;
        if (player1Beurt.isIkBegin()) {
            player1StateVoorBeurt = player1Beurt.clone();
            resolveGelegdeKaart(oorlogjeInterfacePlayer1, player1Beurt, player2Beurt);
            player2StateVoorBeurt = player2Beurt.clone();
            resolveGelegdeKaart(oorlogjeInterfacePlayer2, player2Beurt, player1Beurt);
        } else {
            player2StateVoorBeurt = player2Beurt.clone();
            resolveGelegdeKaart(oorlogjeInterfacePlayer2, player2Beurt, player1Beurt);
            player1StateVoorBeurt = player1Beurt.clone();
            resolveGelegdeKaart(oorlogjeInterfacePlayer1, player1Beurt, player2Beurt);
        }
        oorlogjeInterfacePlayer1.geefRewardVoorBeurt(player1StateVoorBeurt, player1Beurt);
        oorlogjeInterfacePlayer2.geefRewardVoorBeurt(player2StateVoorBeurt, player2Beurt);

        boolean isPlayer1Gewonnen = player1Gewonnen(player1Beurt);

        if (isPlayer1Gewonnen) {
            player1Punten = player1Punten + 1;
//            System.out.println("Player 1 won");
//            System.out.println("Score:" + player1Punten + "-" + player2Punten);
        } else {
            player2Punten = player2Punten + 1;
//            System.out.println("Player 2 won");
//            System.out.println("Score:" + player1Punten + "-" + player2Punten);
        }

        resetBeurt(player1Beurt, isPlayer1Gewonnen);
        resetBeurt(player2Beurt, !isPlayer1Gewonnen);
    }

    private void resetBeurt(Beurt playerBeurt, boolean isGewonnen) {
        playerBeurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(null);
        playerBeurt.withGespeeldeKaartHuidigeBeurt(null);
        playerBeurt.withIkBegin(isGewonnen);
    }

    private boolean player1Gewonnen(Beurt player1Beurt) {
        boolean player1MochtBeginnenEnWon =
                player1Beurt.isIkBegin() && player1Beurt.getGespeeldeKaartHuidigeBeurt().getWaarde() >= player1Beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        boolean player1MochtNietBeginnenEnWon =
                !player1Beurt.isIkBegin() && player1Beurt.getGespeeldeKaartHuidigeBeurt().getWaarde() > player1Beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        return player1MochtBeginnenEnWon || player1MochtNietBeginnenEnWon;
    }

    private void resolveGelegdeKaart(OorlogjeInterface oorlogjeInterface, Beurt spelendePlayerBeurt, Beurt anderePlayerBeurt) {
        Kaart kaartGespeeldDoorActieveSpeler = oorlogjeInterface.bepaalTeSpelenKaart(spelendePlayerBeurt);

        spelendePlayerBeurt.getGespeeldeKaarten().add(kaartGespeeldDoorActieveSpeler);
        spelendePlayerBeurt.withGespeeldeKaartHuidigeBeurt(kaartGespeeldDoorActieveSpeler);
        spelendePlayerBeurt.getHandkaarten().remove(kaartGespeeldDoorActieveSpeler);

        anderePlayerBeurt.getTegenstanderGespeeldeKaarten().add(kaartGespeeldDoorActieveSpeler);
        anderePlayerBeurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(kaartGespeeldDoorActieveSpeler);
    }

}
