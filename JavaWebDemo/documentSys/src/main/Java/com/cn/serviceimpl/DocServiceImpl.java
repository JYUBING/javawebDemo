package com.jiahua.serviceimpl;

import com.jiahua.dao.TDocDao;
import com.jiahua.daoimpl.TDocDaoImpl;
import com.jiahua.pojo.TDoc;
import com.jiahua.service.DocService;
import com.jiahua.util.DatabaseUtils;
import com.jiahua.util.PageSuppot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DocServiceImpl implements DocService {

    TDocDao tDocDao=new TDocDaoImpl();
    @Override
    public boolean insertDoc(TDoc doc) throws SQLException {
       //建立连接
        Connection connection= DatabaseUtils.getConnection();
        try {
            //关闭自动提交，开启事务
            connection.setAutoCommit(false);
            int i=tDocDao.insertDoc(connection,doc);
            connection.commit();
            if (i>0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
           connection.rollback();
           throw e;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Override
    public boolean deleteDocById(int id) throws SQLException {
        //建立连接
        Connection connection= DatabaseUtils.getConnection();
        try {
            //关闭自动提交，开启事务
            connection.setAutoCommit(false);
            int i=tDocDao.deleteDocById(connection,id);
            connection.commit();
            if (i>0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Override
    public boolean updateDocById(TDoc doc) throws SQLException {
        //建立连接
        Connection connection= DatabaseUtils.getConnection();
        try {
            //关闭自动提交，开启事务
            connection.setAutoCommit(false);
            int i=tDocDao.UpdateDoc(connection,doc);
            connection.commit();
            if (i>0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Override
    public TDoc selectDocById(int id) throws SQLException {
        //建立连接
        Connection connection= DatabaseUtils.getConnection();
        TDoc tDoc =null;
        try {
             tDoc = tDocDao.selectDocById(connection, id);
        } catch (SQLException e) {
            throw e;
        }
         return tDoc;
    }

    @Override
    public PageSuppot selectDocListByPage(Integer typeId, String docName, Integer pageNo, Integer pageSize) throws SQLException {
        //创建一个实体类，用来封装数据
       PageSuppot<List<TDoc>> pageSuppot=new PageSuppot(pageNo,pageSize,null,null,null);

        //建立连接
        Connection connection= DatabaseUtils.getConnection();

        List<TDoc> tDocs =null;
        try {
             tDocs = tDocDao.selectDocByTypeId(connection, typeId, docName, pageNo, pageSize);
             pageSuppot.setData(tDocs);
             int count=tDocDao.selectDocCount(connection,typeId,docName);
             pageSuppot.setTotalCount(count);
        } catch (SQLException e) {
            throw e;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
        return pageSuppot;
    }
}
