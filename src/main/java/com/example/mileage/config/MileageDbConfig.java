package com.example.mileage.config;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * MySQL DB 설정 파일.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.mileage.repository.mileage")
public class MileageDbConfig {

  private final Environment env;

  public MileageDbConfig(Environment env) {
    this.env = env;
  }

  /**
   * application.yml에 설정된 DB 정보를 읽어서 dataSource를 생성한다.
   *
   * @return dataSource
   */
  @Bean
  @Primary
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(
        Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
    dataSource.setUrl(env.getProperty("spring.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.password"));
    return dataSource;
  }
}
