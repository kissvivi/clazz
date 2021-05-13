package com.kk.api.dto;

import com.kk.api.entity.Clazz;
import com.kk.api.entity.TestBank;
import com.kk.api.entity.Tests;
import lombok.Data;

import java.util.List;

/**
 * @author kiss_vivi
 * @date 2021/5/13
 */
@Data
public class ClazzTestsBank extends Tests{
    private List<TestBank> testBankList;
}
