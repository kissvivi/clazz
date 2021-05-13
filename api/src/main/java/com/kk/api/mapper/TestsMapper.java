package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.dto.ClazzTestsBank;
import com.kk.api.entity.Tests;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TestsMapper extends MyMapper<Tests> {

    @Update("update tests set clazz_id = #{clazzId},status = 1 where id =#{id} ")
    int updateClazzIdById(Long id,Long clazzId);

    @Select("select * from tests where clazz_id =#{clazzId}")
    List<ClazzTestsBank> findTestsByClazzId(Long clazzId);


}