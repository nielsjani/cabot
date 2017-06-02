package com.cegeka.cabot.oorlogje.state;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Beurt {
    private List<Kaart> handkaarten = newArrayList();
    private List<Kaart> gespeeldeKaarten = newArrayList();
    private List<Kaart> tegenstanderGespeeldeKaarten = newArrayList();
    private Kaart gespeeldeKaartDoorTegenstanderHuidigeBeurt;
    private Kaart gespeeldeKaartHuidigeBeurt;
    private boolean ikBegin;

    private Beurt(){}

    public static Beurt beurt(){
        return new Beurt();
    }

    public Beurt withHandkaarten(List<Kaart> handkaarten) {
        this.handkaarten = handkaarten;
        return this;
    }

    public Beurt withGespeeldeKaarten(List<Kaart> gespeeldeKaarten) {
        this.gespeeldeKaarten = gespeeldeKaarten;
        return this;
    }

    public Beurt withTegenstanderGespeeldeKaarten(List<Kaart> tegenstanderGespeeldeKaarten) {
        this.tegenstanderGespeeldeKaarten = tegenstanderGespeeldeKaarten;
        return this;
    }

    public Beurt withGespeeldeKaartDoorTegenstanderHuidigeBeurt(Kaart gespeeldeKaartHuidigeBeurt) {
        this.gespeeldeKaartDoorTegenstanderHuidigeBeurt = gespeeldeKaartHuidigeBeurt;
        return this;
    }

    public Beurt withIkBegin(boolean ikBegin) {
        this.ikBegin = ikBegin;
        return this;
    }

    public Beurt withGespeeldeKaartHuidigeBeurt(Kaart gespeeldeKaartHuidigeBeurt) {
        this.gespeeldeKaartHuidigeBeurt = gespeeldeKaartHuidigeBeurt;
        return this;
    }

    public Kaart getGespeeldeKaartHuidigeBeurt() {
        return gespeeldeKaartHuidigeBeurt;
    }

    public List<Kaart> getHandkaarten() {
        return handkaarten;
    }

    public List<Kaart> getGespeeldeKaarten() {
        return gespeeldeKaarten;
    }

    public List<Kaart> getTegenstanderGespeeldeKaarten() {
        return tegenstanderGespeeldeKaarten;
    }

    public Kaart getGespeeldeKaartDoorTegenstanderHuidigeBeurt() {
        return gespeeldeKaartDoorTegenstanderHuidigeBeurt;
    }

    public boolean isIkBegin() {
        return ikBegin;
    }

    public Beurt clone() {
        return new Beurt()
                .withHandkaarten(newArrayList(handkaarten))
                .withGespeeldeKaarten(newArrayList(gespeeldeKaarten))
                .withTegenstanderGespeeldeKaarten(newArrayList(tegenstanderGespeeldeKaarten))
                .withGespeeldeKaartHuidigeBeurt(gespeeldeKaartHuidigeBeurt)
                .withGespeeldeKaartDoorTegenstanderHuidigeBeurt(gespeeldeKaartDoorTegenstanderHuidigeBeurt)
                .withIkBegin(ikBegin);
    }
}
