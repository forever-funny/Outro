package com.demons.manager.backstage.handler.business;

import com.demons.manager.backstage.annotation.NettyHttpHandler;
import com.demons.manager.backstage.consumer.ManagerProducer;
import com.demons.manager.backstage.handler.IFunctionHandler;
import com.demons.manager.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Outro
 * 处理用户自定义http上报的custom数据
 */
@NettyHttpHandler(path = "/upload/v1/custom")
public class CustomHandler implements IFunctionHandler {

  @Autowired
  ManagerProducer managerProducer;

  /**
   * @param header 请求头
   * @param body body
   * @return 响应结果
   */
  @Override
  public Response<Object> execute(Map<String, String> header, byte[] body) {
    managerProducer.sendData2Kafka(body);
    return new Response<>(200, "Data received successfully!");
  }
}
