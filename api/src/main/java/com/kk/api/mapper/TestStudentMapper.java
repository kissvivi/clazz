package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.dto.ScoreRank;
import com.kk.api.dto.TestStudentDto;
import com.kk.api.entity.TestStudent;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestStudentMapper extends MyMapper<TestStudent> {

    @Select("select * from test_student where stu_code =#{stuCode} and tests_code=#{testsCode}")
    TestStudent findTestStudentByStuCodeAndTestsCode(Long stuCode,Long testsCode);

    @Select("update test_student set score=#{score},ok_num=#{okNum},self_checks=#{selfChecks} where stu_code =#{stuCode} and tests_code=#{testsCode}")
    Integer updateTestStudentByStuCodeAndTestsCode(TestStudent testStudent);

    @Select("SELECT a.*,b.* FROM test_student a LEFT JOIN tests b ON a.tests_code = b.code  WHERE a.stu_code =#{stuCode} AND a.type = 101")
    List<TestStudentDto> findTestsByStuCode(Long stuCode);

    @Select("SELECT a.*,b.* FROM test_student a LEFT JOIN tests b ON a.tests_code = b.code  WHERE  a.type = 101")
    List<TestStudentDto> findTests();

    /**
     * 统计分数段
     * @param testsCode 试题编号
     * @return
     */
    @Select("SELECT " +
            "SUM(CASE WHEN clazz_id IS NOT NULL THEN 1 ELSE 0 END)   AS countStu, " +
            "SUM(CASE WHEN score>=90 AND  score<=100 THEN 1 ELSE 0 END)   AS s90100, " +
            "SUM(CASE WHEN  score>=80 AND  score<90 THEN 1 ELSE 0 END)  AS s8089, " +
            "SUM(CASE WHEN  score>=70 AND  score<80 THEN 1 ELSE 0 END)  AS s7079, " +
            "SUM(CASE WHEN  score>=60 AND  score<70 THEN 1 ELSE 0 END)  AS s6069, " +
            "SUM(CASE WHEN  score<60  THEN 1 ELSE 0 END)   AS s59 " +
            "FROM test_student WHERE tests_code =#{testsCode} and type =101")
    ScoreRank getScoreRankByTestsCode(Long testsCode);

}