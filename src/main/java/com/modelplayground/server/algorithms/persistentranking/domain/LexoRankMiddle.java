package com.modelplayground.server.algorithms.persistentranking.domain;


import java.util.Objects;

public class LexoRankMiddle extends Rank {

    Integer bucket;
    String marker;
    String rank;

    public LexoRankMiddle(Integer bucket, String marker, String rank) {
        this.bucket = bucket;
        this.marker = marker;
        this.rank = rank;
    }

    public Integer getBucket() {
        return bucket;
    }

    public void setBucket(Integer bucket) {
        this.bucket = bucket;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }



    @Override
    public void increment(long val) {

    }

    @Override
    public void decrement(long val) {
        StringBuilder str = new StringBuilder(rank);
        int i=rank.length()-1;
        while(val>0){
            long count = val%26;
            if(str.charAt(i)-'a'>=count){
                str.setCharAt(i,(char)(str.charAt(i)-count) );
            }else{
                if(i-1>=0){
                    str.setCharAt(i-1,(char)(str.charAt(i-1)-1));
                    count -= str.charAt(i)-'a';
                    str.setCharAt(i,(char)('z'-count+1));
                }else{
                    System.out.println("error");
                }
            }
            i--;
            val/=26;
        }
        this.setRank(str.toString());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LexoRankMiddle)) return false;
        LexoRankMiddle lexoRankMiddle = (LexoRankMiddle) o;
        return bucket == lexoRankMiddle.bucket &&
                Objects.equals(marker, lexoRankMiddle.marker) &&
                Objects.equals(rank, lexoRankMiddle.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, marker, rank);
    }

    @Override
    public String toString() {
        return bucket+"|"+marker+"|"+rank;
    }

    @Override
    public int compareTo(Rank o) {
        if (this == o) return 0;
        if (!(o instanceof LexoRankMiddle)){
            System.out.println("Comparing Different types of ranks !!");
            return -1;
        }

        LexoRankMiddle that = (LexoRankMiddle) o;
        return this.toString().compareTo(that.toString());
    }
}
