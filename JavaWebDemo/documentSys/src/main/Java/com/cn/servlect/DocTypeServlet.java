package com.jiahua.servlect;

import com.alibaba.fastjson.JSON;
import com.jiahua.pojo.TDocType;
import com.jiahua.service.TDocTypeService;
import com.jiahua.serviceimpl.TDocTypeServiceImpl;
import com.jiahua.util.Dto;
import com.jiahua.util.DtoUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class DocTypeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接受前端发送过来的请求~！~~~~~~~~~~~~~~~~~~~ /type");
        //设置编码格式
        response.setContentType("application/json;Charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        TDocTypeService service=new TDocTypeServiceImpl();

        PrintWriter printWriter = response.getWriter();
        Dto dto=null;

        try {
            List<TDocType> allTypeList = service.getAllTypeList();
           dto = DtoUtils.success(allTypeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printWriter.write(JSON.toJSONString(dto));
        printWriter.close();
    }

}
