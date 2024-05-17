package com.ecw.admin.controller;

import com.ecw.admin.dto.ResponseListObj;
import com.ecw.admin.service.PrizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prizemaster")
public class PrizeMasterController {

    PrizeService prizeService;

    public PrizeMasterController(PrizeService prizeService) {
        this.prizeService = prizeService;
    }
    @GetMapping("/getall")
    public ResponseEntity<ResponseListObj> getAll(){
        return prizeService.findAllPrizeList();
    }
    @GetMapping("/getname")
    public ResponseEntity<ResponseListObj> getName(){
        return prizeService.findNameList();
    }


}
