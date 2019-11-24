package com.modelplayground.server.algorithms.persistentranking.application;

import com.modelplayground.server.algorithms.persistentranking.domain.LexoRankMiddle;
import com.modelplayground.server.utils.StringUtils;

public class LexoRankMiddleManager extends RankManager<LexoRankMiddle> {

    private  Integer rebalanceBucket = 0;
    private  Integer currentBucket = 0;
    String marker = "abcd";

    private final int maxLenAllowed = 3;
    private final String MIN_VAL = "aaa";
    private final String MAX_VAL = "zzz";
    private final LexoRankMiddle MIN_RANK = new LexoRankMiddle(currentBucket,marker,MIN_VAL);
    private final LexoRankMiddle MAX_RANK = new LexoRankMiddle(currentBucket,marker,MAX_VAL);

    private String maxAssignedRankVal = MAX_VAL;

    @Override
    public LexoRankMiddle getMin() {
        return this.MIN_RANK;
    }

    @Override
    public LexoRankMiddle getMax() {
        return this.MAX_RANK;
    }

    @Override
    public LexoRankMiddle calculate(LexoRankMiddle prevRank, LexoRankMiddle nextRank) {
        if(prevRank.getBucket().equals(nextRank.getBucket())  ){
            if(prevRank.getMarker().equals(nextRank.getMarker())){
                String newRank = StringUtils.getLexographicMiddleString(prevRank.getRank(),nextRank.getRank());
                if(newRank.length()>maxLenAllowed){
                    setChangeAllowed(false);
                    setScheduleRebalancing(true);
                }
                return new LexoRankMiddle(currentBucket,marker,newRank);
            }
        }else{
            // handle insertion while rebalancing
        }

        return null;
    }

    @Override
    public LexoRankMiddle getUniformRankForPosition(int pos) {
        if(pos<=0) return null;
        int numOfDigits = 0;
        int totalSpace = 1;
        int tmpPos = pos;
        while(tmpPos>0){
            numOfDigits++;
            int div = 1;
            if(maxAssignedRankVal.length()>numOfDigits-1){
                div =maxAssignedRankVal.charAt(numOfDigits-1)-'a'+1;
            }else{
                div =26;
            }
            System.out.println(div);
            totalSpace*=div;
            tmpPos/=div;
        }
        int spacing = (totalSpace-1)/pos;

        String endRank =null;
        if(maxAssignedRankVal.length()>numOfDigits){
            endRank = maxAssignedRankVal.substring(0,numOfDigits);
        }else{
            endRank = maxAssignedRankVal;
            while(endRank.length()<numOfDigits){
                endRank+='a';
            }
        }
        LexoRankMiddle newRank = new LexoRankMiddle(rebalanceBucket,marker,endRank);
        newRank.decrement(spacing);
        maxAssignedRankVal = newRank.getRank();
        return newRank;
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
