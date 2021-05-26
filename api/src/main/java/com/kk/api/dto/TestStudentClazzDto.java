package com.kk.api.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.kk.api.entity.Clazz;
import com.kk.api.entity.Student;
import com.kk.api.entity.TestStudent;
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
 * @date 2021/5/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestStudentClazzDto {

    /**
     * 学生学号
     */
    @Excel(name = "学号", orderNum = "1")
    private Long stuCode;

    @Excel(name = "姓名", orderNum = "2")
    private String stuName;

    /**
     * 考试编号
     */
    @Excel(name = "考试编号", orderNum = "0")
    private Long testsCode;

    /**
     * 分数
     */
    @Excel(name = "分数", orderNum = "3")
    private Integer score;

    /**
     * 正确题目个数
     */
    @Excel(name = "正确题数", orderNum = "4")
    private Integer okNum;

    /**
     * 班级name
     */
    @Excel(name = "所在班级", orderNum = "4")
    private String clazzName;

    /**
     * 课程
     */
    @Excel(name = "课程", orderNum = "4")
    private String courseName;


}
