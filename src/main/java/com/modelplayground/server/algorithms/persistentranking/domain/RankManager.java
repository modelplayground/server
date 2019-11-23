package com.modelplayground.server.algorithms.persistentranking.domain;

public abstract class RankManager<R extends Rank> {

    protected boolean changeAllowed =true;

    public boolean isScheduleRebalancing() {
        return scheduleRebalancing;
    }

    public void setScheduleRebalancing(boolean scheduleRebalancing) {
        this.scheduleRebalancing = scheduleRebalancing;
    }

    protected boolean scheduleRebalancing =false
            ;

    public boolean isChangeAllowed() {
        return changeAllowed;
    }

    public void setChangeAllowed(boolean changeAllowed) {
        this.changeAllowed = changeAllowed;
    }

    public abstract R getMin();

    public abstract R getMax();

    public abstract R calculate(R prevRank, R nextRank);

    public abstract R getUniformRankForPosition(int pos);



    public abstract void rebalancingStart();
    public abstract void rebalancingComplete();

}
