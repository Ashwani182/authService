package com.expense.tracker.auth.repository;

import com.expense.tracker.auth.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserInfo,Long> {

     UserInfo findByUsername(String username);

   // UserInfo findbyUsernameAndPassword(String username,String password);
    // it will find in usernfo table with  user id and password


}
