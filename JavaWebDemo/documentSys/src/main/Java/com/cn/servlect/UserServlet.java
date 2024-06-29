package com.jiahua.servlect;

import com.alibaba.fastjson.JSON;
import com.jiahua.exception.UserServiceException;
import com.jiahua.pojo.TUser;
import com.jiahua.service.DocService;
import com.jiahua.service.UserService;
import com.jiahua.serviceimpl.DocServiceImpl;
import com.jiahua.serviceimpl.UserServiceImpl;
import com.jiahua.util.Constants;
import com.jiahua.util.Dto;
import com.jiahua.util.DtoUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //业务层
        UserService userService=new UserServiceImpl();
        DocService docService=new DocServiceImpl();
        Dto dto=null;

        response.setContentType("application/json;Charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        //request 封装前端过来的数据
        //response 封装处理后的数据及实现页面跳转
        System.out.println("接受前端发送过来的请求~！~~~~~~~~~~~~~~~~~~~ /user");

        //请求响应的类型
        String type = request.getParameter("type");
        //登录功能
        if(type!=null&&type.equals("login")){
            System.out.println("登录功能");
            String username = request.getParameter("username");
            String password = request.getParameter("password");



            try {
                TUser user = userService.login(username, password);
                /*记录登录状态
                方法一：redis 后端的一个缓存服务器的一个存储数据的对象，通过 key value的方式存储数据
                生成一个全局唯一的key  值里面放的是对象
                方法二：通过session来记录登录状态
                 */

                //通过session来记录登录状态
                HttpSession session=request.getSession();
                session.setAttribute(Constants.loginStatus,user);
                dto   = DtoUtils.success(user);
            } catch (UserServiceException e) {
                dto=DtoUtils.fail("10001",e.getMessage());
            } catch (SQLException e) {
                dto=DtoUtils.fail("10002",e.getMessage());
            }

        }else if(type!=null&&type.equals("reg")){
            //注册功能
            System.out.println("注册功能");
        }else if(type!=null&&type.equals("userinfo")){
            //查看个人信息功能
            System.out.println("查看个人信息功能");
        }else if(type!=null&&type.equals("updateinfo")){
            //修改个人信息功能
            System.out.println("修改个人信息功能");

        }else if(type!=null&&type.equals("modifypass")){
            //修改密码功能
            System.out.println("修改密码功能");
        }else if(type!=null&&type.equals("layout")) {
            //退出
            System.out.println("登出功能");
            HttpSession session=request.getSession();
            //清空session所有数据
            session.invalidate();
            dto = DtoUtils.success();
        }
        String result = JSON.toJSONString(dto);
        printWriter.write(result);
        printWriter.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
