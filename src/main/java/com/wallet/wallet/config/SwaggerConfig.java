package com.wallet.wallet.config;

import com.wallet.wallet.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile(value = "dev")
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.wallet.wallet.controllers"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wallet Api")
                .description("Controle e gestão de carteiras")
                .version("0.1")
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername("development@swagger.user");
            token = jwtTokenUtil.getToken(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SecurityConfiguration(null, null, null, "Wallet Api", "Bearer " + token, ApiKeyVehicle.HEADER, "Authorization", ",");
    }

}
