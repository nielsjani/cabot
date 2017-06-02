package com.cegeka.cabot.oorlogje;

import com.cegeka.cabot.api.GameEngine;
import com.cegeka.cabot.oorlogje.api.OorlogjeInterface;
import com.cegeka.cabot.oorlogje.player.OorlogjePlayer;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

public class OorlogjeGameEngine extends GameEngine<OorlogjePlayer> {

    public OorlogjeGameEngine() {
    }

    @Override
    protected GameResult playGame(OorlogjePlayer player1, OorlogjePlayer player2) {
        StartSituatie startSituatie = new GameEngineInterface().getStartSituatie();

        OorlogjeInterface oorlogjeInterfacePlayer1 = new OorlogjeInterface(player1);
        OorlogjeInterface oorlogjeInterfacePlayer2 = new OorlogjeInterface(player2);

        GameResult gameResult = new GameResult(startSituatie.isMoetPlayer1Beginnen());

        Beurt player1Beurt = Beurt.beurt()
                .withIkBegin(startSituatie.isMoetPlayer1Beginnen())
                .withHandkaarten(startSituatie.getPlayer1KaartenAsKaart());
        Beurt player2Beurt = Beurt.beurt()
                .withIkBegin(!startSituatie.isMoetPlayer1Beginnen())
                .withHandkaarten(startSituatie.getPlayer2KaartenAsKaart());
        while (gameResult.getPlayer1Punten() < 3 && gameResult.getPlayer2Punten() < 3) {
            performTurn(oorlogjeInterfacePlayer1, oorlogjeInterfacePlayer2, player1Beurt, player2Beurt, gameResult);
        }
        return gameResult;
    }

    private void performTurn(OorlogjeInterface oorlogjeInterfacePlayer1, OorlogjeInterface oorlogjeInterfacePlayer2, Beurt player1Beurt, Beurt player2Beurt, GameResult gameResult) {
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
            gameResult.addPlayer1Punten(1);
        } else {
            gameResult.addPlayer2Punten(1);
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
