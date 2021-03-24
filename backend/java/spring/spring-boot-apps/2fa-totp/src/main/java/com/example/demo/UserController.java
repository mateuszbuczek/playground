package com.example.demo;

import com.example.demo.totp.TotpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TotpService totpService;
    private final UserInMemoryRepository repository;

    @GetMapping("/signup")
    public ResponseEntity<byte[]> signup(@RequestParam("accountName") String accountName) {
        String secretKey = totpService.createSecretKey();
        repository.save(accountName, secretKey);
        byte[] qrCode = totpService.createQrCode(accountName, secretKey);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.put("Content-type", List.of("image/png"));
        responseHeaders.put("Content-length", List.of(String.valueOf(qrCode.length)));
        return new ResponseEntity<>(qrCode, responseHeaders, 200);
    }

    @GetMapping("/signin")
    public ResponseEntity<String> signin(@RequestParam("accountName") String accountName,
                                         @RequestParam("token") String token) {
        String secretKey = repository.getSecretKey(accountName);
        boolean result = totpService.validate(secretKey, token);

        return result ?
                ResponseEntity.ok("success")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("failure");
    }
}
