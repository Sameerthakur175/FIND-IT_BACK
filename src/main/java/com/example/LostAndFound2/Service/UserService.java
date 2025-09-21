package com.example.LostAndFound2.Service;

import com.example.LostAndFound2.Convertor.UserConvertor;
import com.example.LostAndFound2.DTO.UserDto;
import com.example.LostAndFound2.Model.User;
import com.example.LostAndFound2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String addUser(UserDto userDto){
       User user = UserConvertor.userConvertor(userDto);
       userRepository.save(user);
       return "Student Added Successfully";
    }
}
