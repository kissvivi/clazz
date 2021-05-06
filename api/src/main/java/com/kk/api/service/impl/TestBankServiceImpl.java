package com.kk.api.service.impl;

import com.kk.api.mapper.TestBankMapper;
import com.kk.api.entity.TestBank;
import com.kk.api.service.TestBankService;
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
public class TestBankServiceImpl extends AbstractService<TestBank> implements TestBankService {
@Resource
private TestBankMapper testBankMapper;

}
