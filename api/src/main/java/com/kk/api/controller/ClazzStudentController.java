package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.ClazzStudent;
import com.kk.api.service.ClazzStudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author kk
* @date 2021/05/02
*/
@RestController
@RequestMapping("/clazz/student")
public class ClazzStudentController {
@Resource
private ClazzStudentService clazzStudentService;

@PostMapping
public Result add(@RequestBody ClazzStudent clazzStudent) {
clazzStudentService.save(clazzStudent);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
clazzStudentService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody ClazzStudent clazzStudent) {
clazzStudentService.update(clazzStudent);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
ClazzStudent clazzStudent = clazzStudentService.getById(id);
return ResultGenerator.genOkResult(clazzStudent);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<ClazzStudent> list = clazzStudentService.listAll();
PageInfo<ClazzStudent> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
