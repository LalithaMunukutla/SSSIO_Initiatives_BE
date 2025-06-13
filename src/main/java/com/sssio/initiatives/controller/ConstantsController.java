package com.sssio.initiatives.controller;

import com.sssio.initiatives.response.Constants;
import com.sssio.initiatives.service.ConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstantsController {

    @Autowired
    private ConstantsService constantsService;

    @GetMapping("api/constants")
    public ResponseEntity<Constants> getConstants(){
        try{
            return ResponseEntity.ok(constantsService.getConstants());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
