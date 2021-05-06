package com.kk.api.service.impl;

import com.kk.api.mapper.ClazzMapper;
import com.kk.api.entity.Clazz;
import com.kk.api.service.ClazzService;
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
public class ClazzServiceImpl extends AbstractService<Clazz> implements ClazzService {
@Resource
private ClazzMapper clazzMapper;

}
