package com.cegeka.cabot.oorlogje.reward;

import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class RewardCalculator {

    public int bepaalRewardVoorBotAlsEersteAanZetEnTegenstanderNogGeenKaartGelegd(Beurt beurt,Kaart gespeeldeKaartDoorBot){
        List<Kaart> uniekeBotHandkaartenHoogsteEerst = removeDubbels(beurt.getHandkaarten());
        List<Kaart> botHandkaartenHoogsteEerst = sortBotKaarten(uniekeBotHandkaartenHoogsteEerst);
        return 100 - (botHandkaartenHoogsteEerst.indexOf(gespeeldeKaartDoorBot)*20);
    }

    private List<Kaart> removeDubbels(List<Kaart> botHandkaartenHoogsteEerst) {
        List<Kaart> kaarten = newArrayList();
        kaarten.addAll(newHashSet(botHandkaartenHoogsteEerst));
        return kaarten;
    }

    private List<Kaart> sortBotKaarten(List<Kaart> kaarten) {
        kaarten.sort((k1, k2) -> {
            if(k1.getWaarde() > k2.getWaarde()){
                return -1;
            }
            if(k1.getWaarde() < k2.getWaarde()){
                return 1;
            }
            return 0;
        });
        return kaarten;
    }

    public int bepaalRewardVoorGespeeldeKaart(Beurt beurt,Kaart gespeeldeKaartDoorBot) {
        if(gewonnen(beurt, gespeeldeKaartDoorBot)){
            return berekenRewardVoorGewonnen(beurt, gespeeldeKaartDoorBot);
        } else {
            return berekenRewardVoorVerloren(beurt, gespeeldeKaartDoorBot);
        }
    }

    private boolean gewonnen(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        return botMochtBeginnenEnZijnKaartIsMinstensEvenHoogAlsDieVanDeTegenstander(beurt, gespeeldeKaartDoorBot)
                || botMochtNietBeginnenEnZijnKaartIsHogerDanDieVanDeTegenstander(beurt, gespeeldeKaartDoorBot);
    }

    private boolean botMochtNietBeginnenEnZijnKaartIsHogerDanDieVanDeTegenstander(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        return !beurt.isIkBegin() && beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() < gespeeldeKaartDoorBot.getWaarde();
    }

    private boolean botMochtBeginnenEnZijnKaartIsMinstensEvenHoogAlsDieVanDeTegenstander(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        return beurt.isIkBegin() && beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() <= gespeeldeKaartDoorBot.getWaarde();
    }

    private int berekenRewardVoorGewonnen(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        int verschilInWaardeTussenTweeKaarten = gespeeldeKaartDoorBot.getWaarde() - beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        if(beurt.isIkBegin()){
            return 100 - (verschilInWaardeTussenTweeKaarten*20);
        } else {
            return 100 - ((verschilInWaardeTussenTweeKaarten-1)*20);
        }
    }

    private int berekenRewardVoorVerloren(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        int verschilInWaardeTussenTweeKaarten =  beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() - gespeeldeKaartDoorBot.getWaarde();
        if(beurt.isIkBegin()) {
            return -100 + ((verschilInWaardeTussenTweeKaarten-1)*20);
        } else {
            return -100 + (verschilInWaardeTussenTweeKaarten*20);
        }
    }
}
