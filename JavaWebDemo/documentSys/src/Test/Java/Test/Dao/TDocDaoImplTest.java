package Test.Dao;

import com.jiahua.dao.TDocDao;
import com.jiahua.daoimpl.TDocDaoImpl;
import com.jiahua.pojo.TDoc;
import com.jiahua.util.DatabaseUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


class TDocDaoImplTest {

    @Test
    void selectDocById() {
        Connection connection = DatabaseUtils.getConnection();
        TDocDao tDocDao=new TDocDaoImpl();

        try {
            TDoc doc=tDocDao.selectDocById(connection,3);
            System.out.println(doc);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    void insertDoc() {
        Connection connection = DatabaseUtils.getConnection();
        TDocDao tDocDao=new TDocDaoImpl();
        try {
            int i=tDocDao.insertDoc(connection,new TDoc(3,2,"活着","余华","牛马的一生",new Timestamp(System.currentTimeMillis())));
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
    void updateDoc() {
        Connection connection = DatabaseUtils.getConnection();
        TDocDao tDocDao=new TDocDaoImpl();
        try {
            int i=tDocDao.UpdateDoc(connection,new TDoc(3,2,"活着","牛马的一生","张三"));
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
    void deleteDocById() {
        Connection connection = DatabaseUtils.getConnection();
        TDocDao tDocDao=new TDocDaoImpl();
        try {
            int i=tDocDao.deleteDocById(connection,3);
            if (i>0){
                System.out.println("删除成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtils.closeAllResources(connection,null,null);
        }
    }

    @Test
    void selectDocByTypeId(){
        //1.数据库的连接
        Connection connection = DatabaseUtils.getConnection();
        TDocDao tDocDao=new TDocDaoImpl();
        List<TDoc> tDocs = null;
        try {
            tDocs = tDocDao.selectDocByTypeId(connection, null, "唱",1, 5);
                System.out.println(tDocs.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}