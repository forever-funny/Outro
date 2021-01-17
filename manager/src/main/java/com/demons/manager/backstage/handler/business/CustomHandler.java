package com.demons.manager.backstage.handler.business;

import com.demons.manager.backstage.annotation.NettyHttpHandler;
import com.demons.manager.backstage.consumer.ManagerProducer;
import com.demons.manager.backstage.handler.IFunctionHandler;
import com.demons.manager.utils.CompressionUtils;
import com.demons.manager.utils.Response;
import com.github.luben.zstd.Zstd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Outro
 * 处理用户自定义http上报的custom数据
 */
@NettyHttpHandler(path = "/upload/v1/custom")
public class CustomHandler implements IFunctionHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomHandler.class);

  @Autowired
  ManagerProducer managerProducer;

  /**
   * @param header 请求头
   * @param body body
   * @return 响应结果
   */
  @Override
  public Response<Object> execute(Map<String, String> header, byte[] body) {
    String data = getStrData(header, body);
    managerProducer.sendData2Kafka(data);
    return new Response<>(200, "Data received successfully!");
  }

  private String getStrData(Map<String, String> header, byte[] body) {
    final String contentEncoding = header.get("content-encoding");
    if (contentEncoding == null || "json".equalsIgnoreCase(contentEncoding)) {
      return new String(body, StandardCharsets.UTF_8);
    }
    switch (contentEncoding) {
      case "gzip":
        return CompressionUtils.unzipData(body);
      case "zstd":
        return new String(Zstd.decompress(body, 1024 * 1024 * 10), StandardCharsets.UTF_8);
      default:
        logger.warn("unknown content-encoding:{}, just skip!", contentEncoding);
        return "{}";
    }
  }
}
