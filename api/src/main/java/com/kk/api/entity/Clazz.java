package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班级二维码
     */
    private String url;

    /**
     * 课程名
     */
    private String course;

    /**
     * 创建时间
     */
    private Date time;

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
     * 获取班级名
     *
     * @return name - 班级名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置班级名
     *
     * @param name 班级名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取班级二维码
     *
     * @return url - 班级二维码
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置班级二维码
     *
     * @param url 班级二维码
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取课程名
     *
     * @return course - 课程名
     */
    public String getCourse() {
        return course;
    }

    /**
     * 设置课程名
     *
     * @param course 课程名
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * 获取创建时间
     *
     * @return time - 创建时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置创建时间
     *
     * @param time 创建时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", course='" + course + '\'' +
                ", time=" + time +
                '}';
    }
}