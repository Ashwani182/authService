package com.expense.tracker.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class UserInfo {

    @Id
    @Column(name="user_id")//here no need of generated value because it will be enter by programmning
    private String userId;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable//here we are joining tables to make table
            (
                    name="users_roles",
                    joinColumns = @JoinColumn(name="user_id"),//which column from this
                    inverseJoinColumns = @JoinColumn(name="role") //and from the other table given reference below
            )
    private Set<UserRole> userRoleSet=new HashSet<>();

    //many to many mapping need to provide table name, column name from this table and column name from userRole table
    //in Mapping always we need to provide  with which object it is linked and jpa will gives that from the DB
    //like here we will get set of roles from that table and while saving also it will have set
    //in many to many always ave one table and this

}
