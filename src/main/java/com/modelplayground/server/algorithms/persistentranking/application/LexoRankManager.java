package com.modelplayground.server.algorithms.persistentranking.application;

import com.modelplayground.server.algorithms.persistentranking.application.RankManager;
import com.modelplayground.server.algorithms.persistentranking.domain.LexoRank;
import com.modelplayground.server.utils.StringUtils;

public class LexoRankManager extends RankManager<LexoRank> {

    private  Integer rebalanceBucket = 0;
    private  Integer currentBucket = 0;
    String marker = "abcd";

    private final int maxLenAllowed = 3;
    private final String MIN_VAL = "aaa";
    private final String MAX_VAL = "zzz";
    private final LexoRank MIN_RANK = new LexoRank(currentBucket,marker,MIN_VAL);
    private final LexoRank MAX_RANK = new LexoRank(currentBucket,marker,MAX_VAL);

    private String maxAssignedRankVal = MAX_VAL;

    @Override
    public LexoRank getMin() {
        return this.MIN_RANK;
    }

    @Override
    public LexoRank getMax() {
        return this.MAX_RANK;
    }

    @Override
    public LexoRank calculate(LexoRank prevRank, LexoRank nextRank) {
        if(prevRank.getBucket().equals(nextRank.getBucket())  ){
            if(prevRank.getMarker().equals(nextRank.getMarker())){
                String newRank = StringUtils.getLexographicMiddleString(prevRank.getRank(),nextRank.getRank());
                if(newRank.length()>maxLenAllowed){
                    setChangeAllowed(false);
                    setScheduleRebalancing(true);
                }
                return new LexoRank(currentBucket,marker,newRank);
            }
        }else{
            // handle insertion while rebalancing
        }

        return null;
    }

    @Override
    public LexoRank getUniformRankForPosition(int pos) {
        int numOfDigits = 0;
        long totalSpace = 1;
        while(pos>0){
            numOfDigits++;
            totalSpace*=26;
            pos/=26;
        }
        long spacing = totalSpace/pos;
        LexoRank newRank = new LexoRank(rebalanceBucket,marker,maxAssignedRankVal);
        newRank.decrement(spacing);
        return newRank;
    }

    @Override
    public void rebalancingStart() {
        setScheduleRebalancing(false);
        maxAssignedRankVal = MAX_VAL;
        rebalanceBucket = (currentBucket+1) % 3;
    }

    @Override
    public void rebalancingComplete() {
        currentBucket = rebalanceBucket;
        setChangeAllowed(true);

    }
}
