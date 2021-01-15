package com.demons.manager.api.service;


import com.demons.manager.api.entity.Teacher;
import com.demons.manager.utils.Response;

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
  Response<List<Teacher>> getTeacherInfo(List<Integer> ids);

  /***
   * 删除教师信息
   * @param ids 索引
   * @return 删除结果
   */
  Response<Object> deleteTeacherInfo(List<Integer> ids);

  /***
   * 批量插入教师信息
   * @param teacherList 信息
   * @return 插入结果
   */
  Response<Object> insertTeacherInfo(List<Teacher> teacherList);

  /***
   * 更新教师信息
   * @param id 索引
   * @param teacher 更新内容
   * @return 更新结果
   */
  Response<Object> updateTeacherInfo(Integer id, Teacher teacher);

}
