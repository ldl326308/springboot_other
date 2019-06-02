
/*
 * User:liveGreen
 * Date: 2019/5/27
 */

package com.green.lc.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    /*
    * http://localhost:8080/swagger-ui.html
    * Swagger2默认将所有的Controller中的RequestMapping方法都会暴露
    * 然而在实际开发中，我们并不一定需要把所有API都体现在文档中查看，这种情况
    * 我们会使用注解@ApiIgnore来解决，如果应用在Controller范围上，则当前
    * Controller中的所有方法都会被忽略，如果应用在方法上，则对应的方法忽略暴露API
    * */

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("SpringBoot Other").description("SpringBoot Other 文档")
                .termsOfServiceUrl("https://www.cnblogs.com/ldl326308").contact("je_ge").version("1.0").build();
    }


    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.green.lc.controller"))
                .paths(PathSelectors.any()).build();
    }

}
