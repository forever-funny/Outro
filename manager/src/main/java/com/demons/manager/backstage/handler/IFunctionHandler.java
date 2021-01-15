package com.demons.manager.backstage.handler;

import com.demons.manager.utils.Response;

import java.util.Map;

/**
 * @author Outro
 * 数据解析接口
 */
public interface IFunctionHandler {
    
    /**
     * 处理请求
     * @param header 请求头
     * @param body body
     * @return 响应
     */
    Response execute(Map<String, String> header, byte[] body);
}
