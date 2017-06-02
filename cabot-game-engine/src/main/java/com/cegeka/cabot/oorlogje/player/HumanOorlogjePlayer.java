package com.cegeka.cabot.oorlogje.player;

import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class HumanOorlogjePlayer implements OorlogjePlayer {

    @Override
    public Kaart bepaalActieOmTeSpelen(Beurt beurt, Set<Kaart> toegelatenActies) {
        toonNieuwSpelIndienNodig(beurt.getHandkaarten());
        toonHuidigeSituatie(beurt);
        return KiesTeSpelenKaart(beurt.getHandkaarten());
    }

    private void toonNieuwSpelIndienNodig(List<Kaart> handkaarten) {
        if (handkaarten.size() == 5) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("NIEUW SPEL!");
            System.out.println("===========");
        }
    }

    @Override
    public void kenRewardToeVoorGespeeldeActie(Beurt fromState, Kaart gespeeldeActieFromState, Beurt toState, Set<Kaart> toegelatenActiesToState, int reward) {

        System.out.println(String.format("De tegenstander speelde %s.", toState.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde()));
        if (reward > 0)
            System.out.println("Je wint");
        else
            System.out.println("Je verliest");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private Kaart KiesTeSpelenKaart(List<Kaart> handkaarten) {
        int gekozenKaartWaarde = vraagSpelerskeuze(handkaarten);
        return converteerKeuzeNaarKaart(gekozenKaartWaarde, handkaarten);
    }

    private Kaart converteerKeuzeNaarKaart(int gekozenKaartWaarde, List<Kaart> handkaarten) {
        return handkaarten.stream().filter(kaart -> kaart.getWaarde() == gekozenKaartWaarde).findFirst().get();
    }

    private int vraagSpelerskeuze(List<Kaart> handkaarten) {
        System.out.println("Welke kaart wil je spelen?");
        List<Integer> kaartWaarden = handkaarten.stream().map(Kaart::getWaarde).collect(Collectors.toList());
        Scanner scanner = new Scanner(System.in);
        int keuze = scanner.nextInt();
        while (!kaartWaarden.contains(keuze)) {
            System.out.println("Dit is geen mogelijke keuze. Kies een nieuwe: ");
            keuze = scanner.nextInt();
        }
        return keuze;
    }

    private void toonHuidigeSituatie(Beurt beurt) {
        toonHandkaarten(beurt);
        toonSpelerDieBegint(beurt.isIkBegin());
        toonGespeeldeKaartDoorTegenstanderIndienVanToepassing(beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt(), beurt.isIkBegin());
     //   toonGespeeldeKaartenInVorigeBeurten(beurt.getTegenstanderGespeeldeKaarten(), beurt.getGespeeldeKaarten());
    }

    private void toonSpelerDieBegint(boolean ikBegin) {
        System.out.println(String.format("%s begint", (ikBegin ? "Jij" : "De tegenstander" )));
    }

    private void toonGespeeldeKaartenInVorigeBeurten(List<Kaart> gespeeldeKaartenDoorTegenstander, List<Kaart> gespeeldeKaartenDoorHumanPlayer) {
        System.out.println("In de vorige beurten werden deze kaarten al gespeeld:");
        System.out.println(String.format("Tegenstander: %s", maakToonbareKaarten(gespeeldeKaartenDoorTegenstander)));
        System.out.println(String.format("Jij: %s", maakToonbareKaarten(gespeeldeKaartenDoorHumanPlayer)));
    }

    private void toonGespeeldeKaartDoorTegenstanderIndienVanToepassing(Kaart kaart, boolean ikBegin) {
        if (!ikBegin)
            System.out.println(String.format("De tegenstander speelde deze kaart: %s", kaart.getWaarde()));
    }

    private void toonHandkaarten(Beurt beurt) {
        System.out.println("Je hebt de volgende kaarten in je hand:");
        toonKaarten(beurt.getHandkaarten());
    }

    private void toonKaarten(List<Kaart> handkaarten) {
        System.out.println(maakToonbareKaarten(handkaarten));
    }

    private String maakToonbareKaarten(List<Kaart> kaarten) {
        StringBuilder teTonenKaarten = new StringBuilder();
        kaarten.forEach(kaart -> {
            teTonenKaarten.append(kaart.getWaarde());
            teTonenKaarten.append(", ");
        });
        if (kaarten.size() > 0)
            return teTonenKaarten.toString().substring(0, teTonenKaarten.toString().length() - 2);
        else
            return teTonenKaarten.toString();
    }

}

