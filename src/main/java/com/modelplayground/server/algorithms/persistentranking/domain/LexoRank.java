package com.modelplayground.server.algorithms.persistentranking.domain;


import java.util.Objects;

public class LexoRank extends Rank {
    Integer bucket;
    String marker;
    String rank;

    public LexoRank(Integer bucket, String marker, String rank) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LexoRank)) return false;
        LexoRank lexoRank = (LexoRank) o;
        return bucket == lexoRank.bucket &&
                Objects.equals(marker, lexoRank.marker) &&
                Objects.equals(rank, lexoRank.rank);
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
        if (!(o instanceof LexoRank)){
            System.out.println("Comparing Different types of ranks !!");
            return -1;
        }

        LexoRank  that = (LexoRank) o;
        return this.toString().compareTo(that.toString());
    }
}
