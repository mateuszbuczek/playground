package dev.hateml.accountrabbitmqproducer.domain.account;

import dev.hateml.accountrabbitmqproducer.domain.NotFoundException;
import dev.hateml.accountrabbitmqproducer.domain.account.Account;
import dev.hateml.accountrabbitmqproducer.interfaces.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class Accounts {

    private AccountRepository accountRepository;

    public Accounts(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccount(Long id) {
        AccountDto accountById = accountRepository.getAccountById(id);
        if (accountById == null) {
            throw new NotFoundException();
        }

        return accountById;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
