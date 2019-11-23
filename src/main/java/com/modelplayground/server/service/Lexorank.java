package com.modelplayground.server.service;

import com.modelplayground.server.algorithms.persistentranking.application.RelativeRanking;
import com.modelplayground.server.algorithms.persistentranking.domain.IntEntity;
import com.modelplayground.server.algorithms.persistentranking.domain.IntegerRank;
import com.modelplayground.server.algorithms.persistentranking.domain.IntegerRankManager;
import com.modelplayground.server.algorithms.persistentranking.domain.RankManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/lexorank")
public class Lexorank {

    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi");
    }

    RankManager<IntegerRank> rankManager = new IntegerRankManager();
    RelativeRanking<IntegerRank> relativeRanking = new RelativeRanking<>(rankManager);

    {

        IntStream.range(0,10).forEach(i->{
            IntEntity intEntity = new IntEntity("issue: "+i);
            relativeRanking.add(intEntity);
        });

    }
    @RequestMapping("/addissue/{desc}")
    public ResponseEntity addIssue(@PathVariable("desc") String desc){
        IntEntity intEntity = new IntEntity(desc);

        return ResponseEntity.ok(relativeRanking.add(intEntity));
    }

    @RequestMapping("/move/{from}/{to}")
    public ResponseEntity move(@PathVariable("from") Integer from,@PathVariable("to") Integer to){
        return ResponseEntity.ok(relativeRanking.move(from,to));
    }


    @RequestMapping("/rabalance")
    public ResponseEntity rebalance(){
        return ResponseEntity.ok(relativeRanking.rebalance());
    }

    @RequestMapping("/getall")
    public ResponseEntity getAll(){
        String res = relativeRanking.getAllEntityByRank()
                .stream()
                .map(en->en.getId() + " "+ en.getRank())
                .reduce((a,b)->a+"\n"+b)
                .orElse("Nothing");
        return ResponseEntity.ok(res);
    }

    @RequestMapping("/getalldb")
    public ResponseEntity getAllDB(){
        String res = "<html>";
        res+=relativeRanking.getAllEntityByRankFromDB()
                .stream()
                .map(en->en.getId() + " "+ en.getRank()+"<br>")
                .reduce((a,b)->a+"<br>"+b)
                .orElse("Nothing");
        res+="</html>";
        return ResponseEntity.ok(res);
    }
}
