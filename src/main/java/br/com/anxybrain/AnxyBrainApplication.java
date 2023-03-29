package br.com.anxybrain;

import br.com.anxybrain.config.swagger.SwaggerConfig;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@EnableAsync
@ServletComponentScan
@Import(SwaggerConfig.class)
public class AnxyBrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnxyBrainApplication.class, args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(100000000L));
		factory.setMaxRequestSize(DataSize.ofBytes(100000000L));
		return factory.createMultipartConfig();
	}

}
