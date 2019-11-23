package com.modelplayground.server.algorithms.persistentranking.domain;

public class IntegerRankManager extends RankManager<IntegerRank> {
    private final static Integer MIN_VAL = 0;
    private final static Integer MAX_VAL = 1000;
    private final static IntegerRank MIN_RANK = new IntegerRank(MIN_VAL);
    private final static IntegerRank MAX_RANK = new IntegerRank(MAX_VAL);

    private int maxAssignedRankVal = MAX_VAL;
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
        Integer newRank = Math.floorDiv(prevRank.getRank()+nextRank.getRank(),2);
        if(newRank-prevRank.getRank()<=2 || nextRank.getRank()-newRank<=2){
            setChangeAllowed(false);
            setScheduleRebalancing(true);
        }
        return new IntegerRank(newRank);
    }

    @Override
    public IntegerRank getUniformRankForPosition(int pos) {
        int spacing = (maxAssignedRankVal - MIN_VAL -1)/(pos+1);
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
