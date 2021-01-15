package com.demons.manager.api.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author : Outro
 * Description :
 **/
public class Teacher {

  @JSONField(name = "name")
  private String name;
  @JSONField(name = "age")
  private Integer age;

  public Teacher(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
