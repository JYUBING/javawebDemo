package com.jiahua.dao;

import com.jiahua.pojo.TUser;

import java.sql.Connection;
import java.sql.SQLException;

public interface TUserDao {

    //根据姓名查找
    public TUser selectUserByName(Connection connection,String name) throws SQLException;
    //添加用户数据
    public int insertUser(Connection connection,TUser user) throws SQLException;
    //更新用户数据
    public int UpdateUser(Connection connection,TUser user) throws SQLException;
    //删除用户数据
    public int deleteUserByName(Connection connection,String name) throws SQLException;

}
