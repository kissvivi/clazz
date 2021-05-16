package com.kk.api.dto;

import com.kk.api.entity.Tests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author kiss_vivi
 * @date 2021/5/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestStudentDto extends Tests {

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

}
