package com.jiahua.filter;

import com.alibaba.fastjson.JSON;
import com.jiahua.pojo.TUser;
import com.jiahua.util.Constants;
import com.jiahua.util.Dto;
import com.jiahua.util.DtoUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        System.out.println("-------------------------------初始化登录拦截过滤器");
    }

    public void destroy() {
        System.out.println("-------------------------------销毁登录拦截过滤器");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        String type=request.getParameter("type");
        if (type!=null&&type.equals("login")){
            //要登录的用户直接登录，不用进行拦截
            chain.doFilter(request, response);
        }else{
            //做后端登录校验的
            System.out.println("----------------------------进入登录拦截过滤器");
            HttpServletRequest httpServletRequest= (HttpServletRequest) request;
            HttpSession session=httpServletRequest.getSession();
            TUser tUser = (TUser) session.getAttribute(Constants.loginStatus);
            if (tUser==null){
                //此时说明没有登录,则需要跳转到前端登录页面
                HttpServletResponse response1=(HttpServletResponse)response;
                response1.setContentType("application/json;Charset=UTF-8");
                PrintWriter printWriter=response1.getWriter();
                Dto dto = DtoUtils.fail("403","用户没有访问权限");
                printWriter.write(JSON.toJSONString(dto));
                printWriter.close();
                return;
            }


            chain.doFilter(request, response);
            System.out.println("----------------------------退出登录拦截过滤器");
        }

    }
}
