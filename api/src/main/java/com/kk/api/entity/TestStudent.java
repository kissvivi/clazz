package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "test_student")
public class TestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学生学号
     */
    @Column(name = "stu_code")
    private Long stuCode;

    /**
     * 考试编号
     */
    @Column(name = "tests_code")
    private Long testsCode;

    /**
     * 分数
     */
    private Integer score;

    private Date time;

    /**
     * 自己的选择集合
     */
    @Column(name = "self_checks")
    private String selfChecks;

    /**
     * 正确的选择集合
     */
    @Column(name = "ok_checks")
    private String okChecks;

    /**
     * 正确题目个数
     */
    @Column(name = "ok_num")
    private Integer okNum;

    /**
     * 班级id
     */
    @Column(name = "clazz_id")
    private Long clazzId;

    /**
     * 题目集合
     */
    @Column(name = "tb_ids")
    private String tbIds;

    /**
     * 分类
     */
    private Integer type;

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
     * 获取学生学号
     *
     * @return stu_code - 学生学号
     */
    public Long getStuCode() {
        return stuCode;
    }

    /**
     * 设置学生学号
     *
     * @param stuCode 学生学号
     */
    public void setStuCode(Long stuCode) {
        this.stuCode = stuCode;
    }

    /**
     * 获取考试编号
     *
     * @return tests_code - 考试编号
     */
    public Long getTestsCode() {
        return testsCode;
    }

    /**
     * 设置考试编号
     *
     * @param testsCode 考试编号
     */
    public void setTestsCode(Long testsCode) {
        this.testsCode = testsCode;
    }

    /**
     * 获取分数
     *
     * @return score - 分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Integer score) {
        this.score = score;
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
     * 获取自己的选择集合
     *
     * @return self_checks - 自己的选择集合
     */
    public String getSelfChecks() {
        return selfChecks;
    }

    /**
     * 设置自己的选择集合
     *
     * @param selfChecks 自己的选择集合
     */
    public void setSelfChecks(String selfChecks) {
        this.selfChecks = selfChecks;
    }

    /**
     * 获取正确的选择集合
     *
     * @return ok_checks - 正确的选择集合
     */
    public String getOkChecks() {
        return okChecks;
    }

    /**
     * 设置正确的选择集合
     *
     * @param okChecks 正确的选择集合
     */
    public void setOkChecks(String okChecks) {
        this.okChecks = okChecks;
    }

    /**
     * 获取正确题目个数
     *
     * @return ok_num - 正确题目个数
     */
    public Integer getOkNum() {
        return okNum;
    }

    /**
     * 设置正确题目个数
     *
     * @param okNum 正确题目个数
     */
    public void setOkNum(Integer okNum) {
        this.okNum = okNum;
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
     * 获取题目集合
     *
     * @return tb_ids - 题目集合
     */
    public String getTbIds() {
        return tbIds;
    }

    /**
     * 设置题目集合
     *
     * @param tbIds 题目集合
     */
    public void setTbIds(String tbIds) {
        this.tbIds = tbIds;
    }

    /**
     * 获取分类
     *
     * @return type - 分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置分类
     *
     * @param type 分类
     */
    public void setType(Integer type) {
        this.type = type;
    }
}