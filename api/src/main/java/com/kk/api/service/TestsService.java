package com.kk.api.service;

import com.kk.api.entity.Tests;
import com.kk.api.core.service.Service;

/**
* @author kk
* @date 2021/05/09
*/
public interface TestsService extends Service<Tests> {

    int updateClazzIdById(Long id,Long clazzId);
}
