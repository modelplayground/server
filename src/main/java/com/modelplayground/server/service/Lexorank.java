package com.modelplayground.server.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lexorank")
public class Lexorank {

    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi");
    }


}
