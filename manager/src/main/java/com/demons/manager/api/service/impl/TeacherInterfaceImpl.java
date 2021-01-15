package com.demons.manager.api.service.impl;

import com.demons.manager.api.entity.Teacher;
import com.demons.manager.api.entity.TeacherExample;
import com.demons.manager.api.mapper.TeacherMapper;
import com.demons.manager.api.service.TeacherInterface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Outro
 * Description :
 **/
@Service
public class TeacherInterfaceImpl implements TeacherInterface {

  final TeacherMapper teacherMapper;

  public TeacherInterfaceImpl(TeacherMapper teacherMapper) {
    this.teacherMapper = teacherMapper;
  }

  @Override
  public List<Teacher> getTeacherInfo(List<Integer> ids) {
    final TeacherExample teacherExample = new TeacherExample();
    final TeacherExample.Criteria criteria = teacherExample.createCriteria();
    if (!ids.isEmpty()) {
      criteria.andIdIn(ids);
    }
    return teacherMapper.selectByExample(teacherExample);
  }

  @Override
  public String deleteTeacherInfo(List<Integer> ids) {
    final TeacherExample teacherExample = new TeacherExample();
    final TeacherExample.Criteria criteria = teacherExample.createCriteria();
    criteria.andIdIn(ids);
    final int delete = teacherMapper.deleteByExample(teacherExample);
    return delete == 1 ? "delete ok" : "delete fail";
  }

  @Override
  public String insertTeacherInfo(List<Teacher> teacherList) {
    for (Teacher teacher : teacherList) {
      int result = teacherMapper.insert(teacher);
      if (result != 1) {
        return "insert fail! " + teacher.toString();
      }
    }
    return "insert complete, size:" + teacherList.size();
  }

  @Override
  public String updateTeacherInfo(Integer id, Teacher teacher) {
    final TeacherExample teacherExample = new TeacherExample();
    final TeacherExample.Criteria criteria = teacherExample.createCriteria();
    criteria.andIdEqualTo(id);
    teacher.setId(id);
    final int result = teacherMapper.updateByExampleSelective(teacher, teacherExample);
    return result == 1 ? "update ok" : "update fail";
  }
}
