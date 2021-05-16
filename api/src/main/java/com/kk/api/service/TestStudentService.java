package com.kk.api.service;

import com.kk.api.dto.TestStudentDto;
import com.kk.api.entity.TestStudent;
import com.kk.api.core.service.Service;

import java.util.List;

/**
* @author kk
* @date 2021/05/15
*/
public interface TestStudentService extends Service<TestStudent> {
    TestStudent findTestStudentByStuCodeAndTestsCode(Long stuCode,Long testsCode);

    Integer updateTestStudentByStuCodeAndTestsCode(TestStudent testStudent);

    List<TestStudentDto> findTestsByStuCode(Long stuCode);


}
