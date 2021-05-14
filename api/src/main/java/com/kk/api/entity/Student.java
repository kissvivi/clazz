package com.kk.api.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @Excel(name = "学号", orderNum = "0")
    private String code;

    /**
     * 密码
     */
    @Excel(name = "密码", orderNum = "1")
    private String password;

    /**
     * weixin
     */
    @Column(name = "open_id")
    private String openId;

    private Date time;

    /**
     * 姓名
     */
    @Excel(name = "姓名", orderNum = "2")
    private String name;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取学号
     *
     * @return code - 学号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置学号
     *
     * @param code 学号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取weixin
     *
     * @return open_id - weixin
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置weixin
     *
     * @param openId weixin
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }
}