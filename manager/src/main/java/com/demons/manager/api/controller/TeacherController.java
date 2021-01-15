package com.demons.manager.api.controller;

import com.demons.manager.api.service.TeacherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
