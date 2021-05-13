package com.kk.api.service.impl;

import com.kk.api.dto.ClazzTestsBank;
import com.kk.api.entity.TestBank;
import com.kk.api.mapper.TestBankMapper;
import com.kk.api.mapper.TestsMapper;
import com.kk.api.entity.Tests;
import com.kk.api.service.TestBankService;
import com.kk.api.service.TestsService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author kk
* @date 2021/05/09
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestsServiceImpl extends AbstractService<Tests> implements TestsService {
@Resource
private TestsMapper testsMapper;
    private TestBankMapper testBankMapper;
    @Resource
    private TestBankService testBankService;

    @Override
    public int updateClazzIdById(Long id, Long clazzId) {
        return testsMapper.updateClazzIdById(id,clazzId);
    }

    @Override
    public List<ClazzTestsBank> getClazzTestsBank(Long clazzId) {

        List<ClazzTestsBank> clazzTestsBanks = testsMapper.findTestsByClazzId(clazzId);

        for (int i = 0; i < clazzTestsBanks.size(); i++) {

            String tbids = clazzTestsBanks.get(i).getTbIds();

            String sa[] = tbids.split(",");

            List<TestBank> testBankList = new ArrayList<>();
            for (int j = 0; j <sa.length ; j++) {

                TestBank testBank = testBankService.getById(Long.parseLong(sa[j]));
                testBankList.add(testBank);

            }
            clazzTestsBanks.get(i).setTestBankList(testBankList);

        }

        return clazzTestsBanks;
    }
}
