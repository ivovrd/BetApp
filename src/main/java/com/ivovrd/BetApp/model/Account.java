package com.ivovrd.BetApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private Double balance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Double getBalance() {
        return balance;
    }

    public void increaseBalance(Double amount) {
        this.balance += amount;
    }

    public boolean decreaseBalance(Double amount){
        if (this.balance >= amount){
            this.balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
