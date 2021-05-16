package com.kk.api.controller;

import com.kk.api.core.jwt.JwtUtil;
import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.dto.AccountWithRolePermission;
import com.kk.api.dto.LoginDto;
import com.kk.api.entity.Account;
import com.kk.api.entity.Student;
import com.kk.api.service.AccountService;
import com.kk.api.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.api.service.impl.AccountDetailsServiceImpl;
import com.kk.api.util.ExcelUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kk
 * @date 2021/05/04
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource private AccountDetailsServiceImpl userDetailsService;
    @Resource
    private JwtUtil jwtUtil;

    @Resource private AccountService accountService;

    @PostMapping
    public Result add(@RequestBody Student student) {
        studentService.save(student);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PutMapping
    public Result update(@RequestBody Student student) {
        studentService.update(student);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return ResultGenerator.genOkResult(student);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Student> list = studentService.listAll();
        PageInfo<Student> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @PutMapping("resetPassword/{id}")
    public Result resetPassword(@PathVariable Long id) {
        studentService.resetPassword(id);
        return ResultGenerator.genOkResult();
    }

    /**
     * 修改密码
     * @param student
     * @return
     */
    @PutMapping("alterPassword")
    public Result alterPassword(@RequestBody Student student) {
        studentService.alterPassword(student.getCode(),student.getPassword());
        return ResultGenerator.genOkResult();
    }

    @PostMapping("login")
    public Result login(@RequestBody final LoginDto loginDto) {

        // 用户名登录
        Student student = null;
        if (loginDto.getUsername() != null) {
            student = this.studentService.getBy("code", loginDto.getUsername());
            if (student == null) {
                return ResultGenerator.genFailedResult("用户名错误");
            }
        }
//        // x学号登录
//        if (loginDto.getCode() != null) {
//            student = this.studentService.getBy("phone", loginDto.getCode());
//            if (student == null) {
//                return ResultGenerator.genFailedResult("手机号错误");
//            }
//            loginDto.setCode(student.getCode());
//        }
        // 验证密码
        //noinspection ConstantConditions
        if (!loginDto.getPassword().equals(student.getPassword())) {
            return ResultGenerator.genFailedResult("密码错误");
        }

        return this.getToken(student.getName());
    }



    @GetMapping("/exportTemp")
    public void export(HttpServletResponse response){
        //模拟从数据库获取需要导出的数据
        List<Student> studentList = new ArrayList<>();
        Student student = new Student();
        student.setCode("2021001");
        student.setName("demo");
        student.setPassword("123456");
        //导出操作
        ExcelUtils.exportExcel(studentList,"学生表","模板",Student.class,"学生导入模板表.xls",response);
    }

    @PostMapping("/importExcel")
    public Result importExcel(MultipartFile file){
        String filePath = "";
        //解析excel，
//      方式一:根据文件路径解析
//        List<Person> personList = ExcelUtil.importExcel(filePath,1,1,Person.class);

//      方式二:也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        List<Student> studentList = ExcelUtils.importExcel(file, 1, 1, Student.class);
        System.out.println("导入数据一共【"+studentList.size()+"】行");

        studentService.save(studentList);
        return  ResultGenerator.genOkResult();

    }




    /**
     * 获得 token
     */
    private Result getToken(final String name) {


        final UserDetails accountDetails = this.userDetailsService.loadUserByUsername("admin");
        final String token = this.jwtUtil.sign(name, accountDetails.getAuthorities(), true);
        return ResultGenerator.genOkResult(token);
    }

}


