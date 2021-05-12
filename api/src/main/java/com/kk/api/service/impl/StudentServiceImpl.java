package com.kk.api.service.impl;

import com.kk.api.mapper.StudentMapper;
import com.kk.api.entity.Student;
import com.kk.api.service.StudentService;
import com.kk.api.core.service.AbstractService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2021/05/04
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl extends AbstractService<Student> implements StudentService {
@Resource
private StudentMapper studentMapper;
    @Resource private PasswordEncoder passwordEncoder;

    @Override
    public int resetPassword(Long id) {
        return studentMapper.resetPassword(id);
    }

    @Override
    public boolean verifyPassword(final String rawPassword, final String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
