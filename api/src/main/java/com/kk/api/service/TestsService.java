package com.kk.api.service;

import com.kk.api.dto.ClazzTestsBank;
import com.kk.api.entity.Tests;
import com.kk.api.core.service.Service;

import java.util.List;

/**
* @author kk
* @date 2021/05/09
*/
public interface TestsService extends Service<Tests> {

    int updateClazzIdById(Long id,Long clazzId);

    //获取班级考试题目列表
    List<ClazzTestsBank> getClazzTestsBank(Long clazzId);
}
