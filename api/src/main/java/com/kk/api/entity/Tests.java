package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Tests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 考试编号
     */
    private Long code;

    /**
     * 时间
     */
    private Date time;

    /**
     * 考试标题
     */
    private String title;

    /**
     * 题目id集
     */
    @Column(name = "tb_ids")
    private String tbIds;

    /**
     * 班级id
     */
    @Column(name = "clazz_id")
    private Long clazzId;

    /**
     * 状态（0：未发布，1：已发布）
     */
    private Integer status;

    /**
     * 考试类型
     */
    private Integer type;

    /**
     * 考试时间
     */
    private Integer timeOver;

    public Integer getTimeOver() {
        return timeOver;
    }

    public void setTimeOver(Integer timeOver) {
        this.timeOver = timeOver;
    }

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
     * 获取考试编号
     *
     * @return code - 考试编号
     */
    public Long getCode() {
        return code;
    }

    /**
     * 设置考试编号
     *
     * @param code 考试编号
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * 获取时间
     *
     * @return time - 时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置时间
     *
     * @param time 时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取考试标题
     *
     * @return title - 考试标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置考试标题
     *
     * @param title 考试标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取题目id集
     *
     * @return tb_ids - 题目id集
     */
    public String getTbIds() {
        return tbIds;
    }

    /**
     * 设置题目id集
     *
     * @param tbIds 题目id集
     */
    public void setTbIds(String tbIds) {
        this.tbIds = tbIds;
    }

    /**
     * 获取班级id
     *
     * @return clazz_id - 班级id
     */
    public Long getClazzId() {
        return clazzId;
    }

    /**
     * 设置班级id
     *
     * @param clazzId 班级id
     */
    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    /**
     * 获取状态（0：未发布，1：已发布）
     *
     * @return status - 状态（0：未发布，1：已发布）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0：未发布，1：已发布）
     *
     * @param status 状态（0：未发布，1：已发布）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取考试类型
     *
     * @return type - 考试类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置考试类型
     *
     * @param type 考试类型
     */
    public void setType(Integer type) {
        this.type = type;
    }
}