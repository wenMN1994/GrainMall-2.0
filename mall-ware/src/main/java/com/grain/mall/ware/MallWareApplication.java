package com.grain.mall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/5/21 16:45
 * @description：
 * @modified By：
 * @version: $
 */
@EnableTransactionManagement
@MapperScan("com.grain.mall.ware.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class MallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallWareApplication.class, args);
    }

}
