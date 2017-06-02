package com.cegeka.cabot.tictactoe.state;

public class Beurt {

    Integer[][] spelveld;

    private Beurt() {
        spelveld = new Integer[3][3];
    }

    public static Beurt beurt() {
        return new Beurt();
    }



}
