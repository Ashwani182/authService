package com.expense.tracker.auth.model;

import com.expense.tracker.auth.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) //snake case is USER_INFO like that
public class UserDto extends UserInfo {   //Dto are there for API data transfer not fro DB call modelling

    private String userName; //json will make user_name

    private String lastName;

    private Long phoneNumber;

    private String email;

}
