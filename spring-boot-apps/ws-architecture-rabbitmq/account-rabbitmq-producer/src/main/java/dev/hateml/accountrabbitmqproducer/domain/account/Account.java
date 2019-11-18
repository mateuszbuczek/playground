package dev.hateml.accountrabbitmqproducer.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.hateml.accountrabbitmqproducer.domain.Address;
import dev.hateml.accountrabbitmqproducer.domain.User;
import dev.hateml.accountrabbitmqproducer.interfaces.AccountDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SqlResultSetMapping(name = "accountPartialMapping", classes = {
        @ConstructorResult(targetClass = AccountDto.class, columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "account_number"),
                @ColumnResult(name = "account_name"),
                @ColumnResult(name = "user_name"),
                @ColumnResult(name = "first_name"),
                @ColumnResult(name = "last_name"),
                @ColumnResult(name = "sex"),
                @ColumnResult(name = "member_type"),
                @ColumnResult(name = "registration_date"),
        })
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "AccountRepository.getAccountById",
            query = "select acc.id, acc.account_name, acc.account_number, u.user_name, u.first_name, u.last_name, u.sex, u.member_type, u.registration_date" +
                    " from ACCOUNTS acc, USERS u WHERE acc.user_profile_id = u.id and acc.id = :id")
})
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_NAME", nullable = false)
    private String accountName;

    @OneToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    private Set<Address> addresses = new HashSet<>();

    public Account() {
    }

    public Account(String accountNumber, String accountName, User user, Set<Address> addresses) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.user = user;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(accountName, account.accountName) &&
                Objects.equals(user, account.user) &&
                Objects.equals(addresses, account.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountName, user, addresses);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}
