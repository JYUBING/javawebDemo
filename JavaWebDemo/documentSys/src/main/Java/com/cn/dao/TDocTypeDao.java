package com.jiahua.dao;


import com.jiahua.pojo.TDocType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TDocTypeDao {

    //根据id查找
    public TDocType selectDocByTypeId(Connection connection, int id) throws SQLException;
    //添加文档数据
    public int insertType(Connection connection,TDocType type) throws SQLException;
    //更新文档数据
    public int UpdateType(Connection connection,TDocType type) throws SQLException;
    //删除文档数据
    public int deleteDocByTypeId(Connection connection,int id) throws SQLException;
    //分页查询
    List<TDocType> selectTypeAll(Connection connection, Integer pageNo, Integer pageSize) throws SQLException;

}
