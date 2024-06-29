package com.jiahua.daoimpl;

import com.jiahua.dao.TDocTypeDao;
import com.jiahua.pojo.TDocType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TDocTypeDaoImpl extends BaseDao  implements TDocTypeDao {
    @Override
    public TDocType selectDocByTypeId(Connection connection, int id) throws SQLException {
        String sql="select id,name from t_type where id =?";
        ResultSet resultSet = executeQuery(connection, sql, id);
        while (resultSet.next()){
            int typeId=resultSet.getInt("id");
            String  typeName=resultSet.getString("name");
            TDocType tDocType=new TDocType(typeId,typeName);
            return  tDocType;
        }
        return  null;
    }

    @Override
    public int insertType(Connection connection, TDocType type) throws SQLException {
        String sql="insert into t_type(id,name) values(?,?)";
        int i=executeUpdate(connection,sql,type.getId(),type.getName());
        return i;
    }

    @Override
    public int UpdateType(Connection connection, TDocType type) throws SQLException {
        String sql="update t_type set name=? where id = ?";
        int i=executeUpdate(connection,sql,type.getName(),type.getId());
        return i;
    }

    @Override
    public int deleteDocByTypeId(Connection connection, int id) throws SQLException {
        String sql="delete from t_type where id=?";
        int i=executeUpdate(connection,sql,id);
        return i;
    }

    @Override
    public List<TDocType> selectTypeAll(Connection connection, Integer pageNo, Integer pageSize) throws SQLException {
        StringBuffer sql=new StringBuffer("select id,name from t_type where 1=1 ");
        List<Object> params=new ArrayList<>();
        if (pageSize!=null && pageNo!=null ){
            sql.append(" limit ?,?");
            params.add((pageNo-1)*pageSize);
            params.add(pageSize);
        }
        ResultSet resultSet =executeQuery(connection, sql.toString(), params.toArray());
        List<TDocType> list=new ArrayList<>();
        while (resultSet.next()) {
            int id1 = resultSet.getInt("id");
            String name = resultSet.getString("name");
            TDocType type=new TDocType(id1,name);
            list.add(type);
        }
        return list;
    }

}
