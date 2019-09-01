package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private  UserRepository userRepository;

    @Test
    public void create(){
        // String sql = insert into user (%s, %s) value (account, email);
        User user = new User();
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-3333-3333");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("TestUser3");

        User newUser = userRepository.save(user);
        System.out.println("newUser : "+ newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(5L);

        user.ifPresent(selectUser -> {

            selectUser.getOrderDetailList().stream().forEach(detail ->{

                Item item = detail.getItem();
                System.out.println(user);
                System.out.println("------------------");
                System.out.println(item);
                System.out.println("------------------");
                System.out.println(item.getName());

            });

        });
    }

    public void update(){
        Optional<User> user = userRepository.findById(2L);
        user.ifPresent(selectUser -> {
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");

            userRepository.save(selectUser);
        });
    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(3L);

        Assert.assertTrue(user.isPresent());
        // 무조건 true여야 한다.

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(2L);

        Assert.assertFalse(deleteUser.isPresent());
        // 무조건 false여야 한다.
    }


}
