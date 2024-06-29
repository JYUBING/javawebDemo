package com.jiahua.daoimpl;

import com.jiahua.dao.TDocDao;
import com.jiahua.pojo.TDoc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TDocDaoImpl extends BaseDao implements TDocDao {
    @Override
    public TDoc selectDocById(Connection connection, int id) throws SQLException {
        String sql="select typeid,d.`name`,`describe`,auth,pushDate,t.name,picPath,picPaths from t_doc d inner join t_type t on t.id=d.typeId where d.id=?";
        ResultSet resultSet = executeQuery(connection, sql, id);
        while (resultSet.next()) {
            int docTypeId=resultSet.getInt(1);
            String docName=resultSet.getString(2);
            String docDescribe=resultSet.getString(3);
            String auth=resultSet.getString(4);
            Date  date=resultSet.getDate(5);
            String tName = resultSet.getString(6);
            String picPath = resultSet.getString(7);
            String picPaths = resultSet.getString(8);
            TDoc tDoc=new TDoc(id,docTypeId,docName,docDescribe,auth,date,tName,picPath,picPaths);
            return tDoc;
        }
        return null;
    }

    @Override
    public int insertDoc(Connection connection, TDoc doc) throws SQLException {
        String sql="insert into t_doc(id,typeId,name,`describe`,auth,pushDate,picPath,picPaths,createDate ) values(?,?,?,?,?,?,?,?,now())";
        int i=executeUpdate(connection,sql,doc.getId(),doc.getTypeId(),doc.getName(),doc.getDescribe(),doc.getAuth(),doc.getPushDate(),doc.getPicPath(),doc.getPicPaths());
        return i;
    }

    @Override
    public int UpdateDoc(Connection connection, TDoc doc) throws SQLException {
        String sql="update t_doc set typeId=?,name=?,`describe`=?,auth=?, pushDate=? ,picPath=?, picPaths=? where id=?";
        int i=executeUpdate(connection,sql,doc.getTypeId(),doc.getName(),doc.getDescribe(),doc.getAuth(),doc.getPushDate(),doc.getPicPath(),doc.getPicPaths(),doc.getId());
        return i;
    }

    @Override
    public int deleteDocById(Connection connection, int id) throws SQLException {
        String sql="delete from t_doc where id=?";
        int i=executeUpdate(connection,sql,id);
        return i;
    }

    @Override
    public List<TDoc> selectDocByTypeId(Connection connection, Integer typeId,String docName, int pageNo, int pageSize) throws SQLException {
        StringBuffer sql= new StringBuffer("select doc.id,typeid,doc.name,`describe`,auth,pushdate,t.name  from t_doc doc inner join t_type t on doc.typeid=t.id where 1=1");
        List<Object> list=new ArrayList<>();
        if (typeId!=null){
            sql.append(" and typeid=?");
            list.add(typeId);
        }
        //字符串类型进行数据拼接，需要非空判断以及空字符串判断
        if (docName!=null&&!docName.equals("")){
            sql.append(" and doc.name like concat('%',?,'%')");
            list.add(docName);
        }
        sql.append(" order by createDate DESC  limit ?,?;");
        list.add((pageNo-1)*pageSize);
        list.add(pageSize);


        ResultSet resultSet = executeQuery(connection, sql.toString(), list.toArray());
        List<TDoc> result=new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int typeID=resultSet.getInt(2);
            String name = resultSet.getString(3);
            String describle = resultSet.getString(4);
            String auth = resultSet.getString(5);
            Date pushDate = resultSet.getDate(6);
            String t_name = resultSet.getString(7);
            TDoc tDoc= new TDoc(id,typeID,name,describle,auth,pushDate,t_name);
            result.add(tDoc);
        }
            return result;

    }

    @Override
    public int selectDocCount(Connection connection, Integer typeId, String docName) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(id) from t_doc where 1=1");
        List params = new ArrayList();
        if (typeId != null) {
            sql.append(" and typeid=?");
            params.add(typeId);
        }
        if (docName != null&&!docName.equals("")) {
            sql.append(" and name like concat('%',?,'%')");
            params.add(docName);
        }
        ResultSet resultSet = executeQuery(connection, sql.toString(), params.toArray());
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count;
        }
        return 0;
    }
}
