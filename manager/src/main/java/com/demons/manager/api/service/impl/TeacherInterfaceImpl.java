package com.demons.manager.api.service.impl;

import com.demons.manager.api.dto.Teacher;
import com.demons.manager.api.service.TeacherInterface;
import org.springframework.stereotype.Service;

/**
 * @author : ♞Demons♛
 * @date : 2020-10-18 0:56
 * Description :
 **/
@Service
public class TeacherInterfaceImpl implements TeacherInterface {

  @Override
  public String getTeacherInfo() {
    return new Teacher("Outro", 100).toString();
  }

}
