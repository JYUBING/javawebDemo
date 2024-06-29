package com.jiahua.service;

import com.jiahua.exception.UserServiceException;
import com.jiahua.pojo.TUser;

import java.sql.SQLException;

public interface UserService {

    //1.登录功能
    public TUser login(String userName,String password) throws UserServiceException, SQLException;

    //2.查看当前用户信息
    public TUser selectUserByName(String  name) throws UserServiceException, SQLException;




}
