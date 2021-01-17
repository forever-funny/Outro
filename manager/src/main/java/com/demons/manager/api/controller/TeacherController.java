package com.demons.manager.api.controller;

import com.demons.manager.api.entity.Teacher;
import com.demons.manager.api.service.impl.TeacherInterfaceImpl;
import com.demons.manager.utils.JacksonUtils;
import com.demons.manager.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author : Outro
 * Description :
 **/
@RestController
@RequestMapping("/api/v1/teacher")
@Api(tags = "teacher接口层")
public class TeacherController {

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
  public Response<List<Teacher>> getTeacherInfo(@RequestParam List<Integer> ids) {
    return teacherInterface.getTeacherInfo(ids);
  }

  @ApiOperation(value = "插入教师信息")
  @PostMapping(value = "/insertTeacherInfo")
  public Response<Object> insertTeacherInfo(@RequestBody List<Teacher> teacherList) {
    return teacherInterface.insertTeacherInfo(teacherList);
  }

  @ApiOperation(value = "删除教师信息")
  @DeleteMapping(value = "/deleteTeacherInfo")
  public Response<Object> deleteTeacherInfo(@RequestParam List<Integer> ids) {
    return teacherInterface.deleteTeacherInfo(ids);
  }

  @ApiOperation(value = "更新教师信息")
  @PutMapping(value = "/updateTeacherInfo")
  public Response<Object> updateTeacherInfo(@RequestParam Integer id, @RequestBody Teacher teacher) {
    return teacherInterface.updateTeacherInfo(id, teacher);
  }

  @ApiOperation(value = "测试json格式展示结构")
  @RequestMapping(value = "/tests", method = {RequestMethod.GET, RequestMethod.POST})
  public String testJson() {
    final Map<String, Object> map = new HashMap<>();
    final ArrayList<String> array = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      map.put(i + "", UUID.randomUUID().toString());
      array.add(UUID.randomUUID().toString());
    }
    map.put("arr", array);
    return JacksonUtils.map2JsonString(map);
  }
}
