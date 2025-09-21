package com.example.LostAndFound2.Controller;

import com.example.LostAndFound2.DTO.UserDto;
import com.example.LostAndFound2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

 @Autowired
    UserService userService;

   @PostMapping("/add")
    public String addUser(@RequestBody() UserDto userDto){
       String ans = userService.addUser(userDto);
       return ans;
   }
}
