package com.modelplayground.server.algorithms.persistentranking.domain;

public class IntEntity extends  Entity<IntegerRank> {

    String desc = "";

    public IntEntity(String desc) {
        this.desc = desc;
        this.id = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
