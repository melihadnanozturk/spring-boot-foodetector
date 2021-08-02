package com.mao.foodetector.controller;

import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.service.DesertService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deserts")
public class DesertController {

    private DesertService desertService;

    @Autowired
    public DesertController(DesertService desertService){
        this.desertService=desertService;
    }

    @GetMapping
    public Iterable<DesertResponse> getAll(){
        return desertService.getAll(new ArrayList<>(),new DesertResponse());
    }

    @GetMapping("/{desertname}")
    public DesertResponse getOne(@PathVariable String desertname){
        return desertService.getOne(desertname);
    }


    @PutMapping("/{desertname}/{newname}")
    public DesertResponse updateName(@PathVariable String desertname, @PathVariable String newname){
        return  desertService.updateName(desertname,newname);
    }

    @DeleteMapping("/{desertname}")
    public DoneResponse delete(@PathVariable String desertname){
        return desertService.delete(desertname);
    }

    @PostMapping
    public ResponseEntity<?> newDesert(@RequestBody @Valid DesertRequest request){
        desertService.newDesert(request);
        return ResponseEntity.ok("NewDesert added.");
    }

}
