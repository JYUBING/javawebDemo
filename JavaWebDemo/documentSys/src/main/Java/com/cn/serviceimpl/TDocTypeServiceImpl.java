package com.jiahua.serviceimpl;

import com.jiahua.dao.TDocTypeDao;
import com.jiahua.daoimpl.TDocTypeDaoImpl;
import com.jiahua.pojo.TDoc;
import com.jiahua.pojo.TDocType;
import com.jiahua.service.TDocTypeService;
import com.jiahua.util.DatabaseUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TDocTypeServiceImpl implements TDocTypeService {
    TDocTypeDao tDocTypeDao=new TDocTypeDaoImpl();
    @Override
    public List<TDocType> getAllTypeList() throws SQLException {
        //建立连接
        Connection connection= DatabaseUtils.getConnection();
        try {
            return tDocTypeDao.selectTypeAll(connection,null,null);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }
    }
