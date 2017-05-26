package com.cegeka.cabot.oorlogje.startsituatie;

import java.util.List;

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

    public boolean isMoetBotBeginnen() {
        return moetBotBeginnen;
    }
}
