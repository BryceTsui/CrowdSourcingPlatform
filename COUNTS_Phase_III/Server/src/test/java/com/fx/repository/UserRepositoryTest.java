package com.fx.repository;

import com.fx.model.User;
import com.fx.repository.impl.UserRepositoryImpl;
import com.fx.util.ResultMessage;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Description:
 * Created by Hanxinhu at 9:41 2018/3/20/020
 */
public class UserRepositoryTest {
    UserRepository userRepository;
    public UserRepositoryTest(){
        userRepository = new UserRepositoryImpl();
    }
    @Test
    public void addUser() {
        String username = new String();
        for (int i = 0; i < 9; i++) {
            username += (int) (Math.random() * 10);
        }
        User user = new User(username, "hwl123456", "123456@email.com",
                "18812345678");
        ResultMessage message = userRepository.addUser(user);
        assertEquals(ResultMessage.SUCCESS,message);

        userRepository.deleteUser(user);
    }
    @Test
    public void findNextId() {
        int id = userRepository.findNextId();
        assertNotEquals(0, id);
    }

    @Test
    public void findUserById() {
        String username = new String();
        for (int i = 0; i < 9; i++) {
            username += (int) (Math.random() * 10);
        }
        User user = new User(username, "hwl123456", "123456@email.com",
                "18812345678");
        userRepository.addUser(user);
        int id = user.getId();
        user = userRepository.findUserById(id);
        assertNotNull(user);
        userRepository.deleteUser(user);
    }

    @Test
    public void findUserByUsername() {
        String username = new String();
        for (int i = 0; i < 9; i++) {
            username += (int) (Math.random() * 10);
        }
        User user = new User(username, "hwl123456", "123456@email.com",
                "18812345678");
        userRepository.addUser(user);
        User user1 = userRepository.findUserByUsername(username);
        assertNotNull(user1);
        userRepository.deleteUser(user);

    }

    @Ignore
    @Test
    public void findPasswordByUsername() {
        String username = new String();
        for (int i = 0; i < 9; i++) {
            username += (int) (Math.random() * 10);
        }
        User user = new User(username, "hwl123456", "123456@email.com",
                "18812345678");
        userRepository.addUser(user);
        String password = userRepository.findPasswordByUsername("hxh");
        assertEquals("hwl123456",password);
        userRepository.deleteUser(user);
    }



    @Test
    public void updateUser() {
        User user = new User(99999,"hxh","hwl123456","123456@email.com",
                "18812345678");
        assertEquals(ResultMessage.SUCCESS,ResultMessage.SUCCESS);

    }

    @Test
    public void deleteUser() {
        String username = new String();
        for (int i = 0; i < 9; i++) {
            username += (int) (Math.random() * 10);
        }
        User user = new User(username, "hwl1234567", "123456@email.com", "18812345678");
        userRepository.addUser(user);
      ResultMessage message =  userRepository.deleteUser(user);
      assertEquals(ResultMessage.SUCCESS,message);
    }
}