package com.demons.manager.api.service.impl;

import com.demons.manager.api.entity.Teacher;
import com.demons.manager.api.entity.TeacherExample;
import com.demons.manager.api.mapper.TeacherMapper;
import com.demons.manager.api.service.TeacherInterface;
import com.demons.manager.utils.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
  public Response<List<Teacher>> getTeacherInfo(List<Integer> ids) {
    final TeacherExample teacherExample = new TeacherExample();
    final TeacherExample.Criteria criteria = teacherExample.createCriteria();
    if (ids.isEmpty()) {
      return new Response<>(200, "查询参数为空", new ArrayList<>());
    }
    if (ids.size() == 1 && ids.get(0) < 0) {
      // 当只传一个参数且为负数时,默认查询全部
      criteria.andIdIsNotNull();
    } else {
      criteria.andIdIn(ids);
    }
    final List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
    return new Response<>(200, "get teacher info complete", teacherList);
  }

  @Override
  public Response<Object> deleteTeacherInfo(List<Integer> ids) {
    if (ids != null && !ids.isEmpty()) {
      for (Integer id : ids) {
        final int delete = teacherMapper.deleteByPrimaryKey(id);
        if (delete != 1) {
          return new Response<>(20001, "delete fail");
        }
      }
    }
    return new Response<>(200, "delete ok");
  }

  @Override
  public Response<Object> insertTeacherInfo(List<Teacher> teacherList) {
    for (Teacher teacher : teacherList) {
      int result = teacherMapper.insert(teacher);
      if (result != 1) {
        final String reason = "insert fail! " + teacher.toString();
        return new Response<>(20002, reason);
      }
    }
    final String reason = "insert complete, size:" + teacherList.size();
    return new Response<>(200, reason);
  }

  @Override
  public Response<Object> updateTeacherInfo(Integer id, Teacher teacher) {
    if (id != null && id >= 0 && teacher != null) {
      final TeacherExample teacherExample = new TeacherExample();
      final TeacherExample.Criteria criteria = teacherExample.createCriteria();
      criteria.andIdEqualTo(id);
      teacher.setId(id);
      final int result = teacherMapper.updateByExampleSelective(teacher, teacherExample);
      if (result == 1) {
        return new Response<>(200, "update ok");
      }
    }
    return new Response<>(20003, "update fail");
  }
}
