package com.jiahua.servlect;

import com.alibaba.fastjson.JSON;
import com.jiahua.pojo.TDoc;
import com.jiahua.pojo.TDocType;
import com.jiahua.service.DocService;
import com.jiahua.service.TDocTypeService;
import com.jiahua.serviceimpl.DocServiceImpl;
import com.jiahua.serviceimpl.TDocTypeServiceImpl;
import com.jiahua.util.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DocServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DocService docService=new DocServiceImpl();
        TDocTypeService tDocTypeService=new TDocTypeServiceImpl();
        System.out.println("接受前端发送过来的请求~！~~~~~~~~~~~~~~~~~~~ /doc");

        response.setContentType("application/json;Charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        Dto dto=null;
        String type=request.getParameter("type");

        if (type!=null&&type.equals("listpage")){
            System.out.println("------------分页功能-------------");
            //1.获取文档分类ID
            String _typeId=request.getParameter("typeId");
            //2.获取文档名
            String _docName=request.getParameter("docname");
            //3.获取页码
            String _pageNo=request.getParameter("pageNo");
            //4.类型转换
            Integer pageNo=1;
            Integer typeId=null;
            if (_pageNo!=null&&!_pageNo.equals("")) {
                pageNo=Integer.parseInt(_pageNo);
            }
            if (_typeId!=null&&!_typeId.equals("")) {
                typeId=Integer.parseInt(_typeId);
            }

            Map<String,Object> map=new HashMap();
            //5.执行业务查询
            try {
                PageSuppot pageSuppot = docService.selectDocListByPage(typeId, _docName, pageNo, Constants.pageSize);

                List<TDocType> allTypeList = tDocTypeService.getAllTypeList();

                //将后端返回来的数据用map封装起来
                map.put("typeid",typeId);
                map.put("docName",_docName);
                map.put("page",pageSuppot);
                map.put("typelist",allTypeList);

                dto= DtoUtils.success(map);
            } catch (SQLException e) {
                e.printStackTrace();
                dto= DtoUtils.fail();
            }

        }else if (type!=null&&type.equals("add")){
            System.out.println("------------增加功能-------------");
            //1.获取前端请求参数

            String _docName = request.getParameter("docName");
            String _typeId = request.getParameter("typeId");
            String _describe = request.getParameter("describe");
            String _auth = request.getParameter("auth");
            String _pushDate = request.getParameter("pushDate");
            TDoc tDoc = new TDoc(null, null, _docName, _describe, _auth, null);


            if(_typeId!=null&&Integer.parseInt(_typeId)!=0){
                tDoc.setTypeId(Integer.parseInt(_typeId));
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(_pushDate!=null){
                try {
                    Date date = simpleDateFormat.parse(_pushDate);
                    tDoc.setPushDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //2.调用Service层执行业务
            try {
                boolean result = docService.insertDoc(tDoc);
                if(result){
                    dto= DtoUtils.success(result);
                }else{
                    dto=DtoUtils.fail();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                dto=DtoUtils.fail();
            }




        }else if (type!=null&&type.equals("del")){
            System.out.println("------------删除功能-------------");
            //1.获取参数
            String id =request.getParameter("id");
            //2.调用service执行业务
            try {
                TDoc docById = docService.selectDocById(Integer.parseInt(id));
                boolean result = docService.deleteDocById(Integer.parseInt(id));
                if (result){
                    //删除磁盘文件
                    String realPath = this.getServletContext().getRealPath("/upload");
                    String separator=System.getProperty("file.separator");
                    String path1=realPath+separator+docById.getPicPath();
                    String path2=realPath+separator+docById.getPicPaths();
                    del(path1,realPath);
                    del(path2,realPath);
                    dto=DtoUtils.success(result);
                }else{
                    dto=DtoUtils.fail();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                dto=DtoUtils.fail();
            }
        }else if (type!=null&&type.equals("update")) {
            System.out.println("------------修改功能-------------");
            //1.获取前端请求参数
            String id = request.getParameter("id");
            //Tdoc获取这个对应的文档数据（文本数据，两个文件的路径）
            TDoc tdoc = null;
            try {
                tdoc = docService.selectDocById(Integer.parseInt(id));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            //获取前端的文本数据，直接更新Tdoc文本数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                dto = DtoUtils.fail("400", "请求参数错误");
            }
            //3. 创建磁盘文件库
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //4. 获取servlet文件上传对象
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);


            //4.2 处理乱码问题
            fileUpload.setHeaderEncoding("UTF-8");
            //4.3 设置单个文件大小的最大值
            fileUpload.setFileSizeMax(1024 * 1024 * 10);

            try {
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                for (FileItem fileItem : fileItems) {
                    //普通标签，除了type=file的input标签外，其他都是普通的标签
                    //判断是否是文本域
                    if (fileItem.isFormField()) {//当前控件为普通表单
                        //拿到表单标签name属性
                        String fieldName = fileItem.getFieldName();
                        //拿到表单标签value属性
                        String value = fileItem.getString("UTF-8");
                        System.out.println(fieldName + ":" + value);
                        if (fieldName != null && fieldName.equals("docName")) {
                            tdoc.setName(fileItem.getString("UTF-8"));
                        } else if (fieldName != null && fieldName.equals("auth")) {
                            tdoc.setAuth(fileItem.getString("UTF-8"));

                        } else if (fieldName != null && fieldName.equals("describe")) {
                            tdoc.setDescribe(fileItem.getString("UTF-8"));
                        } else if (fieldName != null && fieldName.equals("pushDate")) {
                            tdoc.setPushDate(DateUtils.paseDateString(fileItem.getString("UTF-8")));
                        } else if (fieldName != null && fieldName.equals("typeId")) {
                            tdoc.setTypeId(Integer.parseInt(fileItem.getString("UTF-8")));
                        }

                    } else {
                        //文件域
                        //当前控件为带文件的表单 type=file的input标签
                        //这个字段需要文件上传
                        //2.获取文件上传的存储路径
                        //this.getServletContext() 获取项目的上下文路径，localhost:8080/docsys/
                        String uploadPath = this.getServletContext().getRealPath("/upload");
                        System.out.println("上传文件根目录：" + uploadPath);
                        File uploadFile = new File(uploadPath);
                        if (!uploadFile.exists()) {
                            //localhost:8080/docsys/upload  这是tomcat项目根路径下面创建upload文件夹
                            uploadFile.mkdir();
                        }

                        //获取一个路径的分隔符   获取linux系统的路径分隔符  /
                        String separator = System.getProperty("file.separator");

                        //name属性： idPicPath /WorkPicPath
                        String filefieldName = fileItem.getFieldName();


                        //头像的文件上传
                        String uploadFileName = fileItem.getName();
                        //5.3 对获取的文件字符串路径进行处理
                        if (uploadFileName.trim().equals("") || uploadFileName == null)
                            continue;
                        //5.3.1  获取文件名 fileName -->tou
                        String fileName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."));
                        System.out.println("文件信息：" + "[文件名：" + fileName + "]");
                        //5.4 生成唯一的字符串，标识文件名，保证文件不会因为重名和覆盖
                        UUID uuidName = UUID.randomUUID();

                        String realUploadPath = null;
                        String delPath=null;
                        String delDestinationPath = null;
                        if (filefieldName != null && filefieldName.equals("picPath")) {
                            realUploadPath = uploadPath  + separator + uuidName;
                            delPath=tdoc.getPicPath();
                            delDestinationPath=this.getServletContext().getRealPath("/upload");

                        }else {
                            //工作正路径
                            realUploadPath = uploadPath + separator  + uuidName;
                            delPath=tdoc.getPicPaths();
                            delDestinationPath=this.getServletContext().getRealPath("/upload");
                        }
                        File realUploadFile = new File(realUploadPath);
                        if (!realUploadFile.exists())
                            realUploadFile.mkdir();
                        //5.6 将上传的文件保存到上面存储的唯一文件夹中
                        //5.6.1  获取上传文件的流
                        InputStream inputStream = fileItem.getInputStream();
                        //5.6.2 将文件流写出到指定服务器文件 localhost:8080/docsys/upload/6282720f-694f-45d1-84ca-dc0c6a30ca5c/tou.jpg
                        FileOutputStream fos = new FileOutputStream(realUploadPath + separator + uploadFileName);
                        byte[] buffer = new byte[1024 * 1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        inputStream.close();
                        fileItem.delete();//上传成功，清除临时文件
                        //刪除原來的圖片
                        String realPath = this.getServletContext().getRealPath("/upload");
                        String path=realPath+separator+delPath;
                        del(path,delDestinationPath);
                        //将新图片插入进去
                        if (filefieldName != null && filefieldName.equals("picPath")) {
                            tdoc.setPicPath( uuidName + separator + uploadFileName);
                        } else if (filefieldName != null && filefieldName.equals("picPaths")) {
                            //工作证的文件上传
                            tdoc.setPicPaths(uuidName + separator + uploadFileName);
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            //更新数据库
            try {
                boolean b = docService.updateDocById(tdoc);
                dto=DtoUtils.success(b);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }




        }else if (type!=null&&type.equals("view")){
            System.out.println("------------查看功能-------------");
            //1.获取参数
            String id =request.getParameter("id");
            //2.调用service执行业务
            try {
                TDoc docById = docService.selectDocById(Integer.parseInt(id));
                dto=DtoUtils.success(docById);
            } catch (SQLException e) {
                e.printStackTrace();
                dto=DtoUtils.fail();
            }

        }else if (type!=null&&type.equals("viewUpdate")){
            System.out.println("------------查看文档类型分类功能-------------");
            //1.获取参数
            String id =request.getParameter("id");

            //2.调用service执行业务
            try {
                HashMap<Object, Object> map = new HashMap<>();
                TDoc docById = docService.selectDocById(Integer.parseInt(id));
                List<TDocType> allTypeList = tDocTypeService.getAllTypeList();
                map.put("doc",docById);
                map.put("typeList",allTypeList);
                dto=DtoUtils.success(map);
            } catch (SQLException e) {
                e.printStackTrace();
                dto=DtoUtils.fail();
            }

        }else if (type!=null&&type.equals("upload")) {
            if (!ServletFileUpload.isMultipartContent(request)) {
                dto = DtoUtils.fail("400", "请求参数错误");
            }

            //3. 创建磁盘文件库
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //4. 获取servlet文件上传对象
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);

            //4.2 处理乱码问题
            fileUpload.setHeaderEncoding("UTF-8");
            //4.3 设置单个文件大小的最大值
            fileUpload.setFileSizeMax(1024 * 1024 * 10);

            //5. 处理上传的文件
            try {
                //5.1 获取表单中的每一个控件  FileItem 就是可能是普通的文本域 后或者文件域
                //6个FileItem 其中 1个是文件域  5个是文本域
                //获取5个文本域数据 ，封装到Tdoc中
                //获取1个文件域数据，字节流方式写到tomcat的指定磁盘中，且获取路径，存到Tdoc
                //Tdoc（这6个数据）插入数据库中
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                TDoc tdoc = new TDoc();
                for (FileItem fileItem : fileItems) {
                    //普通标签，除了type=file的input标签外，其他都是普通的标签
                    //判断是否是文本域
                    if (fileItem.isFormField()) {//当前控件为普通表单
                        //拿到表单标签name属性
                        String fieldName = fileItem.getFieldName();
                        //拿到表单标签value属性
                        String value = fileItem.getString("UTF-8");
                        System.out.println(fieldName + ":" + value);
                        if (fieldName != null && fieldName.equals("docName")) {
                            tdoc.setName(fileItem.getString("UTF-8"));
                        } else if (fieldName != null && fieldName.equals("auth")) {
                            tdoc.setAuth(fileItem.getString("UTF-8"));

                        } else if (fieldName != null && fieldName.equals("describe")) {
                            tdoc.setDescribe(fileItem.getString("UTF-8"));
                        } else if (fieldName != null && fieldName.equals("pushDate")) {
                            tdoc.setPushDate(DateUtils.paseDateString(fileItem.getString("UTF-8")));
                        } else if (fieldName != null && fieldName.equals("typeId")) {
                            tdoc.setTypeId(Integer.parseInt(fileItem.getString("UTF-8")));
                        }
                    }
                    if (!fileItem.isFormField() && fileItem.getFieldName().equals("picPath")) {
                        //文件域
                        //当前控件为带文件的表单 type=file的input标签
                        //这个字段需要文件上传
                        //2.获取文件上传的存储路径
                        //this.getServletContext() 获取项目的上下文路径，localhost:8080/docsys/
                        String uploadPath = this.getServletContext().getRealPath("/upload");
                        System.out.println("上传文件根目录：" + uploadPath);
                        File uploadFile = new File(uploadPath);
                        if (!uploadFile.exists()) {
                            //localhost:8080/docsys/upload  这是tomcat项目根路径下面创建upload文件夹
                            uploadFile.mkdir();
                        }


                        //获取一个路径的分隔符   获取linux系统的路径分隔符  /
                        String separator = System.getProperty("file.separator");

                        //5.2 获取文件路径  上传上来文件名，  tou.jpg
                        String uploadFileName = fileItem.getName();
                        //5.3 对获取的文件字符串路径进行处理
                        if (uploadFileName.trim().equals("") || uploadFileName == null)
                            continue;
                        //5.3.1  获取文件名 fileName -->tou
                        String fileName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."));
                        System.out.println("文件信息：" + "[文件名：" + fileName + "]");
                        //5.4 生成唯一的字符串，标识文件名，保证文件不会因为重名和覆盖
                        UUID uuidName = UUID.randomUUID();

                        //5.5 为上传的文件创建一个唯一命名的文件夹 localhost:8080/docsys/upload/6282720f-694f-45d1-84ca-dc0c6a30ca5c
                        String realUploadPath = uploadPath + separator + uuidName;
                        File realUploadFile = new File(realUploadPath);
                        if (!realUploadFile.exists())
                            realUploadFile.mkdir();
                        //5.6 将上传的文件保存到上面存储的唯一文件夹中
                        //5.6.1  获取上传文件的流
                        InputStream inputStream = fileItem.getInputStream();
                        //5.6.2 将文件流写出到指定服务器文件 localhost:8080/docsys/upload/6282720f-694f-45d1-84ca-dc0c6a30ca5c/tou.jpg
                        FileOutputStream fos = new FileOutputStream(realUploadPath + separator + uploadFileName);
                        byte[] buffer = new byte[1024 * 1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        inputStream.close();
                        fileItem.delete();//上传成功，清除临时文件

                        tdoc.setPicPath(uuidName + separator + uploadFileName);
                    }
                    if (!fileItem.isFormField()&&fileItem.getFieldName().equals("picPaths")) {
                        //文件域
                        //当前控件为带文件的表单 type=file的input标签
                        //这个字段需要文件上传
                        //2.获取文件上传的存储路径
                        //this.getServletContext() 获取项目的上下文路径，localhost:8080/docsys/
                        String uploadPath = this.getServletContext().getRealPath("/upload");
                        System.out.println("上传文件根目录：" + uploadPath);
                        File uploadFile = new File(uploadPath);
                        if (!uploadFile.exists()) {
                            //localhost:8080/docsys/uploads  这是tomcat项目根路径下面创建upload文件夹
                            uploadFile.mkdir();
                        }

                        //获取一个路径的分隔符   获取linux系统的路径分隔符  /
                        String separator = System.getProperty("file.separator");

                        //5.2 获取文件路径  上传上来文件名，  tou.jpg
                        String uploadFileName = fileItem.getName();
                        //5.3 对获取的文件字符串路径进行处理
                        if (uploadFileName.trim().equals("") || uploadFileName == null)
                            continue;
                        //5.3.1  获取文件名 fileName -->tou
                        String fileName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."));
                        System.out.println("文件信息：" + "[文件名：" + fileName + "]");
                        //5.4 生成唯一的字符串，标识文件名，保证文件不会因为重名和覆盖
                        UUID uuidName = UUID.randomUUID();

                        //5.5 为上传的文件创建一个唯一命名的文件夹 localhost:8080/docsys/upload/6282720f-694f-45d1-84ca-dc0c6a30ca5c
                        String realUploadPath = uploadPath + separator + uuidName;
                        File realUploadFile = new File(realUploadPath);
                        if (!realUploadFile.exists())
                            realUploadFile.mkdir();
                        //5.6 将上传的文件保存到上面存储的唯一文件夹中
                        //5.6.1  获取上传文件的流
                        InputStream inputStream = fileItem.getInputStream();
                        //5.6.2 将文件流写出到指定服务器文件 localhost:8080/docsys/upload/6282720f-694f-45d1-84ca-dc0c6a30ca5c/tou.jpg
                        FileOutputStream fos = new FileOutputStream(realUploadPath + separator + uploadFileName);
                        byte[] buffer = new byte[1024 * 1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        inputStream.close();
                        fileItem.delete();//上传成功，清除临时文件
                        tdoc.setPicPaths(uuidName + separator + uploadFileName);
                    }
                }

                try {
                    boolean b = docService.insertDoc(tdoc);
                    if(b){
                        //添加成功
                        //跳转回到分页页面展示
                        dto = DtoUtils.success();
                    }else{
                        //插入数据库失败
                        dto = DtoUtils.fail("500", "数据插入数据失败，请重新输入");

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                    dto = DtoUtils.fail("500", "系統异常");
                }



            } catch (FileUploadException e) {
                e.printStackTrace();
                dto = DtoUtils.fail("500", "系統异常");
            }
            }

        //3.数据封装返回前端
        String result = JSON.toJSONString(dto);
        printWriter.write(result);
        printWriter.close();

    }
    public static void del(String path,String rootPath){
        File file=new File(path);
        file.delete();
        String subPath = path.substring(0,path.lastIndexOf("\\"));
        if (subPath.equals(rootPath)){
            return;
        }
        del(subPath,rootPath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
