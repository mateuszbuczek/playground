package dev.hateml.accountrabbitmqproducer.interfaces;

import dev.hateml.accountrabbitmqproducer.domain.account.Account;
import dev.hateml.accountrabbitmqproducer.domain.account.AccountEvent;
import dev.hateml.accountrabbitmqproducer.domain.account.Accounts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController extends AbstractController {

    private final Accounts accounts;

    public AccountController(Accounts accounts) {
        this.accounts = accounts;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account savedAccount = accounts.save(account);

        AccountDto accountDto = accounts.getAccount(savedAccount.getId());

        AccountEvent accountEvent = new AccountEvent(this, "AccountCreatedEvent", accountDto);
        applicationEventPublisher.publishEvent(accountEvent);

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@PathVariable("id") Long id) {
        AccountDto accountDto = accounts.getAccount(id);

        AccountEvent accountEvent = new AccountEvent(this, "AccountRetrievedEvent", accountDto);
        applicationEventPublisher.publishEvent(accountEvent);

        return accountDto;
    }
}
