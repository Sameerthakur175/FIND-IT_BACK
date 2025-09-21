package com.example.LostAndFound2.Convertor;


import com.example.LostAndFound2.DTO.UserDto;
import com.example.LostAndFound2.Model.User;

public class UserConvertor {
    public static User userConvertor(UserDto userDto){
      User user = User.builder().emailId(userDto.getEmail()).name(userDto.getName()).mobileNo(userDto.getMobileNo()).build();
      return user;

    }
}
