package com.vvc.user_center.service;
import java.util.Date;

import com.vvc.user_center.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 用户服务测试
 *
 * @author Vvc
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){

        User user = new User();

        user.setUsername("Vvc");
        user.setUserAccount("123456");
        user.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/sKmSRHYDVB6HJmpPIAgSt5f0DMU6clccuDhCl1oC98Lrp1qVxXwGfnrSsbiargXeagiavCAZ8EcjEZ6PweazZxFQ/132");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("1234564");
        user.setEmail("15464");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void userAdd(){
        long register = userService.userRegister("testVvc", "123456789", "123456789");
        System.out.println(register);
    }
    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount= "vc";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount ="yu pi";
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        checkPassword= "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount= "123456";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="wangcan";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);


    }


}