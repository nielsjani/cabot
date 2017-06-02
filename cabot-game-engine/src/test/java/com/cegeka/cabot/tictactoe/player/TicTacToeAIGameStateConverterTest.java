package com.cegeka.cabot.tictactoe.player;

import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToeAIGameStateConverterTest {

    @Test
    public void toActionValue() {
        TicTacToeAIGameStateConverter converter = new TicTacToeAIGameStateConverter();

        assertThat(converter.toActionValue(TicTacToeAction.ticTacToeAction(1,2))).isEqualTo(12);
        assertThat(converter.toActionValue(TicTacToeAction.ticTacToeAction(0,1))).isEqualTo(1);
    }

}