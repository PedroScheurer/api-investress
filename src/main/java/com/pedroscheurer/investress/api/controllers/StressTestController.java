package com.pedroscheurer.investress.api.controllers;

import com.pedroscheurer.investress.api.model.RiskMetricResult;
import com.pedroscheurer.investress.api.model.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.RiskMetricType;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import com.pedroscheurer.investress.api.services.StressTestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stress-test")
public class StressTestController {

    private final StressTestService service;

    public StressTestController(StressTestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<RiskMetricResult>> stressTest(@RequestParam StressTestType scenario,
                                                             @RequestParam List<RiskMetricType> metrics){
        StressTestContext context = new StressTestContext();
        return ResponseEntity.ok(service.run(scenario, metrics, context));
    }
}
