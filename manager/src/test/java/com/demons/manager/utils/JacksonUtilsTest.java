package com.demons.manager.utils;

import com.demons.manager.api.entity.Teacher;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * @author : Administrator
 * @date : 2021-01-17 0017 9:48
 * Description :
 **/
public class JacksonUtilsTest extends TestCase {

  public void setUp() throws Exception {
    super.setUp();
  }

  public void testObj2JsonString() throws Exception {
    final Response<Object> res = new Response<>(200, "ok");
    System.out.println(JacksonUtils.obj2JsonString(res));
    System.out.println(JacksonUtils.obj2JsonStringIgnoreNull(res));
    final String jsonString = "{\"name\":\"xiha\",\"age\":666,\"result\":{\"nani\":\"wuwu\"}}";
    System.out.println(JacksonUtils.jsonString2Pojo(jsonString, Teacher.class));
    System.out.println(JacksonUtils.jsonString2Map(jsonString));
    final HashMap<String, Object> map = new HashMap<>();
    map.put("a", "aa");
    map.put("b", "bb");
    System.out.println(JacksonUtils.map2JsonString(map));
  }

  public void testObj2JsonStringIgnoreNull() {
  }

  public void testJsonString2Pojo() {
  }
}