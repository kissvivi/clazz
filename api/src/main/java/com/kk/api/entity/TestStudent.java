package com.kk.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "test_student")
public class TestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学生id
     */
    @Column(name = "stu_id")
    private Long stuId;

    /**
     * 考试id
     */
    @Column(name = "t_code")
    private Long tCode;

    /**
     * 分数
     */
    private Integer score;

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
     * 获取学生id
     *
     * @return stu_id - 学生id
     */
    public Long getStuId() {
        return stuId;
    }

    /**
     * 设置学生id
     *
     * @param stuId 学生id
     */
    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    /**
     * 获取考试id
     *
     * @return t_code - 考试id
     */
    public Long gettCode() {
        return tCode;
    }

    /**
     * 设置考试id
     *
     * @param tCode 考试id
     */
    public void settCode(Long tCode) {
        this.tCode = tCode;
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
}