package com.vvc.user_center.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author Vvc
 *
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -3253181927173066345L;

    private String userAccount;

    private String userPassword;

}
