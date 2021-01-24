package com.manager.gateway.filter;

import com.manager.gateway.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : Outro
 * Description : 统一鉴权
 **/
@Component
public class TokenFilter implements GlobalFilter, Ordered {

  private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("---------- Start Gateway authentication ----------");
    final ServerHttpRequest serverHttpRequest = exchange.getRequest();
    final String requestPath = serverHttpRequest.getURI().getPath();
    logger.info("Gateway request path:{}", requestPath);
    final String requestRawPath = serverHttpRequest.getURI().getRawPath();
    logger.info("Gateway request raw path:{}", requestRawPath);
    final HttpMethod requestMethod = serverHttpRequest.getMethod();
    if (requestMethod == null) {
      logger.warn("Gateway request method is null!");
      return getResponse(exchange, 450, "Request method is null!");
    }
    logger.info("Gateway request method:{}", requestMethod);
    // 验证用户名和token是否有效
    AtomicBoolean isRightToken = new AtomicBoolean(false);
    if (requestMethod.matches(HttpMethod.GET.name())) {
      final MultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
      logger.info("Gateway request params:{}", queryParams);
      handleUserNameAndToken(queryParams, isRightToken);
    } else if (requestMethod.matches(HttpMethod.POST.name())) {
      final HttpHeaders requestHeader = serverHttpRequest.getHeaders();
      logger.info("Gateway request header:{}", requestHeader);
      handleUserNameAndToken(requestHeader, isRightToken);
    } else {
      return getResponse(exchange, 451, "Not support request method:" + requestMethod);
    }
    if (isRightToken.get()) {
      return chain.filter(exchange);
    }
    logger.warn("Gateway request authentication failed!");
    logger.info("---------- End Gateway authentication ----------");
    return getResponse(exchange, 401, "authentication failed!");
  }

  private void handleUserNameAndToken(MultiValueMap<String, String> queryParams, AtomicBoolean isRightToken) {
    final boolean hasUserName = StringUtils.isNotBlank(queryParams.getFirst("username"));
    final boolean hasToken = StringUtils.isNotBlank(queryParams.getFirst("token"));
    if (hasUserName && hasToken) {
      isRightToken.set(true);
    }
  }

  private Mono<Void> getResponse(ServerWebExchange exchange, int code, String reason) {
    ServerHttpResponse serverHttpResponse = exchange.getResponse();
    serverHttpResponse.setStatusCode(HttpStatus.OK);
    final ResponseVo responseVo = new ResponseVo(code, reason);
    final DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(responseVo.toString().getBytes(StandardCharsets.UTF_8));
    return serverHttpResponse.writeWith(Mono.just(dataBuffer));
  }

  @Override
  public int getOrder() {
    // 该过滤器的执行顺序,值越小,执行优先级越高
    return 0;
  }
}
