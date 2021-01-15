package com.demons.manager.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demons.manager.api.service.impl.TeacherInterfaceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author : Outro
 * Description :
 **/
@RestController
@RequestMapping("/api/v1/teacher")
@Api(tags = "teacher接口层")
public class TeacherController {

  private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

  final TeacherInterfaceImpl teacherInterface;

  /**
   * 实测在使用构造方式注入时@Autowired注解可有可无
   * @param teacherInterface 接口实现类
   */
  @Autowired
  public TeacherController(TeacherInterfaceImpl teacherInterface) {
    this.teacherInterface = teacherInterface;
  }

  @ApiOperation(value = "查询教师信息")
  @GetMapping(value = "/getTeacherInfo")
  public String getTeacherList() {
    return teacherInterface.getTeacherInfo();
  }

  @ApiOperation(value = "测试json格式展示结构")
  @RequestMapping(value = "/tests", method = {RequestMethod.GET, RequestMethod.POST})
  public JSONObject testJson() {
    final JSONObject jsonObject = new JSONObject();
    final JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < 5; i++) {
      jsonObject.put(i + "", UUID.randomUUID().toString());
      jsonArray.add(UUID.randomUUID().toString());
    }
    jsonObject.put("arr", jsonArray);
    return jsonObject;
  }
}
