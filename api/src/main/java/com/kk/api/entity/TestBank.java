package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "test_bank")
public class TestBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 题目类型
     */
    private Integer type;

    private String title;

    @Column(name = "select_a")
    private String selectA;

    @Column(name = "select_b")
    private String selectB;

    @Column(name = "select_c")
    private String selectC;

    @Column(name = "select_d")
    private String selectD;

    /**
     * 正确答案
     */
    @Column(name = "select_ok")
    private String selectOk;

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
     * 获取题目类型
     *
     * @return type - 题目类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置题目类型
     *
     * @param type 题目类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return select_a
     */
    public String getSelectA() {
        return selectA;
    }

    /**
     * @param selectA
     */
    public void setSelectA(String selectA) {
        this.selectA = selectA;
    }

    /**
     * @return select_b
     */
    public String getSelectB() {
        return selectB;
    }

    /**
     * @param selectB
     */
    public void setSelectB(String selectB) {
        this.selectB = selectB;
    }

    /**
     * @return select_c
     */
    public String getSelectC() {
        return selectC;
    }

    /**
     * @param selectC
     */
    public void setSelectC(String selectC) {
        this.selectC = selectC;
    }

    /**
     * @return select_d
     */
    public String getSelectD() {
        return selectD;
    }

    /**
     * @param selectD
     */
    public void setSelectD(String selectD) {
        this.selectD = selectD;
    }

    /**
     * 获取正确答案
     *
     * @return select_ok - 正确答案
     */
    public String getSelectOk() {
        return selectOk;
    }

    /**
     * 设置正确答案
     *
     * @param selectOk 正确答案
     */
    public void setSelectOk(String selectOk) {
        this.selectOk = selectOk;
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
}