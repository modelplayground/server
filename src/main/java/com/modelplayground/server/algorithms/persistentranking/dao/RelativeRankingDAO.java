package com.modelplayground.server.algorithms.persistentranking.dao;

import com.modelplayground.server.algorithms.persistentranking.domain.Rank;


import com.modelplayground.server.utils.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RelativeRankingDAO{
    private Map<String, Rank> database =new HashMap<>();

    public void save(String id, Rank rank){
        database.put(id,rank);
    }

    public Rank get(String id){
        return database.get(id);
    }

    public List<Pair<String, Rank>> getAll(){
        return database.entrySet()
                .stream()
                .map(k->  new Pair<String, Rank>(k.getKey(),k.getValue()))
                .collect(Collectors.toList());
    }

}
