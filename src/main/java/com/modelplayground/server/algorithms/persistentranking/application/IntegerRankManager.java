package com.modelplayground.server.algorithms.persistentranking.application;

import com.modelplayground.server.algorithms.persistentranking.domain.IntegerRank;

public class IntegerRankManager extends RankManager<IntegerRank> {
    private final static Long MIN_VAL = 0L;
    private final static Long MAX_VAL = 1000L;
    private final static IntegerRank MIN_RANK = new IntegerRank(MIN_VAL);
    private final static IntegerRank MAX_RANK = new IntegerRank(MAX_VAL);

    private long maxAssignedRankVal = MAX_VAL;
    @Override
    public IntegerRank getMin() {
        return MIN_RANK;
    }

    @Override
    public IntegerRank getMax() {
        return MAX_RANK;
    }

    @Override
    public IntegerRank calculate(IntegerRank prevRank, IntegerRank nextRank) {
        Long newRank = Math.floorDiv(prevRank.getRank()+nextRank.getRank(),2);
        if(newRank-prevRank.getRank()<=2 || nextRank.getRank()-newRank<=2){
            setChangeAllowed(false);
            setScheduleRebalancing(true);
        }
        return new IntegerRank(newRank);
    }

    @Override
    public IntegerRank getUniformRankForPosition(int pos) {
        long spacing = (maxAssignedRankVal - MIN_VAL -1)/(pos+1);
        maxAssignedRankVal = maxAssignedRankVal - spacing;
        return new IntegerRank(maxAssignedRankVal);
    }

    @Override
    public void rebalancingStart() {
        setScheduleRebalancing(false);
        maxAssignedRankVal = MAX_VAL;
    }

    @Override
    public void rebalancingComplete() {
        setChangeAllowed(true);
    }


}
