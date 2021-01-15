package com.demons.manager.utils;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * @author : Outro
 * Description :
 **/
public class ResponseTest extends TestCase {

  public void setUp() throws Exception {
    super.setUp();
  }

  public void testGetCode() {
  }

  public void testGetReason() {
  }

  public void testGetResult() {
  }

  public void testOk() {
  }

  public void testTestFail() {
  }

  public void testTestToString() {
    final Response<Integer> response = new Response<>(200, "ok", 3);
    System.out.println(response);
    Assert.assertTrue(true);
  }
}