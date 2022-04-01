package com.example.springshellnative;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Component
class LoginService {
    private final AtomicReference<String> user = new AtomicReference<>();

    void login(String username) {
        this.user.set(username);
    }

    void logout() {
        this.user.set(null);
    }

    boolean isLoggedIn() {
        return this.user.get() != null;
    }

    String loggedInUser() {
        return this.user.get();
    }
}

@ShellComponent
record LoginCommands(LoginService service) {

    @ShellMethod("login")
    public void login(String username, String password) {
        this.service.login(username);
    }

    @ShellMethod("logout")
    @ShellMethodAvailability("logoutAvailability")
    public void logout() {
        this.service.logout();
    }

    public Availability logoutAvailability() {
        return this.service.isLoggedIn() ?
                Availability.available() :
                Availability.unavailable("you must be logged in");
    }
}

@ShellComponent
record LoginPromptProvider(LoginService loginService) implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        var logged = new AttributedString(this.loginService.loggedInUser() + " >", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
        var notLoggedIn = new AttributedString( "unknown >", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));

        return this.loginService.isLoggedIn() ? logged : notLoggedIn;
    }
}
