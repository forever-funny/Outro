package com.demons.manager.api.controller;

import com.demons.manager.api.service.TeacherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ♞Demons♛
 * @date : 2020-10-17 23:09
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

}
