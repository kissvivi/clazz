package com.kk.api.service.impl;

import com.kk.api.mapper.TestStudentMapper;
import com.kk.api.entity.TestStudent;
import com.kk.api.service.TestStudentService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2021/05/02
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestStudentServiceImpl extends AbstractService<TestStudent> implements TestStudentService {
@Resource
private TestStudentMapper testStudentMapper;

}