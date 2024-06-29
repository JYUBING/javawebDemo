package com.jiahua.util;

import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    //获取连接对象
    public static Connection getConnection(){
        //1.通过反射加载类对象，加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接对象
            Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/doc_sys?useUnicode=true&characterEncoding=utf8","root","123456789");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }


    //关闭资源方法
    public  static void closeAllResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
