package dev.hateml.accountrabbitmqproducer.domain.account;

import dev.hateml.accountrabbitmqproducer.domain.account.Account;
import dev.hateml.accountrabbitmqproducer.infrastructure.JpaAccountRepository;
import dev.hateml.accountrabbitmqproducer.interfaces.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaAccountRepository {

    Page<Account> findAll(Pageable pageable);

    AccountDto getAccountById(Long id);
}
