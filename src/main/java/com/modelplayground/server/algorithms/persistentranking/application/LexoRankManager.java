package com.modelplayground.server.algorithms.persistentranking.application;

import com.modelplayground.server.algorithms.persistentranking.domain.LexoRank;
import com.modelplayground.server.utils.StringUtils;

public class LexoRankManager extends RankManager<LexoRank> {

    private  Integer rebalanceBucket = 0;
    private  Integer currentBucket = 0;
    String marker = "abcd";

    private final int maxLenAllowed = 3;
    private final int totalSpace = (int)(Math.pow(26,maxLenAllowed))-2;
    private final String MIN_VAL = "aaa";
    private final String MAX_VAL = "zzz";
    private  LexoRank MIN_RANK = new LexoRank(currentBucket,marker,MIN_VAL);
    private  LexoRank MAX_RANK = new LexoRank(currentBucket,marker,MAX_VAL);

    private  int increment = 8;

    private String maxAssignedRankVal = MAX_VAL;

    @Override
    public LexoRank getMin() {
        return this.MIN_RANK;
    }

    @Override
    public LexoRank getMax() {
        return this.MAX_RANK;
    }
/*
possible bucket scenarios :
old old
old new
new new
 */
    @Override
    public LexoRank calculate(LexoRank prevRank, LexoRank nextRank) {
        LexoRank nextRankToUse = nextRank;
        if(!prevRank.getBucket().equals(nextRank.getBucket())  ){
            // handle insertion while rebalancing
           nextRankToUse = new LexoRank(prevRank.getBucket(),prevRank.getMarker(),MAX_VAL);
        }
        String newRank = null;
        int val = increment;

        while(val>0 && newRank==null){
            String tmp = StringUtils.increment(prevRank.getRank(),val);
            if(tmp != null && tmp.compareTo(nextRankToUse.getRank())<0){
                newRank = tmp;
            }
            val/=2;
        }
        if(newRank==null){
            newRank = StringUtils.getLexographicMiddleString(prevRank.getRank(),nextRankToUse.getRank());
        }
        if(newRank.length()>maxLenAllowed){
            setChangeAllowed(false);
            setScheduleRebalancing(true);
        }
        return new LexoRank(currentBucket,marker,newRank);

    }

    @Override
    public LexoRank getUniformRankForPosition(int pos) {
        int spacing = StringUtils.calculateSpace(MIN_VAL,maxAssignedRankVal)/pos;
        maxAssignedRankVal= StringUtils.decrement(maxAssignedRankVal,spacing);
        return new LexoRank(rebalanceBucket,marker,maxAssignedRankVal);
    }

    @Override
    public void rebalancingStart() {
        setScheduleRebalancing(false);
        maxAssignedRankVal = MAX_VAL;
        rebalanceBucket = (currentBucket+1) % 3;
        MAX_RANK.setBucket(rebalanceBucket);
    }

    @Override
    public void rebalancingComplete() {
        currentBucket = rebalanceBucket;
        MIN_RANK.setBucket(currentBucket);
        setChangeAllowed(true);

    }
}
