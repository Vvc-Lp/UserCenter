package com.vvc.user_center.utils;

import com.vvc.user_center.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PasswordEncryptorTest {

    @Test
    public void passwordTest(){
        //假设是从数据库中取出的密码
        String pw = "123456";
        String encrypt = PasswordEncryptor.encrypt(pw);


        //登录是输入的密码
        String npw="1564632162";
        String encrypt1 = PasswordEncryptor.encrypt(npw);
        if (encrypt1.equals(encrypt)){
            System.out.println("密码相同");
        }else {
            System.out.println("密码不同");
        }
    }


}