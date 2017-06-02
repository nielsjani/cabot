package com.cegeka.cabot.oorlogje.startsituatie;

import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.List;
import java.util.stream.Collectors;

public class StartSituatie {
    private final List<StartSituatieKaart> botKaarten;
    private final List<StartSituatieKaart> mensKaarten;
    private final boolean moetBotBeginnen;

    public StartSituatie(List<StartSituatieKaart> botKaarten, List<StartSituatieKaart> mensKaarten, boolean moetBotBeginnen) {
        this.botKaarten = botKaarten;
        this.mensKaarten = mensKaarten;
        this.moetBotBeginnen = moetBotBeginnen;
    }

    public List<StartSituatieKaart> getBotKaarten() {
        return botKaarten;
    }

    public List<StartSituatieKaart> getMensKaarten() {
        return mensKaarten;
    }

    public List<Kaart> getPlayer1KaartenAsKaart(){
        return botKaarten.stream().map(botkaart-> new Kaart(botkaart.getWaarde())).collect(Collectors.toList());
    }

    public List<Kaart> getPlayer2KaartenAsKaart(){
        return mensKaarten.stream().map(menskaart-> new Kaart(menskaart.getWaarde())).collect(Collectors.toList());
    }

    public boolean isMoetPlayer1Beginnen() {
        return moetBotBeginnen;
    }
}
