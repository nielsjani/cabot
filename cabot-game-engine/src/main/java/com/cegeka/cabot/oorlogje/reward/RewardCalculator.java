package com.cegeka.cabot.oorlogje.reward;

import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

public class RewardCalculator {

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
        return !beurt.isBotMochtAlsEerste() && beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() < gespeeldeKaartDoorBot.getWaarde();
    }

    private boolean botMochtBeginnenEnZijnKaartIsMinstensEvenHoogAlsDieVanDeTegenstander(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        return beurt.isBotMochtAlsEerste() && beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() <= gespeeldeKaartDoorBot.getWaarde();
    }

    private int berekenRewardVoorGewonnen(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        int verschilInWaardeTussenTweeKaarten = gespeeldeKaartDoorBot.getWaarde() - beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde();
        if(beurt.isBotMochtAlsEerste()){
            return 100 - (verschilInWaardeTussenTweeKaarten*20);
        } else {
            return 100 - ((verschilInWaardeTussenTweeKaarten-1)*20);
        }
    }

    private int berekenRewardVoorVerloren(Beurt beurt, Kaart gespeeldeKaartDoorBot) {
        int verschilInWaardeTussenTweeKaarten =  beurt.getGespeeldeKaartDoorTegenstanderHuidigeBeurt().getWaarde() - gespeeldeKaartDoorBot.getWaarde();
        if(beurt.isBotMochtAlsEerste()) {
            return -100 + ((verschilInWaardeTussenTweeKaarten-1)*20);
        } else {
            return -100 + (verschilInWaardeTussenTweeKaarten*20);
        }
    }
}
