package com.kk.api.service.impl;

import com.kk.api.mapper.ClazzStudentMapper;
import com.kk.api.entity.ClazzStudent;
import com.kk.api.service.ClazzStudentService;
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
public class ClazzStudentServiceImpl extends AbstractService<ClazzStudent> implements ClazzStudentService {
@Resource
private ClazzStudentMapper clazzStudentMapper;

}
