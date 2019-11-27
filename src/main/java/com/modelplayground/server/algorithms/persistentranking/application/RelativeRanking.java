package com.modelplayground.server.algorithms.persistentranking.application;

import com.modelplayground.server.algorithms.persistentranking.dao.RelativeRankingDAO;
import com.modelplayground.server.algorithms.persistentranking.domain.Entity;
import com.modelplayground.server.algorithms.persistentranking.domain.LexoRank;
import com.modelplayground.server.algorithms.persistentranking.domain.Rank;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RelativeRanking<R extends Rank> {

    RelativeRankingDAO relativeRankingDAO;

    LinkedList<Entity<R>> entities;
    RankManager<R> rankManager;

    public RelativeRanking(RankManager<R> rankManager) {
        this.relativeRankingDAO = new RelativeRankingDAO();
        this.entities = new LinkedList<>();
        this.rankManager = rankManager;
    }

    public boolean move(int fromIndex,int toIndex){
        Entity<R> entity = this.peek(fromIndex);
        if(this.remove(fromIndex)){
            return this.addAt(toIndex,entity);
        }
        return false;

    }

    public Entity<R> peek(int index) {
        if (index < 0 || index >= entities.size()) {
            return null;
        }
        return entities.get(index);

    }

    public boolean remove(int index) {
        if (index < 0 || index >= entities.size()) {
            return false;
        }
        entities.remove(index);
        return  true;
    }

    public boolean addAt(int index, Entity<R> entity) {
        if(rankManager.isChangeAllowed()){
            ListIterator<Entity<R>> listIterator = entities.listIterator(index);
            R prevRank = rankManager.getMin();
            R nextRank = rankManager.getMax();
            if (listIterator.hasPrevious()) {
                prevRank = listIterator.previous().getRank();
                listIterator.next();
            }

            if (listIterator.hasNext()) {
                nextRank = listIterator.next().getRank();
            }
            entity.setRank(rankManager.calculate(prevRank, nextRank));
            entities.add(index, entity);
            relativeRankingDAO.save(entity.getId(),entity.getRank());
            if(rankManager.isScheduleRebalancing()){
                return this.rebalance();
            }else{
                return true;
            }
        }
        return false;
    }

    public boolean add(Entity entity) {
        return this.addAt(entities.size(), entity);
    }

    public boolean rebalance() {
        rankManager.rebalancingStart();
        AtomicInteger entitiesRebalanced = new AtomicInteger(0);
        entities.descendingIterator().forEachRemaining(entity -> {
                    entity.setRank(rankManager.getUniformRankForPosition(entities.size()-entitiesRebalanced.get()));
                    relativeRankingDAO.save(entity.getId(),entity.getRank());
                    entitiesRebalanced.getAndIncrement();
                });
        rankManager.rebalancingComplete();
        return true;
    }

    public List<Entity<R>> getAllEntityByRank() {
        return this.entities;
    }

    public List<Entity<Rank>> getAllEntityByRankFromDB() {
        return this.relativeRankingDAO.getAll()
                .stream().sorted()
                .map(p->{
            Entity<Rank> entity = new Entity();
            entity.setId(p.getKey());
            entity.setRank(p.getValue());
            return entity;
        })
                .collect(Collectors.toList());
    }


}
