package br.com.convergencia.testejava.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.convergencia.testejava.cpf.resource"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Controle de Cpf API").description("CRUD API para controle de CPF's")
				.termsOfServiceUrl("https://github.com/betofroesjr/")
				.contact(new Contact("José Humberto", "https://github.com/betofroesjr/", "betofroesjr@gmail.com"))
				.license("Convergencia License").licenseUrl("https://github.com/betofroesjr/").version("1.0.0").build();
	}
}