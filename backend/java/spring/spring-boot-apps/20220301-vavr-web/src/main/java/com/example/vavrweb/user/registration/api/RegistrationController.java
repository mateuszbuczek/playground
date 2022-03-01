package com.example.vavrweb.user.registration.api;

import com.example.vavrweb.user.registration.domain.RegisteredUser;
import com.example.vavrweb.user.registration.domain.RegistrationFacade;
import com.example.vavrweb.user.registration.domain.RegistrationValidator;
import io.vavr.API;
import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.vavr.API.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationFacade registrationFacade;

    @GetMapping
    public List<RegisteredUser> findAll() {
        return registrationFacade.findAll().toJavaList();
    }

    @PostMapping
    public Object register(@RequestBody RegistrationModel model) {
        return registrationFacade.create(model.toRegistrationApplication())
                .fold(Value::toJavaList, right -> right);
    }
}

