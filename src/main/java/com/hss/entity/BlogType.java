package com.hss.entity;

public class BlogType {

    private Integer id;
    private String typeName;//博客类别名称
    private int orderNo;//博客类别排序

    private int blogCount;//根据博客类别进行统计

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }
}
