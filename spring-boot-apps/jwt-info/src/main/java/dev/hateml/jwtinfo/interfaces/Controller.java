package dev.hateml.jwtinfo.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("/getTokenInfo")
    public String getTokenInfo(@RequestParam String token) {
        String payload = token.split("\\.")[1]
                .replace("-", "+")
                .replace("_", "/");
        return new String(Base64.getDecoder().decode(payload.getBytes()));
    }
}
