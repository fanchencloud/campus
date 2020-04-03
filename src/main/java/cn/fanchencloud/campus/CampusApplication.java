package cn.fanchencloud.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/1
 * Time: 18:58
 * Description: 校园商铺平台 SpringBoot版本
 *
 * @author chen
 */
@SpringBootApplication
@EnableTransactionManagement
public class CampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusApplication.class, args);
    }

}
