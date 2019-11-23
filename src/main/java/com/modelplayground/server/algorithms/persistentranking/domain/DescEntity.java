package com.modelplayground.server.algorithms.persistentranking.domain;

public class DescEntity<R extends Rank> extends  Entity<R> {

    String desc = "";

    public DescEntity(String desc) {
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
