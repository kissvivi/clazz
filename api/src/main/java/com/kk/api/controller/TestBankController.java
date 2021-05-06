package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.TestBank;
import com.kk.api.service.TestBankService;
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
@RequestMapping("/testBank")
public class TestBankController {
@Resource
private TestBankService testBankService;

@PostMapping
public Result add(@RequestBody TestBank testBank) {
testBankService.save(testBank);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
testBankService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody TestBank testBank) {
testBankService.update(testBank);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
TestBank testBank = testBankService.getById(id);
return ResultGenerator.genOkResult(testBank);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<TestBank> list = testBankService.listAll();
PageInfo<TestBank> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
