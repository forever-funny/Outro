package com.demons.manager.api.dto;

/**
 * @author : Administrator
 * @date : 2021-01-15 0015 16:58
 * Description :
 **/
public class Teacher {

  private String name;
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
    return "Teacher{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}