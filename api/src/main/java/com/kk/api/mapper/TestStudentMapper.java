package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
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

}