package com.example.reviewMovie;

import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@ComponentScan
public class ReviewMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMoviesApplication.class, args);
	}
	private List<Parameter> getGlobalHeaders() {
		List<Parameter> parametersList = new LinkedList<>();
		parametersList.add(getParameter("X-Review-Movie-Session-Token", "Valid Session Token", "string", "header", false));
		parametersList.add(getParameter("X-Review-Movie-Email", "Valid Session Token", "string", "header", false));
		parametersList.add(getParameter("X-Review-Movie-App-Id", "Valid App Id", "string", "header", true));
		parametersList.add(getParameter("X-Review-Movie-API-Key", "Valid Api Key", "string", "header", true));
		return parametersList;
	}

	private List<ResponseMessage> getGlobalResponseMessages() {
		List<ResponseMessage> responseMessageList = new LinkedList<>();
		responseMessageList.add(getGlobalMessage("Internal server error.", 500));
		responseMessageList.add(getGlobalMessage("Unauthorized", 401));
		responseMessageList.add(getGlobalMessage("Forbidden.", 403));
		responseMessageList.add(getGlobalMessage("Not found.", 404));
		responseMessageList.add(getGlobalMessage("Success.", 200));
		return responseMessageList;
	}

	private ResponseMessage getGlobalMessage(String errorMessage, int errorCode) {
		return new ResponseMessageBuilder().code(errorCode).message(errorMessage).responseModel(new ModelRef("Error"))
		                .build();
	}

	private Parameter getParameter(String paramName, String paramDescription, String paramDataType, String paramType,
	                boolean isRequired) {
		return new ParameterBuilder().name(paramName).description(paramDescription)
		                .modelRef(new ModelRef(paramDataType)).parameterType(paramType).required(isRequired).build();
	}

	
	@Bean
	public Docket reviewMovieDocumentation() {
		String groupName = "review Movie";
		String basePackage = "com.example.reviewMovie.controller";
		return getDocumentation(groupName, basePackage);
	}
	
	private Docket getDocumentation(String groupName, String packageName) {
		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(metadata()).select()
		                .apis(RequestHandlerSelectors.basePackage(packageName)).build()
		                .globalOperationParameters(getGlobalHeaders());
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder().title("movie review Documentation")
		                .description("movie review Documentation with Swagger")
		                .contact("Product movie review").version("1.0").build();
	}
	

}
