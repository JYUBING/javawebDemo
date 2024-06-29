package com.jiahua.daoimpl;

import com.jiahua.dao.TUserDao;
import com.jiahua.pojo.TUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TUserDaoImpl extends BaseDao implements TUserDao {
    @Override
    public TUser selectUserByName(Connection connection, String userName) throws SQLException {
        String sql="select username,`password`,nickname from t_user where username=? ";
        ResultSet resultSet=executeQuery(connection,sql,userName);
        while (resultSet.next()){
            String username =resultSet.getString("username");
            String password =resultSet.getString("password");
            String nickname=resultSet.getString("nickname");
            TUser tUser = new TUser(username, password,nickname);
            return tUser;
        }
        return null;
    }

    @Override
    public int insertUser(Connection connection, TUser user) throws SQLException {
        String sql="insert into t_user(username,password,nickname) values(?,?,?)";
        int i = executeUpdate(connection, sql, user.getUsername(),user.getPassword(),user.getNickname());
        return i;

    }

    @Override
    public int UpdateUser(Connection connection, TUser user) throws SQLException {
        String sql="update t_user set password=?,nickname=? where  username=?";
        int i = executeUpdate(connection, sql, user.getPassword(),user.getNickname(),user.getUsername());
        return i;
    }

    @Override
    public int deleteUserByName(Connection connection, String name) throws SQLException {
        String sql="delete from t_user where  username=?";
        int i = executeUpdate(connection, sql,name);
        return i;
    }
}
