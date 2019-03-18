package com.hss.entity;

public class Blogger {

    private Integer id;
    private String userName;//用户名  用户名不能重复
    private String  password;//密码
    private String profile;//个人说明
    private String nickName;//昵称
    private String sign;//个性签名
    private String imageName;//图片路径
    private int isExit;//是否注销
    private Power power;//用户的权限

    public Power getPower() {
        return power;
    }
    public void setPower(Power power) {
        this.power = power;
    }

    public int getIsExit() {
        return isExit;
    }

    public void setIsExit(int isExit) {
        this.isExit = isExit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
