package com.modelplayground.server.service;

import com.modelplayground.server.algorithms.persistentranking.application.LexoRankManager;
import com.modelplayground.server.algorithms.persistentranking.application.RelativeRanking;
import com.modelplayground.server.algorithms.persistentranking.domain.DescEntity;
import com.modelplayground.server.algorithms.persistentranking.domain.IntegerRank;
import com.modelplayground.server.algorithms.persistentranking.application.IntegerRankManager;
import com.modelplayground.server.algorithms.persistentranking.application.RankManager;
import com.modelplayground.server.algorithms.persistentranking.domain.LexoRank;
import com.modelplayground.server.algorithms.persistentranking.domain.Rank;
import com.modelplayground.server.utils.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/lexorank")
public class Lexorank {

    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi");
    }



    Map<String, RelativeRanking> managers ;

    @PostConstruct
    public void init(){
        managers = new HashMap<>();
    }

    @RequestMapping("/createmanager/{type}/{name}")
    public ResponseEntity createManager(@PathVariable("type") String type,@PathVariable("name") String name) {
        switch(type){
            case "lexorank":
                RankManager<LexoRank> lexoRankRankManager = new LexoRankManager();
                RelativeRanking<LexoRank> lexoRankRelativeRanking = new RelativeRanking<>(lexoRankRankManager);
                managers.put(name,lexoRankRelativeRanking);
                break;

            case "integerrank":
                RankManager<IntegerRank> integerRankRankManager = new IntegerRankManager();
                RelativeRanking<IntegerRank> integerRankRelativeRanking = new RelativeRanking<>(integerRankRankManager);
                managers.put(name,integerRankRelativeRanking);
                break;
        }
        return ResponseEntity.ok(name + " created");
    }

    @RequestMapping("{name}/addrandomissue/{count}")
    public ResponseEntity addIssue(@PathVariable("name") String name,@PathVariable("count") Integer count){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        IntStream.range(0,10).forEach(i->{
            DescEntity descEntity = new DescEntity("issue: "+i);
            relativeRanking.add(descEntity);
        });
        return ResponseEntity.ok("added "+count +" issues");
    }
    @RequestMapping("{name}/addissue/{desc}")
    public ResponseEntity addIssue(@PathVariable("name") String name,@PathVariable("desc") String desc){
        DescEntity descEntity = new DescEntity(desc);
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.add(descEntity));
    }

    @RequestMapping("/{name}/move/{from}/{to}")
    public ResponseEntity move(@PathVariable("name") String name,@PathVariable("from") Integer from,@PathVariable("to") Integer to){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.move(from,to));
    }


    @RequestMapping("{name}/rabalance")
    public ResponseEntity rebalance(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.rebalance());
    }

    @RequestMapping("/{name}/getall")
    public ResponseEntity getAll(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(getAllForManager(relativeRanking));
    }

    @RequestMapping("/{name}/getalldb")
    public ResponseEntity getAllDB(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
           return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(getAllFromDBForRankManager(relativeRanking));
    }




    public String getAllForManager(RelativeRanking<Rank> relativeRanking){
        String res = relativeRanking.getAllEntityByRank()
                .stream()
                .map(en->en.getId() + " "+ en.getRank())
                .reduce((a,b)->a+"\n"+b)
                .orElse("Nothing");
        return res;
    }

    public String getAllFromDBForRankManager(RelativeRanking<Rank> relativeRanking){
        String res = "<html>";
        res+=relativeRanking.getAllEntityByRankFromDB()
                .stream()
                .map(en->en.getId() + " "+ en.getRank()+"<br>")
                .reduce((a,b)->a+"<br>"+b)
                .orElse("Nothing");
        res+="</html>";
        return res;
    }
}
