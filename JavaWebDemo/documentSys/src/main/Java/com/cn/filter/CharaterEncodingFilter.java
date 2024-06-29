package com.jiahua.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharaterEncodingFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        System.out.println("--------------------初始化字符编码过滤器------------");
    }

    public void destroy() {
        System.out.println("--------------------销毁字符编码过滤器------------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("--------------------字符编码过滤器------------接受请求");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;Charset=UTF-8");
        chain.doFilter(request, response);
        System.out.println("--------------------字符编码过滤器------------处理完请求，返回前端");
    }
}
