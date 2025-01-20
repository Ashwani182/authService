package com.expense.tracker.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

//All DB related things comes from jakarta persistence and all the boiler plate codes comes from lombok
@Entity
@Data //gives builder batter and getter setters
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")//table name
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity means it wil be identical
    private int id;

    private String token;

    private Instant expiryDate;

    //always remember while joining where exactly we need data of others like here we need user info object
    //for one to one mapping we don't have to make another table in same tables it can be done
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")   //here id of refreshment maps with user+id of UserInfo
    private UserInfo userInfo;       //that's why provided UserInfo from here it will take out user_id and Map with id

    //this mapping is done by hibernate JPA

}
