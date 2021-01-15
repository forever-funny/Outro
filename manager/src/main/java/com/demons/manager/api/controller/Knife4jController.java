package com.demons.manager.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : Administrator
 * @date : 2021-01-15 0015 20:29
 * Description :
 **/
@RestController
@RequestMapping("/api/v1/knife4j")
@Api(tags = "knife4j测试接口层")
public class Knife4jController {

  @ApiOperation(value = "测试swagger格式")
  @ApiImplicitParam(name = "req", value = "请求体参数", required = true)
  @PostMapping(value = "/format")
  public String format(@RequestBody Map<String, String> req) {
    return "format api, do nothing..." + req;
  }

  @ApiOperation(value = "测试swagger请求头参数说明")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "用户名", required = true),
      @ApiImplicitParam(name = "token", value = "token", required = true),
      @ApiImplicitParam(name = "id", value = "唯一ID"),
  })
  @GetMapping(value = "/reqParam")
  public String reqParam(@RequestParam String userName,
                         @RequestParam String token,
                         @RequestParam Integer id) {
    return "userName:" + userName + " - token:" + token + " - id:" + id;
  }

}
