package dev.hateml.accountrabbitmqproducer.interfaces;

import java.util.Date;
import java.util.Objects;

public class AccountDto {

    private Long id;
    private String accountNumber;
    private String accountName;
    private String userName;
    private String firstName;
    private String lastName;
    private String sex;
    private String memberType;
    private Date registrationDate;

    public AccountDto() {
    }

    public AccountDto(Long id, String accountNumber, String accountName, String userName, String firstName, String lastName, String sex, String memberType, Date registrationDate) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.memberType = memberType;
        this.registrationDate = registrationDate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        AccountDto that = (AccountDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(memberType, that.memberType) &&
                Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountName, userName, firstName, lastName, sex, memberType, registrationDate);
    }
}
