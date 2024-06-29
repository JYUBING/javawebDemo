package Test.Service;

import com.jiahua.pojo.TDoc;
import com.jiahua.service.DocService;
import com.jiahua.serviceimpl.DocServiceImpl;
import com.jiahua.util.PageSuppot;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;


class DocServiceImplTest {

    DocService docService=new DocServiceImpl();

    @Test
    void insertDoc() {
        TDoc tDoc = new TDoc(5,3,"荀文自传","牛马的一生","荀文",null);

        try {
            boolean result = docService.insertDoc(tDoc);
            if (result){
                System.out.println("数据插入成功");
            }else{
                System.out.println("数据插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteDocById() {
        try {
            boolean result = docService.deleteDocById(5);
            if (result){
                System.out.println("数据删除成功");
            }else{
                System.out.println("数据删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateDocById() {
        TDoc tDoc = new TDoc(5,3,"荀文自传","牛马的一生","阿文",null);

        try {
            boolean result = docService.updateDocById(tDoc);
            if (result){
                System.out.println("数据更新成功");
            }else{
                System.out.println("数据更新失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void selectDocById() {
        try {
            TDoc tDoc = docService.selectDocById(3);
            System.out.println(tDoc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void selectDocListByPage() {
        try {
           PageSuppot<List<TDoc>> pageSuppot = docService.selectDocListByPage(null, null, 1, 3);
            for (TDoc tDoc:pageSuppot.getData()) {
                System.out.println(tDoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}