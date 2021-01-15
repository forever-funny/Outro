package com.demons.manager.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demons.manager.api.service.TeacherInterface;
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
public class TeacherController {

  @Autowired
  TeacherInterface teacherInterface;

  @GetMapping(value = "/getTeacherInfo")
  public String getTeacherList() {
    return teacherInterface.getTeacherInfo();
  }

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
