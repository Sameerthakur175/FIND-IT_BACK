package com.example.LostAndFound2.Repository;

import com.example.LostAndFound2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

     User findByEmailId(String emailID);
}
