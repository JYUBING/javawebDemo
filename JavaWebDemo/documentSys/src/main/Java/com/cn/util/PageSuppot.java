package com.jiahua.util;

public class PageSuppot<T> {
    //1.页码
    private  Integer pageNo;
    //2.页大小
    private Integer pageSize;
    //3.数据总数
    private Integer totalCount;
    //4.总页数
    private Integer totalPage;
    //5.分页数据
    private T data;

    public PageSuppot() {
    }

    public PageSuppot(Integer pageNo, Integer pageSize, Integer totalCount, Integer totalPage, T data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.data = data;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
