package com.demons.manager.api.entity;

/**
 * @author Outro
 */
public class Teacher {

  public Teacher() {
  }

  public Teacher(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public Teacher(String name, Integer age, String info) {
    this.name = name;
    this.age = age;
    this.info = info;
  }

  private Integer id;

  private String name;

  private Integer age;

  private String info;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info == null ? null : info.trim();
  }

  @Override
  public String toString() {
    return "Teacher{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", info='" + info + '\'' +
        '}';
  }
}