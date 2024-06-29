package Test.Dao;

import com.jiahua.dao.TUserDao;
import com.jiahua.daoimpl.TUserDaoImpl;
import com.jiahua.pojo.TUser;
import com.jiahua.util.DatabaseUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


class TUserDaoImplTest {


    @Test
   public void selectUserByName() {
        //1.数据库的连接
        Connection connection = DatabaseUtils.getConnection();
        TUserDao tUserDao=new TUserDaoImpl();

        try {
            TUser user = tUserDao.selectUserByName(connection, "张三");
            System.out.println(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }

    }

    @Test
    public void insertUser() {
        //1.数据库的连接
        Connection connection = DatabaseUtils.getConnection();
        TUserDao tUserDao=new TUserDaoImpl();

        try {
            int i= tUserDao.insertUser(connection,new TUser("jack","123123","jj"));
            if(i>0){
                System.out.println("插入数据成功");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
}

    @Test
   public  void updateUser() {
        //1.数据库的连接
        Connection connection = DatabaseUtils.getConnection();
        TUserDao tUserDao=new TUserDaoImpl();

        try {
            int i=tUserDao.UpdateUser(connection,new TUser("张三","111111","jj"));
            if (i>0){
                System.out.println("更新数据成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    public void deleteUserByName() {
        //1.数据库的连接
        Connection connection = DatabaseUtils.getConnection();
        TUserDao tUserDao=new TUserDaoImpl();

        try {
            int i=tUserDao.deleteUserByName(connection,"jack");
            if (i>0){
                System.out.println("删除数据成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }
}