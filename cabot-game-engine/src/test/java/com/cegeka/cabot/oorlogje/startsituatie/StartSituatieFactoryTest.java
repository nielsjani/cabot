package com.cegeka.cabot.oorlogje.startsituatie;

import com.cegeka.cabot.UnitTest;
import org.junit.Test;

import static com.cegeka.cabot.oorlogje.state.OorlogjeConstants.INITIELE_HAND_GROOTTE;
import static org.assertj.core.api.Assertions.assertThat;

public class StartSituatieFactoryTest extends UnitTest{

    @Test
    public void createStartSituatie_ShouldCreateRandomizedStartSituatieWithCorrectNumberOfCardsForEachPlayer() {
        StartSituatie startSituatie = new StartSituatieFactory().createStartSituatie();

        assertThat(startSituatie.getBotKaarten()).doesNotContainAnyElementsOf(startSituatie.getMensKaarten());
        assertThat(startSituatie.getBotKaarten()).doesNotHaveDuplicates();
        assertThat(startSituatie.getBotKaarten().size())
                .isEqualTo(startSituatie.getMensKaarten().size())
                .isEqualTo(INITIELE_HAND_GROOTTE);
        assertThat(startSituatie.isMoetPlayer1Beginnen()).isNotNull();
    }

    //In theory this test could fail once every 1000s of runs. Should never fail multiple times in a row, though
    @Test
    public void createStartSituatie_ShouldAlwaysProduceDifferentHandCards() {
        StartSituatie startSituatie1 = new StartSituatieFactory().createStartSituatie();
        StartSituatie startSituatie2 = new StartSituatieFactory().createStartSituatie();

        assertThat(startSituatie1.getBotKaarten()).isNotEqualTo(startSituatie2.getBotKaarten());
        assertThat(startSituatie1.getMensKaarten()).isNotEqualTo(startSituatie2.getMensKaarten());

    }
}