package fr.hardis.mybatis;

import fr.hardis.mybatis.model.User;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan
@EnableMybatisRepositories
@MappedTypes(User.class)
@EnableScheduling
@SpringBootApplication
public class MyBatisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBatisProjectApplication.class, args);
	}


}
