package Test.Dao;

import com.jiahua.util.DatabaseUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDatabase {

    //查询操作
    @Test
    public void testJdbcQuery() {
        //1.连接数据库
        Connection connection = DatabaseUtils.getConnection();
        //2.执行sql语句，通过PrepareStatement 操作数据库
        String sql = "select username `password` from t_user where username=?";
        PreparedStatement preparedStatement = null;

        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //注入参数
            preparedStatement.setObject(1, "张三");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //3.ResultSet封装返回的数据(查询语法独有)
        ResultSet resultSet = null;

        try {
            resultSet = preparedStatement.executeQuery();

            //4.遍历ResultSet获取数据
            while (resultSet.next()) {
                String username = (String) resultSet.getObject("username");
                String password = (String) resultSet.getObject("password");
                System.out.println(username + "\t" + password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //5.关闭流
        try {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加操作
    @Test
    public void testJdbcInsert() {
        //1.连接数据库
        Connection connection = DatabaseUtils.getConnection();
        //2.执行sql语句，通过PrepareStatement 操作数据库
        String sql = "INSERT INTO t_doc(id,typeid,`name`,`describe`,auth,pushdate)VALUES(?,?,?,?,?,NOW())";
        PreparedStatement preparedStatement = null;

        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //注入参数
            preparedStatement.setObject(1, 3);
            preparedStatement.setObject(2, 2);
            preparedStatement.setObject(3, "霸道总裁录用我");
            preparedStatement.setObject(4, "高薪入职java开发");
            preparedStatement.setObject(5, "张三");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //3.执行插入操作
        int i = 0;
        try {
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(i>0){
            System.out.println("插入成功");
        }


        //4.关闭流
        try {
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除操作
    @Test
    public void testJdbcDelete() {
        //1.连接数据库
        Connection connection = DatabaseUtils.getConnection();
        //2.执行sql语句，通过PrepareStatement 操作数据库
        String sql = "DELETE FROM t_doc WHERE id=?";
        PreparedStatement preparedStatement = null;

        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //注入参数
            preparedStatement.setObject(1, 3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //3.执行删除操作
        int i = 0;
        try {
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }if(i>0){
            System.out.println("删除成功");
        }

        //4.关闭流
        try {
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新操作
    @Test
    public void testJdbcUpdate() {
        //1.连接数据库
        Connection connection = DatabaseUtils.getConnection();
        //2.执行sql语句，通过PrepareStatement 操作数据库
        String sql = "UPDATE t_doc SET typeid=?,`name`=?,`describe`=?,auth=?,pushdate=now() WHERE id=?";
        PreparedStatement preparedStatement = null;

        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //注入参数
            preparedStatement.setObject(1, 3);
            preparedStatement.setObject(2, "霸道总裁录用我");
            preparedStatement.setObject(3, "高薪入职java开发");
            preparedStatement.setObject(4, "张三");
            preparedStatement.setObject(5, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //3.执行更新操作
        int i = 0;
        try {
            i= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(i>0){
            System.out.println("更新成功");
        }

        //4.关闭流
        try {
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }





