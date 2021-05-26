package com.kk.api.service.impl;

import com.kk.api.dto.ScoreRank;
import com.kk.api.dto.TestStudentClazzDto;
import com.kk.api.dto.TestStudentDto;
import com.kk.api.mapper.TestStudentMapper;
import com.kk.api.entity.TestStudent;
import com.kk.api.service.TestStudentService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author kk
* @date 2021/05/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestStudentServiceImpl extends AbstractService<TestStudent> implements TestStudentService {
@Resource
private TestStudentMapper testStudentMapper;

    @Override
    public TestStudent findTestStudentByStuCodeAndTestsCode(Long stuCode, Long testsCode) {
        return testStudentMapper.findTestStudentByStuCodeAndTestsCode(stuCode,testsCode);
    }

    @Override
    public Integer updateTestStudentByStuCodeAndTestsCode(TestStudent testStudent) {
        return testStudentMapper.updateTestStudentByStuCodeAndTestsCode(testStudent);
    }

    @Override
    public List<TestStudentDto> findTestsByStuCode(Long stuCode) {
        return testStudentMapper.findTestsByStuCode(stuCode);
    }

    @Override
    public List<TestStudentDto> findTests() {
        return testStudentMapper.findTests();
    }

    @Override
    public ScoreRank getScoreRankByTestsCode(Long testsCode) {
        return testStudentMapper.getScoreRankByTestsCode(testsCode);
    }

    @Override
    public List<TestStudentClazzDto> getStudentTestsByTestsCode(Long testsCode) {
        return testStudentMapper.getStudentTestsByTestsCode(testsCode);
    }
}
