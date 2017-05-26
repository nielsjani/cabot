package com.cegeka.cabot.api;

import com.cegeka.cabot.UnitTest;
import com.cegeka.cabot.oorlogje.startsituatie.StartSituatie;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;

public class GameEngineInterfaceTest extends UnitTest {

    @InjectMocks
    private GameEngineInterface gameEngineInterface;

    @Test
    public void getStartSituatie() {
        StartSituatie startSituatie = gameEngineInterface.getStartSituatie();
        Assertions.assertThat(startSituatie).isNotNull();
    }

}