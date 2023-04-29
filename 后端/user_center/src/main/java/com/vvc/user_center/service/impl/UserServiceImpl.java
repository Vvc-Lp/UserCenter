package com.vvc.user_center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vvc.user_center.common.ErrorCode;
import com.vvc.user_center.exception.BusinessException;
import com.vvc.user_center.mapper.UserMapper;
import com.vvc.user_center.model.User;
import com.vvc.user_center.service.UserService;
import com.vvc.user_center.utils.PasswordEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.vvc.user_center.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author Vvc
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-04-24 16:09:13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 注册功能
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 确认密码
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            //todo 修改自定义异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码太短");
        }

        //校验账户包含特殊字符
        String validPattern = "^[a-zA-Z0-9_]+$";
        boolean matches = userAccount.matches(validPattern);
        if (!matches) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"包含特殊字符");
        }

        //密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不一致");
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
//        int count = this.count(queryWrapper);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号重复");
        }
        //给密码进行MD5加密
        String encryptPassword = PasswordEncryptor.encrypt(userPassword);
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
//        boolean saveResult = this.save(user);

        if (!this.save(user)) {
            log.info("出错");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"保存数据失败");
        }
        return user.getId();
    }

    /**
     * 登录功能
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 脱敏信息
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验账户包含特殊字符
        String validPattern = "^[a-zA-Z0-9_]+$";
        boolean matches = userAccount.matches(validPattern);
        if (!matches) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //给密码进行MD5加密
        String encryptPassword = PasswordEncryptor.encrypt(userPassword);

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User selectOne = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (selectOne == null) {
            log.info("user login failed");
           throw new RuntimeException(String.valueOf(ErrorCode.PARAMS_ERROR));
        }

        //用户脱敏
          User newUser = getSafetyUser(selectOne);

        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, newUser);

        return newUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User newUser = new User();
        newUser.setId(originUser.getId());
        newUser.setUsername(originUser.getUsername());
        newUser.setUserAccount(originUser.getUserAccount());
        newUser.setAvatarUrl(originUser.getAvatarUrl());
        newUser.setGender(originUser.getGender());
        newUser.setPhone(originUser.getPhone());
        newUser.setEmail(originUser.getEmail());
        newUser.setUserRole(originUser.getUserRole());
        newUser.setCreateTime(originUser.getCreateTime());
        newUser.setUpdateTime(originUser.getUpdateTime());
        return newUser;
    }


    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


}




