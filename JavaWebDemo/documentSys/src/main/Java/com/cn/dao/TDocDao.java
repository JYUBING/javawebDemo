package com.jiahua.dao;


import com.jiahua.pojo.TDoc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TDocDao {

    //根据id查找
    public TDoc selectDocById(Connection connection, int id) throws SQLException;
    //添加文档数据
    public int insertDoc(Connection connection,TDoc doc) throws SQLException;
    //更新文档数据
    public int UpdateDoc(Connection connection,TDoc doc) throws SQLException;
    //删除文档数据
    public int deleteDocById(Connection connection,int id) throws SQLException;
    //分页查询
    public List<TDoc> selectDocByTypeId(Connection connection,Integer typeId,String docName,int pageNo,int pageSize) throws SQLException;
    //分页查询，获取数据的总数
    public int selectDocCount(Connection connection,Integer typeId,String docName) throws SQLException;
}
