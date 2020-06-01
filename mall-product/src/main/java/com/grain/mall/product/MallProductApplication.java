package com.grain.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/5/21 16:45
 * @description：
 * 1、整合MyBatis-Plus
 *      1）、导入依赖
 *          <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.2.0</version>
 *         </dependency>
 *      2）、配置
 *          1、配置数据源
 *              1）、导入数据库驱动
 *              2）、在application.yml配置数据源相关信息
 *          2、MyBatis-Plus
 *              1）、使用MapperScan
 *              2）、告诉MyBatis-Plus，sql映射文件位置
 * @modified By：
 * @version: $
 */
@MapperScan("com.grain.mall.product.dao")
@SpringBootApplication
public class MallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }

}
