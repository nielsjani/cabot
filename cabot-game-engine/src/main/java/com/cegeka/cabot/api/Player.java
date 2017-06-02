package com.cegeka.cabot.api;

public interface Player<State,Actie>  {

    Actie bepaalActieOmTeSpelen(State state);

    void kenRewardToeVoorGespeeldeActie(State fromState, State toState, Actie gespeeldeActie, int reward);

}
