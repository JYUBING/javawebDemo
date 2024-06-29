package com.jiahua.util;

/**
 * 这个实体类专门封装返回的格式的
 */
public class Dto<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public Dto() {
    }

    public Dto(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Dto(boolean success, String code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

}
