package com.demons.manager;

import com.demons.manager.backstage.annotation.NettyHttpHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Outro
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.demons.manager.api.mapper")
@ComponentScan(includeFilters = @ComponentScan.Filter(NettyHttpHandler.class))
public class ManagerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ManagerApplication.class, args);
  }
}
