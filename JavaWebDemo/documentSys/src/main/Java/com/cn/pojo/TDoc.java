package com.jiahua.pojo;

import java.util.Date;

public class TDoc {

    private Integer id;
    private Integer typeId;
    private String name;
    private String describe;
    private String auth;
    private Date pushDate;
    private  String typeName;
    private String picPath;
    private String picPaths;
    private Date createDate;
    private Date updateDate;

    public TDoc(Integer id, Integer typeId, String name, String describe, String auth, Date pushDate, String typeName, String picPath, String picPaths, Date createDate, Date updateDate) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
        this.pushDate = pushDate;
        this.typeName = typeName;
        this.picPath = picPath;
        this.picPaths = picPaths;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public TDoc(Integer id, Integer typeId, String name, String describe, String auth, Date pushDate) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
        this.pushDate = pushDate;
    }



    public TDoc(Integer id, Integer typeId, String name, String describe, String auth) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
    }

    public TDoc() {
    }

    public TDoc(Integer id, Integer typeId, String name, String describe, String auth, Date pushDate, String typeName) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
        this.pushDate = pushDate;
        this.typeName = typeName;
    }

    public TDoc(Integer id, Integer typeId, String name, String describe, String auth, Date pushDate, String typeName, String picPath) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
        this.pushDate = pushDate;
        this.typeName = typeName;
        this.picPath = picPath;
    }

    public TDoc(Integer id, Integer typeId, String name, String describe, String auth, Date pushDate, String typeName, String picPath, String picPaths) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.describe = describe;
        this.auth = auth;
        this.pushDate = pushDate;
        this.typeName = typeName;
        this.picPath = picPath;
        this.picPaths = picPaths;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPicPaths() {
        return picPaths;
    }

    public void setPicPaths(String picPaths) {
        this.picPaths = picPaths;
    }


    @Override
    public String toString() {
        return "TDoc{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", auth='" + auth + '\'' +
                ", pushDate=" + pushDate +
                ", typeName='" + typeName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", picPaths='" + picPaths + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
