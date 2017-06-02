package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.oorlogje.player.OorlogjePlayer;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import com.cegeka.cabot.oorlogje.state.Beurt;
import com.cegeka.cabot.oorlogje.state.Kaart;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.cegeka.cabot.oorlogje.state.Beurt.beurt;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OorlogjeInterfaceTest extends UnitTest{

    @InjectMocks
    private OorlogjeInterface oorlogjeInterface;
    @Mock
    private OorlogjePlayer oorlogjePlayer;
    @Mock
    private RewardCalculator rewardCalculator;

    @Test
    public void bepaalTeSpelenActie_ShouldCallMachineLearningInterfaceToGetCorrectCardAndGiveFeedbackAboutReward() {
        Kaart teSpelenKaartBepaaldDoorMachine = new Kaart(3);
        Beurt beurt = beurt().withGespeeldeKaarten(newArrayList())
                .withHandkaarten(newArrayList(new Kaart(2), teSpelenKaartBepaaldDoorMachine, new Kaart(1)))
                .withTegenstanderGespeeldeKaarten(newArrayList());
        when(oorlogjePlayer.bepaalActieOmTeSpelen(beurt)).thenReturn(teSpelenKaartBepaaldDoorMachine);
        when(rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, teSpelenKaartBepaaldDoorMachine)).thenReturn(41);

        Kaart kaartTeSpelen = oorlogjeInterface.bepaalTeSpelenKaart(beurt);

        assertThat(kaartTeSpelen).isEqualTo(teSpelenKaartBepaaldDoorMachine);
        verify(oorlogjePlayer).bepaalActieOmTeSpelen(beurt);
        verify(oorlogjePlayer).kenRewardToeVoorGespeeldeActie(Mockito.any(Beurt.class), beurt, teSpelenKaartBepaaldDoorMachine, 41);
    }
}