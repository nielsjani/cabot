package com.cegeka.cabot.tictactoe.player;

import com.cegeka.cabot.api.AIGameStateConverter;
import com.cegeka.cabot.tictactoe.domain.TicTacToeAction;
import com.cegeka.cabot.tictactoe.domain.TicTacToeGameState;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class TicTacToeAIGameStateConverter implements AIGameStateConverter<TicTacToeGameState, TicTacToeAction> {

    @Override
    public Integer toStateValue(TicTacToeGameState ticTacToeGameState) {
        List<Integer> cellValues = Lists.newArrayList();
        for (int row=0; row < 3; row++) {
            for (int column=0; column < 3; column++) {
                Integer cellValue = ticTacToeGameState.getPlayingField()[row][column];
                cellValues.add(cellValue == null? 0 : cellValue);
            }
        }

        return concatIntegerListIntoInt(cellValues);
    }

    @Override
    public Set<Integer> toActionValues(Set<TicTacToeAction> ticTacToeActions) {
        return ticTacToeActions.stream()
                .map(this::toActionValue)
                .collect(toSet());
    }

    @Override
    public Integer toActionValue(TicTacToeAction action) {
        return concatIntegerListIntoInt(Lists.newArrayList(action.getRowColumnCoordinate().getLeft(), action.getRowColumnCoordinate().getRight()));
    }

    private static int concatIntegerListIntoInt(List<Integer> integerList) {
        return IntStream.range(0, integerList.size())
                .mapToObj(i -> Pair.of(integerList.get(i), i))
                .mapToInt(valueAndIndex ->  Double.valueOf(valueAndIndex.getLeft() * Math.pow(10, (integerList.size() - 1 - valueAndIndex.getRight()))).intValue())
                .sum();
    }

}
