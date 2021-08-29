package ua.comsys.kpi.snailboard.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health-check")
public class TestController {

    @GetMapping
    public String healthCheck() {
        return "OK";
    }

}
