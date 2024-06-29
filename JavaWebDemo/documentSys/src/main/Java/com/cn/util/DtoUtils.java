package com.jiahua.util;
/**
 * 这个实体类专门封装错误信息
 */
public class DtoUtils {

    public static Dto success(){
        Dto dto=new Dto(true,"200",null,null);
        return dto;
    }

    public static Dto success(String mess){
        Dto dto=new Dto(true,"200",mess,null);
        return dto;
    }

    public static Dto success(Object data){
        Dto dto=new Dto(true,"200",null,data);
        return dto;
    }
    public static Dto success(String mess,Object data){
        Dto dto=new Dto(true,"200",mess,data);
        return dto;
    }
    public static Dto fail(){
        Dto dto=new Dto(false,null,null,null);
        return dto;
    }
    public static Dto fail(String code,String mess){
        Dto dto=new Dto(false,code,mess,null);
        return dto;
    }
    public static Dto fail(String code,String mess,Object data){
        Dto dto=new Dto(false,code,mess,data);
        return dto;
    }
}
