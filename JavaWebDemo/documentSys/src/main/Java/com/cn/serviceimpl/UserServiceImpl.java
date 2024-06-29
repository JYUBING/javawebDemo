package com.jiahua.serviceimpl;

import com.jiahua.dao.TUserDao;
import com.jiahua.daoimpl.TUserDaoImpl;
import com.jiahua.exception.UserServiceException;
import com.jiahua.pojo.TUser;
import com.jiahua.service.UserService;
import com.jiahua.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    TUserDao userDao =new TUserDaoImpl();

    @Override
    public TUser login(String userName, String password) throws UserServiceException, SQLException {
        //创建连接
        Connection connection = DatabaseUtils.getConnection();

        try {
            TUser user=userDao.selectUserByName(connection,userName);
            if (user==null){
                throw new UserServiceException("不存在此用户，用户名不存在");
            }
            if (!user.getPassword().equals(password)){
                throw new UserServiceException("存在此用户，但是密码错误");
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw  throwables;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Override
    public TUser selectUserByName(String  name) throws UserServiceException, SQLException {

        Connection connection = DatabaseUtils.getConnection();
        try {
            TUser user=userDao.selectUserByName(connection,name);
            if (user==null){
                throw new UserServiceException("不存在用户");
            }
            return  user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }

    }
}
