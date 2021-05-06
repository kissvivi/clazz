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
     * 题目id
     */
    @Column(name = "tb_id")
    private Long tbId;

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
     * 获取题目id
     *
     * @return tb_id - 题目id
     */
    public Long getTbId() {
        return tbId;
    }

    /**
     * 设置题目id
     *
     * @param tbId 题目id
     */
    public void setTbId(Long tbId) {
        this.tbId = tbId;
    }
}