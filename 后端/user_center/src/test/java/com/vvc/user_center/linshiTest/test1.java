package com.vvc.user_center.linshiTest;

public class test1 {

    //正则表达式测试

    public static void main(String[] args) {
        String userName = "afASDAScas231_";

        String validPattern = "^[a-zA-Z0-9_]+$";
//        boolean matches = userName.matches(validPattern);
        if (!userName.matches(validPattern)){
            System.out.println("有效");
        }else {
            System.out.println("无效");
        }
    }
}
