package com.cegeka.cabot.oorlogje.api;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.api.MachineLearningInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;
import com.cegeka.cabot.oorlogje.reward.RewardCalculator;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.cegeka.cabot.api.beurt.Beurt.beurt;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OorlogjeInterfaceTest extends UnitTest{

    @InjectMocks
    private OorlogjeInterface oorlogjeInterface;
    @Mock
    private MachineLearningInterface machineLearningInterface;
    @Mock
    private RewardCalculator rewardCalculator;

    @Test
    public void bepaalTeSpelenKaart_ShouldCallMachineLearningInterfaceToGetCorrectCardAndGiveFeedbackAboutReward() {
        Kaart teSpelenKaartBepaaldDoorMachine = new Kaart(3);
        Beurt beurt = beurt().withBotGespeeldeKaarten(newArrayList())
                .withBotHandkaarten(newArrayList(new Kaart(2), teSpelenKaartBepaaldDoorMachine, new Kaart(1)))
                .withMensGespeeldeKaarten(newArrayList());
        when(machineLearningInterface.getTeSpelenKaartVoor(beurt)).thenReturn(teSpelenKaartBepaaldDoorMachine);
        when(rewardCalculator.bepaalRewardVoorGespeeldeKaart(beurt, teSpelenKaartBepaaldDoorMachine)).thenReturn(41);

        Kaart kaartTeSpelen = oorlogjeInterface.bepaalTeSpelenKaart(beurt);

        assertThat(kaartTeSpelen).isEqualTo(teSpelenKaartBepaaldDoorMachine);
        verify(machineLearningInterface).getTeSpelenKaartVoor(beurt);
        verify(machineLearningInterface).geefRewardVoorBeurt(beurt, teSpelenKaartBepaaldDoorMachine, 41);
    }
}