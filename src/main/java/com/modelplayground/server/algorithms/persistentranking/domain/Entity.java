package com.modelplayground.server.algorithms.persistentranking.domain;

import java.util.Objects;

public class Entity<R extends Rank> {
    String id;
    R rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public R getRank() {
        return rank;
    }

    public void setRank(R rank) {
        this.rank = rank;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", rank=" + rank +
                '}';
    }
}
