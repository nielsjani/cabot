package com.cegeka.cabot.train;

import com.cegeka.cabot.api.GameEngineInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;

public class Trainer {

    private int player1Punten = 0;
    private int player2Punten = 0;
    private RandomMachineLearningInterface machineLearningInterface;

    public Trainer(RandomMachineLearningInterface machineLearningInterface) {
        this.machineLearningInterface = machineLearningInterface;
    }

    public void start(int numberOfGames) {
        for (int i = 0; i <= numberOfGames; i++) {
            System.out.println("Start playing game " + i);
            playGame();
            player1Punten = 0;
            player2Punten = 0;
            System.out.println("Ended playing game " + i);
        }
    }

    private void playGame() {
        StartSituatie startSituatie = new GameEngineInterface().getStartSituatie();
        OorlogjeInterface oorlogjeInterface = new OorlogjeInterface(machineLearningInterface);
        Beurt player1Beurt = Beurt.beurt()
                .withBotMochtAlsEerste(startSituatie.isMoetBotBeginnen())
                .withBotHandkaarten(startSituatie.getBotKaartenAsKaart());
        Beurt player2Beurt = Beurt.beurt()
                .withBotMochtAlsEerste(!startSituatie.isMoetBotBeginnen())
                .withBotHandkaarten(startSituatie.getMensKaartenAsKaart());
        while (player1Punten < 3 && player2Punten < 3) {
            performTurn(oorlogjeInterface, player1Beurt, player2Beurt);
        }
    }

    private void performTurn(OorlogjeInterface oorlogjeInterface, Beurt player1Beurt, Beurt player2Beurt) {
        if (player1Beurt.isBotMochtAlsEerste()) {
            resolveGelegdeKaart(oorlogjeInterface, player1Beurt, player2Beurt);
            resolveGelegdeKaart(oorlogjeInterface, player2Beurt, player1Beurt);
            oorlogjeInterface.geefRewardVoorBeurtWaarBotMochtBeginnen(player1Beurt);
        } else {
            resolveGelegdeKaart(oorlogjeInterface, player2Beurt, player1Beurt);
            resolveGelegdeKaart(oorlogjeInterface, player1Beurt, player2Beurt);
            oorlogjeInterface.geefRewardVoorBeurtWaarBotMochtBeginnen(player2Beurt);
        }
        boolean isPlayer1Gewonnen = player1Gewonnen(player1Beurt);
        if (isPlayer1Gewonnen) {
            player1Punten = player1Punten + 1;
            System.out.println("Player 1 won");
            System.out.println("Score:" + player1Punten + "-" + player2Punten);
        } else {
            player2Punten = player2Punten + 1;
            System.out.println("Player 2 won");
            System.out.println("Score:" + player1Punten + "-" + player2Punten);
        }
        resetBeurt(player1Beurt, isPlayer1Gewonnen);
        resetBeurt(player2Beurt, !isPlayer1Gewonnen);
    }

    private void resetBeurt(Beurt playerBeurt, boolean isGewonnen) {
        playerBeurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(null);
        playerBeurt.withGespeeldeKaartDoorBotHuidigeBeurt(null);
        playerBeurt.withBotMochtAlsEerste(isGewonnen);
    }

    private boolean player1Gewonnen(Beurt player1Beurt) {
        boolean player1MochtBeginnenEnWon = player1Beurt.isBotMochtAlsEerste() && player1Beurt.getGespeeldeKaartDoorBotHuidigeBeurt().getWaarde() >= player1Beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        boolean player1MochtNietBeginnenEnWon = !player1Beurt.isBotMochtAlsEerste() && player1Beurt.getGespeeldeKaartDoorBotHuidigeBeurt().getWaarde() > player1Beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        return player1MochtBeginnenEnWon || player1MochtNietBeginnenEnWon;
    }

    private void resolveGelegdeKaart(OorlogjeInterface oorlogjeInterface, Beurt spelendePlayerBeurt, Beurt anderePlayerBeurt) {
        Kaart kaartGespeeldDoorActieveSpeler = oorlogjeInterface.bepaalTeSpelenKaart(spelendePlayerBeurt);

        spelendePlayerBeurt.getBotGespeeldeKaarten().add(kaartGespeeldDoorActieveSpeler);
        spelendePlayerBeurt.withGespeeldeKaartDoorBotHuidigeBeurt(kaartGespeeldDoorActieveSpeler);
        spelendePlayerBeurt.getBotHandkaarten().remove(kaartGespeeldDoorActieveSpeler);

        anderePlayerBeurt.getMensGespeeldeKaarten().add(kaartGespeeldDoorActieveSpeler);
        anderePlayerBeurt.withGespeeldeKaartDoorTegenstanderHuidigeBeurt(kaartGespeeldDoorActieveSpeler);
    }

}
