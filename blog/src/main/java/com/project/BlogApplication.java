package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import com.project.security.GitProperties;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EntityScan(basePackageClasses = {BlogApplication.class, Jsr310JpaConverters.class})
@EnableCaching
@EnableConfigurationProperties({GitProperties.class})
public class BlogApplication extends SpringBootServletInitializer {
	
	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(BlogApplication.class);
	  }


  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      cm.createCache("blog.category", initConfiguration(TEN_SECONDS));
      cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
    };
  }

  public static final Duration TEN_SECONDS = new Duration(TimeUnit.SECONDS, 10);

  private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
    return new MutableConfiguration<>()
      .setStatisticsEnabled(true)
      .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
