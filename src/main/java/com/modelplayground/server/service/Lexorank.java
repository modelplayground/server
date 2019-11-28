package com.modelplayground.server.service;

import com.modelplayground.server.algorithms.persistentranking.application.*;
import com.modelplayground.server.algorithms.persistentranking.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/lexorank")
public class Lexorank {

    @CrossOrigin
    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi");
    }

    @CrossOrigin
    @RequestMapping("/entity")
    public ResponseEntity getSampleEntity(){
        SampleResponse sampleResponse = new SampleResponse("Hi");
        return ResponseEntity.ok(sampleResponse);
    }



    Map<String, RelativeRanking> managers ;

    @PostConstruct
    public void init(){
        managers = new HashMap<>();
    }

    @CrossOrigin
    @RequestMapping("/createmanager/{type}/{name}")
    public ResponseEntity createManager(@PathVariable("type") String type,@PathVariable("name") String name) {
        switch(type){
            case "lexorank":
                RankManager<LexoRank> lexoRankManager = new LexoRankManager();
                RelativeRanking<LexoRank> lexoRankRelativeRanking = new RelativeRanking<>(lexoRankManager);
                managers.put(name,lexoRankRelativeRanking);
                break;

            case "lexorankmiddle":
                RankManager<LexoRankMiddle> lexoRankMiddleManager = new LexoRankMiddleManager();
                RelativeRanking<LexoRankMiddle> lexoRankMiddleRelativeRanking = new RelativeRanking<>(lexoRankMiddleManager);
                managers.put(name,lexoRankMiddleRelativeRanking);
                break;

            case "integerrank":
                RankManager<IntegerRank> integerRankRankManager = new IntegerRankManager();
                RelativeRanking<IntegerRank> integerRankRelativeRanking = new RelativeRanking<>(integerRankRankManager);
                managers.put(name,integerRankRelativeRanking);
                break;
        }
        return ResponseEntity.ok(name + " created");
    }

    @CrossOrigin
    @RequestMapping("{name}/addrandomissue/{count}")
    public ResponseEntity addIssue(@PathVariable("name") String name,@PathVariable("count") Integer count){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        IntStream.range(0,10).forEach(i->{
            Issue issue = new Issue("issue: "+i);
            relativeRanking.add(issue);
        });
        return ResponseEntity.ok("added "+count +" issues");
    }
    @CrossOrigin
    @RequestMapping("{name}/addissue/{desc}")
    public ResponseEntity addIssue(@PathVariable("name") String name,@PathVariable("desc") String desc){
        Issue issue = new Issue(desc);
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.add(issue));
    }

    @CrossOrigin
    @RequestMapping("/{name}/move/{from}/{to}")
    public ResponseEntity move(@PathVariable("name") String name,@PathVariable("from") Integer from,@PathVariable("to") Integer to){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.move(from,to));
    }

    @CrossOrigin
    @RequestMapping("{name}/rebalance")
    public ResponseEntity rebalance(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(relativeRanking.rebalance());
    }

    @CrossOrigin
    @RequestMapping("/{name}/getall")
    public ResponseEntity getAll(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
            return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(getAllForManagerAsList(relativeRanking));
    }

    @CrossOrigin
    @RequestMapping("/{name}/getalldb")
    public ResponseEntity getAllDB(@PathVariable("name") String name){
        RelativeRanking relativeRanking = managers.getOrDefault(name,null);
        if(relativeRanking == null ){
           return ResponseEntity.ok("No manager with name : "+name+ " found");
        }
        return ResponseEntity.ok(getAllFromDBForRankManagerAsList(relativeRanking));
    }



    public List<Entity<Rank>> getAllForManagerAsList(RelativeRanking<Rank> relativeRanking){
        return  relativeRanking.getAllEntityByRank();
    }

    public List<Entity<Rank>> getAllFromDBForRankManagerAsList(RelativeRanking<Rank> relativeRanking){
        return relativeRanking.getAllEntityByRankFromDB();

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
