package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.dto.ClazzTestsBank;
import com.kk.api.entity.Tests;
import com.kk.api.service.TestsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kk
 * @date 2021/05/09
 */
@RestController
@RequestMapping("/tests")
public class TestsController {
    @Resource
    private TestsService testsService;

    @PostMapping
    public Result add(@RequestBody Tests tests) {
        testsService.save(tests);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Tests tests) {
        testsService.update(tests);
        return ResultGenerator.genOkResult();
    }

    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestBody Tests tests) {
        testsService.updateClazzIdById(tests.getId(), tests.getClazzId());
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Tests tests = testsService.getById(id);
        return ResultGenerator.genOkResult(tests);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Tests> list = testsService.listAll();
        PageInfo<Tests> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/{clazzId}/findListByClazz")
    public Result getTestsList(@PathVariable Long clazzId) {

        //Tests tests = testsService.getBy("clazz_id",clazzId);

        List<ClazzTestsBank> clazzTestsBankList = testsService.getClazzTestsBank(clazzId);
        if(clazzTestsBankList!=null)
            return ResultGenerator.genOkResult(clazzTestsBankList);
        else
            return ResultGenerator.genFailedResult("无题目列表");
    }
}
