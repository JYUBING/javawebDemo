package com.jiahua.service;

import com.jiahua.pojo.TDoc;
import com.jiahua.util.PageSuppot;

import java.sql.SQLException;

public interface DocService {

    //1.增加
    public boolean insertDoc(TDoc doc) throws SQLException;
    //2.删除
    public boolean deleteDocById(int id) throws SQLException;
    //3.修改
    public boolean updateDocById(TDoc doc) throws SQLException;
    //4.查询
    public TDoc selectDocById(int id) throws SQLException;
    //5.分页查询
    public PageSuppot selectDocListByPage(Integer typeId, String docName, Integer pageNo, Integer pageSize) throws SQLException;
}
