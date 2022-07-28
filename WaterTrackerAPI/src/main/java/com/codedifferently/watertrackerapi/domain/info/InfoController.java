package com.codedifferently.watertrackerapi.domain.info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
public class InfoController {
    @GetMapping
    public String test(){
        return "test";
    }
}
