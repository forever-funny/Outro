package com.demons.manager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Outro
 * 使用gzip进行数据的压缩和解压缩
 */
public class CompressionUtils {

  private static final Logger logger = LoggerFactory.getLogger(CompressionUtils.class);

  private CompressionUtils() {
  }

  /**
   * 使用gzip解压数据
   * @param data 字节数组
   * @return json数据
   */
  public static String unzipData(byte[] data) {
    ByteArrayInputStream bis = new ByteArrayInputStream(data);
    String result;
    try {
      GZIPInputStream gis = new GZIPInputStream(bis);
      byte[] bytesData = new byte[1024];
      int len;
      ByteArrayOutputStream boos = new ByteArrayOutputStream();
      while ((len = gis.read(bytesData, 0, bytesData.length)) != -1) {
        boos.write(bytesData, 0, len);
      }
      result = boos.toString("UTF-8");
      // 记得关流
      gis.close();
      boos.close();
    } catch (IOException e) {
      logger.error("Get gzip data exception!", e);
      return "{}";
    }
    return result;
  }

  /**
   * 使用gzip对获取到的数据流进行压缩
   * @param data 字节数组
   * @return 压缩后的字节数组
   * @throws IOException IO异常
   */
  public static byte[] zipData(byte[] data) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    GZIPOutputStream gzip = new GZIPOutputStream(bos);
    gzip.write(data);
    gzip.finish();
    gzip.close();
    return bos.toByteArray();
  }
}
