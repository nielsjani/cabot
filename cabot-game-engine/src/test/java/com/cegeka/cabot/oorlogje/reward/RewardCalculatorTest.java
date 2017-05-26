package com.cegeka.cabot.oorlogje.reward;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RewardCalculatorTest extends UnitTest{


    @Test
    public void bepaalRewardVoorGespeeldeKaart_GivenGewonnenInBeurtWaarTegenstanderBegonThenMeestePuntenVoorKleinsteVerschil(){
        assertGewonnenInBeurtWaarTegenstanderBegon(1, 2, 100);
        assertGewonnenInBeurtWaarTegenstanderBegon(1, 3, 80);
        assertGewonnenInBeurtWaarTegenstanderBegon(1, 4, 60);
        assertGewonnenInBeurtWaarTegenstanderBegon(1, 5, 40);

        assertGewonnenInBeurtWaarTegenstanderBegon(2, 3, 100);
        assertGewonnenInBeurtWaarTegenstanderBegon(2, 4, 80);
        assertGewonnenInBeurtWaarTegenstanderBegon(2, 5, 60);

        assertGewonnenInBeurtWaarTegenstanderBegon(3, 4, 100);
        assertGewonnenInBeurtWaarTegenstanderBegon(3, 5, 80);

        assertGewonnenInBeurtWaarTegenstanderBegon(4, 5, 100);
    }

    @Test
    public void bepaalRewardVoorGespeeldeKaart_GivenGewonnenInBeurtWaarBotBegonThenMeestePuntenVoorKleinsteVerschil(){
        assertGewonnenInBeurtWaarBotBegon(1, 1, 100);
        assertGewonnenInBeurtWaarBotBegon(1, 2, 80);
        assertGewonnenInBeurtWaarBotBegon(1, 3, 60);
        assertGewonnenInBeurtWaarBotBegon(1, 4, 40);
        assertGewonnenInBeurtWaarBotBegon(1, 5, 20);

        assertGewonnenInBeurtWaarBotBegon(2, 2, 100);
        assertGewonnenInBeurtWaarBotBegon(2, 3, 80);
        assertGewonnenInBeurtWaarBotBegon(2, 4, 60);
        assertGewonnenInBeurtWaarBotBegon(2, 5, 40);

        assertGewonnenInBeurtWaarBotBegon(3, 3, 100);
        assertGewonnenInBeurtWaarBotBegon(3, 4, 80);
        assertGewonnenInBeurtWaarBotBegon(3, 5, 60);

        assertGewonnenInBeurtWaarBotBegon(4, 4, 100);
        assertGewonnenInBeurtWaarBotBegon(4, 5, 80);
    }

    @Test
    public void bepaalRewardVoorGespeeldeKaart_GivenVerlorenInBeurtWaarTegenstanderBegonThenMeestePuntenVoorGrootsteVerschil(){
        assertVerlorenInBeurtWaarTegenstanderBegon(5, 1, -20);
        assertVerlorenInBeurtWaarTegenstanderBegon(5, 2, -40);
        assertVerlorenInBeurtWaarTegenstanderBegon(5, 3, -60);
        assertVerlorenInBeurtWaarTegenstanderBegon(5, 4, -80);
        assertVerlorenInBeurtWaarTegenstanderBegon(5, 5, -100);

        assertVerlorenInBeurtWaarTegenstanderBegon(4, 1, -40);
        assertVerlorenInBeurtWaarTegenstanderBegon(4, 2, -60);
        assertVerlorenInBeurtWaarTegenstanderBegon(4, 3, -80);
        assertVerlorenInBeurtWaarTegenstanderBegon(4, 4, -100);

        assertVerlorenInBeurtWaarTegenstanderBegon(3, 1, -60);
        assertVerlorenInBeurtWaarTegenstanderBegon(3, 2, -80);
        assertVerlorenInBeurtWaarTegenstanderBegon(3, 3, -100);

        assertVerlorenInBeurtWaarTegenstanderBegon(2, 1, -80);
        assertVerlorenInBeurtWaarTegenstanderBegon(2, 2, -100);

        assertVerlorenInBeurtWaarTegenstanderBegon(1, 1, -100);
    }

    @Test
    public void bepaalRewardVoorGespeeldeKaart_GivenVerlorenInBeurtWaarBotBegonThenMeestePuntenVoorGrootsteVerschil(){
        assertVerlorenInBeurtWaarBotBegon(5, 1, -40);
        assertVerlorenInBeurtWaarBotBegon(5, 2, -60);
        assertVerlorenInBeurtWaarBotBegon(5, 3, -80);
        assertVerlorenInBeurtWaarBotBegon(5, 4, -100);

        assertVerlorenInBeurtWaarBotBegon(4, 1, -60);
        assertVerlorenInBeurtWaarBotBegon(4, 2, -80);
        assertVerlorenInBeurtWaarBotBegon(4, 3, -100);

        assertVerlorenInBeurtWaarBotBegon(3, 1, -80);
        assertVerlorenInBeurtWaarBotBegon(3, 2, -100);

        assertVerlorenInBeurtWaarBotBegon(2, 1, -100);
    }

    private void assertVerlorenInBeurtWaarTegenstanderBegon(int kaartWaardeTegenstander, int kaartWaardeBot, int verwachteReward) {
        Beurt beurt = givenTegenstanderSpeeltEerstMetWaarde(kaartWaardeTegenstander);
        Kaart kaart = givenGespeeldKaartDoorBotMetWaarde(kaartWaardeBot);

        int reward = new RewardCalculator().bepaalRewardVoorGespeeldeKaart(beurt, kaart);

        assertThat(reward).isEqualTo(verwachteReward);
    }

    private void assertVerlorenInBeurtWaarBotBegon(int kaartWaardeTegenstander, int kaartWaardeBot, int verwachteReward) {
        Beurt beurt = givenTegenstanderSpeeltAlsTweedeMetWaarde(kaartWaardeTegenstander);
        Kaart kaart = givenGespeeldKaartDoorBotMetWaarde(kaartWaardeBot);

        int reward = new RewardCalculator().bepaalRewardVoorGespeeldeKaart(beurt, kaart);

        assertThat(reward).isEqualTo(verwachteReward);
    }

    private void assertGewonnenInBeurtWaarTegenstanderBegon(int kaartWaardeTegenstander, int kaartWaardeBot, int verwachteReward) {
        Beurt beurt = givenTegenstanderSpeeltEerstMetWaarde(kaartWaardeTegenstander);
        Kaart kaart = givenGespeeldKaartDoorBotMetWaarde(kaartWaardeBot);

        int reward = new RewardCalculator().bepaalRewardVoorGespeeldeKaart(beurt, kaart);

        assertThat(reward).isEqualTo(verwachteReward);
    }

    private void assertGewonnenInBeurtWaarBotBegon(int kaartWaardeTegenstander, int kaartWaardeBot, int verwachteReward) {
        Beurt beurt = givenTegenstanderSpeeltAlsTweedeMetWaarde(kaartWaardeTegenstander);
        Kaart kaart = givenGespeeldKaartDoorBotMetWaarde(kaartWaardeBot);

        int reward = new RewardCalculator().bepaalRewardVoorGespeeldeKaart(beurt, kaart);

        assertThat(reward).isEqualTo(verwachteReward);
    }

    private Kaart givenGespeeldKaartDoorBotMetWaarde(int kaartWaarde) {
        return new Kaart(kaartWaarde);
    }

    private Beurt givenTegenstanderSpeeltEerstMetWaarde(int kaartWaarde) {
        return Beurt.beurt().withBotMochtAlsEerste(false)
                .withGespeeldeKaartDoorTegenstanderHuidigeBeurt(new Kaart(kaartWaarde));
    }

    private Beurt givenTegenstanderSpeeltAlsTweedeMetWaarde(int kaartWaarde) {
        return Beurt.beurt().withBotMochtAlsEerste(true)
                .withGespeeldeKaartDoorTegenstanderHuidigeBeurt(new Kaart(kaartWaarde));
    }
}