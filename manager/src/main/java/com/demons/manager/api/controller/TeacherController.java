package com.demons.manager.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demons.manager.api.entity.Teacher;
import com.demons.manager.api.service.impl.TeacherInterfaceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
  @RequestMapping(value = "/getTeacherInfo", method = {RequestMethod.GET, RequestMethod.POST})
  public List<Teacher> getTeacherInfo(@RequestParam List<Integer> ids) {
    return teacherInterface.getTeacherInfo(ids);
  }

  @ApiOperation(value = "插入教师信息")
  @PostMapping(value = "/insertTeacherInfo")
  public String insertTeacherInfo(@RequestBody List<Teacher> teacherList) {
    return teacherInterface.insertTeacherInfo(teacherList);
  }

  @ApiOperation(value = "删除教师信息")
  @GetMapping(value = "/deleteTeacherInfo")
  public String deleteTeacherInfo(@RequestParam List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
      return "无需删除任何内容";
    }
    return teacherInterface.deleteTeacherInfo(ids);
  }

  @ApiOperation(value = "更新教师信息")
  @PostMapping(value = "/updateTeacherInfo")
  public String updateTeacherInfo(@RequestParam Integer id, @RequestBody Teacher teacher) {
    return teacherInterface.updateTeacherInfo(id, teacher);
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
