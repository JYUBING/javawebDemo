package com.jiahua.pojo;

public class TDocType {
    private int id;
    private String name;

    public TDocType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TDocType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
