package com.cegeka.cabot.api;

import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

public interface MachineLearningAlgo {

    /**
     * Bepaalt een kaart die gespeeld wordt door het ML Algo.
     * op basis van een huidige spelsituatie (beurt) en gespeelde kaart
     * door de tegenstander
     * @param beurt
     *        De huidige situatie waarbij het ML Algo.
     *        een kaart moet bepalen om te spelen
     * @param gespeeldDoorTegenstander
     *        De kaart gespeeld door de tegenstander waarop
     *        een tegenactie (het spelen van een eigen kaart)
     *        moet bepaald worden
     * @return De kaart die door het ML. Algo. gespeeld wordt
     */
    Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander);

    /**
     * Kent het ML Algo. een reward toe voor de kaart die het bepaald heeft
     * te spelen. Hoe hoger de reward, hoe beter de gespeelde kaart was.
     * @param fromBeurt
     *        zie hierboven
     * @param toBeurt
     * @param gespeeldDoorMLAlgo
     *        zie hierboven
     * @param reward
     *        De reward, bepaald door de game-engine, die het ML Algo. krijgt voor zijn
     *        gespeelde kaart.
     */
    void kenRewardToeVoorGespeeldeKaart(Beurt fromBeurt, Beurt toBeurt, Kaart gespeeldDoorMLAlgo, int reward);
}
