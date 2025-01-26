package com.expense.tracker.auth.model;

import com.expense.tracker.auth.entities.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) //snake case is USER_INFO like that
public class UserInfoDto extends UserInfo {   //Dto are there for API data transfer not fro DB call modelling

    @JsonProperty("first_name")
    private String firstName; //json will make first_name

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private Long phoneNumber;

    @JsonProperty("email")
    private String email;

}
