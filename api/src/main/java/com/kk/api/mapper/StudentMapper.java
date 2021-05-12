package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.entity.Student;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper extends MyMapper<Student> {

    @Update("update student set password = '123456' where id =#{id}")
    int resetPassword(Long id);



}