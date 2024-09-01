package com.qing.fan.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Configuration
@EnableOpenApi
@SuppressWarnings("unchecked")
public class SwaggerConfig {

    private static final String SPLIT = ";";

    @Bean(value = "userDocket")
    public Docket userDocket() {
        List<Response> responses = buildResponseList();
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.POST, responses)
                .apiInfo(apiInfo())
                .groupName("User API")
                .enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("com.qing.fan.controller"))
                .apis(basePackage("com.qing.fan.controller.user"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean(value = "personDocket")
    public Docket personDocket() {
        List<Response> responses = buildResponseList();
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.POST, responses)
                .apiInfo(apiInfo())
                .groupName("Person API")
                .enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("com.qing.fan.controller"))
                .apis(basePackage("com.qing.fan.controller.person"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试Swagger")
                .description("Swagger")
                .version("v1.0")
                .build();
    }

    private List<Response> buildResponseList() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("200").description("Success").build());
        responseList.add(new ResponseBuilder().code("400").description("参数错误").build());
        responseList.add(new ResponseBuilder().code("401").description("没有认证").build());
        responseList.add(new ResponseBuilder().code("403").description("没有访问权限").build());
        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
        responseList.add(new ResponseBuilder().code("500").description("服务器内部错误").build());
        return responseList;
    }

    /**
     * 重写basePackage方法，使能够实现多包访问
     *
     * @param basePackage 所有包路径
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return requestHandler -> {
            Class<?> clazz = requestHandler.declaringClass();
            // 匹配包
            for (String packageStr : basePackage.split(SPLIT)) {
                boolean matched = clazz.getPackage().getName().startsWith(packageStr);
                if (matched) {
                    return true;
                }
            }
            return false;
        };
    }

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
}