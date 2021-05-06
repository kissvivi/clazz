package com.kk.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.zxing.WriterException;
import com.kk.api.Constant.QRConstant;
import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Clazz;
import com.kk.api.service.ClazzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.api.util.QRCodeGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
* @author kk
* @date 2021/05/02
*/
@RestController
@RequestMapping("/clazz")
public class ClazzController {
@Resource
private ClazzService clazzService;

@PostMapping
public Result add(@RequestBody Clazz clazz) {
clazzService.save(clazz);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
clazzService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Clazz clazz) {
clazzService.update(clazz);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Clazz clazz = clazzService.getById(id);
return ResultGenerator.genOkResult(clazz);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Clazz> list = clazzService.listAll();
PageInfo<Clazz> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}


    @GetMapping("getQRCode")
    public Result getQRCode(@RequestParam(defaultValue = "0") Integer clazzId) throws IOException, WriterException {

        Clazz clazz  = clazzService.getById(clazzId);

        String clazzJson =  JSON.toJSONString(clazz);

        String url = QRCodeGenerator.generateQRCodeImage(clazzJson, QRConstant.QR_CODE_IMAGE_WIDTH,QRConstant.QR_CODE_IMAGE_HEIGHT,QRConstant.QR_CODE_IMAGE_PATH);

        clazz.setUrl(url);
        clazzService.update(clazz);
        return ResultGenerator.genOkResult(clazz);
    }


}
