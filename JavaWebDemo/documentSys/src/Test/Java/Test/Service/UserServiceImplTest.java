package Test.Service;

import com.jiahua.exception.UserServiceException;
import com.jiahua.pojo.TUser;
import com.jiahua.service.UserService;
import com.jiahua.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserService userService=new UserServiceImpl();
    @Test
    void login() {
        try {
            TUser user = userService.login("李四", "123456");
            if (user!=null){
                System.out.println("登录成功");
                System.out.println(user.toString());
            }
        } catch (UserServiceException throwables) {
            System.out.println("登录失败");
            System.out.println(throwables.getMessage());
        } catch (SQLException throwables) {
            System.out.println("登录失败");
            System.out.println("系统异常");
        }
    }

    @Test
    void selectUserByName() {

        try {
            TUser tUser = userService.selectUserByName("李四");
            System.out.println(tUser.toString());
        } catch (UserServiceException throwables) {
            System.out.println("查找失败");
            System.out.println(throwables.getMessage());
        } catch (SQLException throwables) {
            System.out.println("系统异常");
        }
    }
}