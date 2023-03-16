package com.x14n.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

//@Configuration
//@EnableSwagger2WebMvc
public class Knife4jConfiguration {

	@Value("${swagger.enable}")
	private boolean enable;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
        		.enable(enable)
                .apiInfo(
                	new ApiInfoBuilder()
					//.title("swagger-bootstrap-ui-demo RESTful APIs")
	                .description("# swagger-bootstrap-ui-demo RESTful APIs")
	                .termsOfServiceUrl("http://www.xx.com/")
	                .contact(new Contact("x14n", "123", "xxxxxx@qq.com"))
	                .version("1.0")
	                .build()
                )
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定你自己的Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.x14n.web.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
