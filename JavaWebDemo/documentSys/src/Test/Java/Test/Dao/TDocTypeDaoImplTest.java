package Test.Dao;

import com.jiahua.dao.TDocTypeDao;
import com.jiahua.daoimpl.TDocTypeDaoImpl;
import com.jiahua.pojo.TDocType;
import com.jiahua.util.DatabaseUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TDocTypeDaoImplTest {

    @Test
    void selectDocByTypeId() {
            Connection connection = DatabaseUtils.getConnection();
        TDocTypeDao tDocTypeDao=new TDocTypeDaoImpl();

        try {
            TDocType tDocType=tDocTypeDao.selectDocByTypeId(connection,3);
            System.out.println(tDocType);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    void insertType() {
        Connection connection = DatabaseUtils.getConnection();
        TDocTypeDao tDocTypeDao=new TDocTypeDaoImpl();
        try {
            int i=tDocTypeDao.insertType(connection,new TDocType(4,"玄幻"));
            if (i>0){
                System.out.println("插入成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    void updateType() {
        Connection connection = DatabaseUtils.getConnection();
        TDocTypeDao tDocTypeDao=new TDocTypeDaoImpl();
        try {
            int i=tDocTypeDao.UpdateType(connection,new TDocType(2,"仙侠"));
            if (i>0){
                System.out.println("更新成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    void deleteDocByTypeId() {
        Connection connection = DatabaseUtils.getConnection();
        TDocTypeDao tDocTypeDao=new TDocTypeDaoImpl();
        try {
            int i=tDocTypeDao.deleteDocByTypeId(connection,4);
            if (i>0){
                System.out.println("删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }
}