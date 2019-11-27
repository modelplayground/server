package com.modelplayground.server.algorithms.persistentranking.domain;

public class Issue<R extends Rank> extends  Entity<R> {

    String desc = "";

    public Issue(String desc) {
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
