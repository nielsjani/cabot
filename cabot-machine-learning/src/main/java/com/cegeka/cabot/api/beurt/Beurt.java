package com.cegeka.cabot.api.beurt;

import java.util.List;

public class Beurt {
    private List<Kaart> botHandkaarten;
    private List<Kaart> botGespeeldeKaarten;
    private List<Kaart> mensGespeeldeKaarten;
    private Kaart gespeeldeKaartDoorTegenstanderHuidigeBeurt;
    private Kaart gespeeldeKaartDoorBotHuidigeBeurt;
    private boolean botMochtAlsEerste;

    private Beurt(){}

    public static Beurt beurt(){
        return new Beurt();
    }

    public Beurt withBotHandkaarten(List<Kaart> botHandkaarten) {
        this.botHandkaarten = botHandkaarten;
        return this;
    }

    public Beurt withBotGespeeldeKaarten(List<Kaart> botGespeeldeKaarten) {
        this.botGespeeldeKaarten = botGespeeldeKaarten;
        return this;
    }

    public Beurt withMensGespeeldeKaarten(List<Kaart> mensGespeeldeKaarten) {
        this.mensGespeeldeKaarten = mensGespeeldeKaarten;
        return this;
    }

    public Beurt withGespeeldeKaartDoorTegenstanderHuidigeBeurt(Kaart gespeeldeKaartHuidigeBeurt) {
        this.gespeeldeKaartDoorTegenstanderHuidigeBeurt = gespeeldeKaartHuidigeBeurt;
        return this;
    }

    public Beurt withBotMochtAlsEerste(boolean botMochtAlsEerste) {
        this.botMochtAlsEerste = botMochtAlsEerste;
        return this;
    }

    public Beurt withGespeeldeKaartDoorBotHuidigeBeurt(Kaart gespeeldeKaartDoorBotHuidigeBeurt) {
        this.gespeeldeKaartDoorBotHuidigeBeurt = gespeeldeKaartDoorBotHuidigeBeurt;
        return this;
    }

    public Kaart getGespeeldeKaartDoorBotHuidigeBeurt() {
        return gespeeldeKaartDoorBotHuidigeBeurt;
    }

    public List<Kaart> getBotHandkaarten() {
        return botHandkaarten;
    }

    public List<Kaart> getBotGespeeldeKaarten() {
        return botGespeeldeKaarten;
    }

    public List<Kaart> getMensGespeeldeKaarten() {
        return mensGespeeldeKaarten;
    }

    public Kaart getGespeeldeKaartDoorTegenstanderHuidigeBeurt() {
        return gespeeldeKaartDoorTegenstanderHuidigeBeurt;
    }

    public boolean isBotMochtAlsEerste() {
        return botMochtAlsEerste;
    }
}
