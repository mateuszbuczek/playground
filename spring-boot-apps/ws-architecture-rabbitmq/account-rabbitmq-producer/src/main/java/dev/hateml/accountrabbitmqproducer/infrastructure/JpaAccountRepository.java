package dev.hateml.accountrabbitmqproducer.infrastructure;

import dev.hateml.accountrabbitmqproducer.domain.account.Account;
import dev.hateml.accountrabbitmqproducer.interfaces.AccountDto;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface JpaAccountRepository extends PagingAndSortingRepository<Account, Long> {

    @Query(name = "accountPartialMapping", nativeQuery = true)
    AccountDto getAccountById(@Param("id") Long id);
}
