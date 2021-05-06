package com.kk.api.service;

import com.kk.api.entity.Student;
import com.kk.api.core.service.Service;

/**
* @author kk
* @date 2021/05/04
*/
public interface StudentService extends Service<Student> {

    int resetPassword(Long id);

}
