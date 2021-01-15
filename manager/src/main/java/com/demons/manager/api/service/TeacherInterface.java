package com.demons.manager.api.service;


import com.demons.manager.api.entity.Teacher;

import java.util.List;

/**
 * @author : Outro
 * Description :
 **/
public interface TeacherInterface {

  /***
   * 批量查询教师信息
   * @param ids 唯一id
   * @return 结果
   */
  List<Teacher> getTeacherInfo(List<Integer> ids);

  String deleteTeacherInfo(List<Integer> ids);

  /***
   * 批量插入教师信息
   * @param teacherList 信息
   * @return 插入结果
   */
  String insertTeacherInfo(List<Teacher> teacherList);

  String updateTeacherInfo(Integer id, Teacher teacher);

}
