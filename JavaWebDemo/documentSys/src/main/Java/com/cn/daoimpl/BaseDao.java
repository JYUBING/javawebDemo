package com.jiahua.daoimpl;



import com.jiahua.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BaseDao {

    //增删改的封装方法
    public int executeUpdate(Connection connection,String sql,Object... params) throws SQLException {
        //1.连接数据库
        System.out.println("=============执行的sql語句=========="+sql.toString());
        System.out.println("=============执行的params語句=======" + Arrays.toString(params));
        PreparedStatement preparedStatement=null;
        //2.预编译sql
         preparedStatement = connection.prepareStatement(sql);
        //3.注入参数  有几个参数就循环几遍，然后将参数的值赋入
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject((i+1),params[i]);
        }
        //4.执行操作
        int i = 0;
        try {
            i = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(i>0){
            System.out.println("数据库操作成功");
        }
        //5.关闭流
        DatabaseUtils.closeAllResources(null,  preparedStatement,null);
        return i;
       }

       //查询的方法封装
    public ResultSet executeQuery(Connection connection,String sql,Object... params) {
        System.out.println("=============执行的sql語句=========="+sql.toString());
        System.out.println("=============执行的params語句=======" + Arrays.toString(params));
        //1.连接数据库
        PreparedStatement preparedStatement = null;

        try {
            //2.预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //3.注入参数  有几个参数就循环几遍，然后将参数的值赋入
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject((i + 1), params[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //4.ResultSet封装返回数据的结果
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
                return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
