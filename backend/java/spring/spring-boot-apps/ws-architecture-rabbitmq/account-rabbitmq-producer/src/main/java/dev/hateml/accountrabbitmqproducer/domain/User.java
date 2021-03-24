package dev.hateml.accountrabbitmqproducer.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    private String sex;

    @Column(name = "MEMBER_TYPE", nullable = false)
    private String memberType;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "REGISTRATION_DATE", nullable = false, unique = false, length = 10)
    private Date registrationDate;

    public User() {
    }

    public User(String firstName, String lastName, String sex, String memberType, String accountNumber, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.memberType = memberType;
        this.accountNumber = accountNumber;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(memberType, user.memberType) &&
                Objects.equals(accountNumber, user.accountNumber) &&
                Objects.equals(registrationDate, user.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, sex, memberType, accountNumber, registrationDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", memberType='" + memberType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
