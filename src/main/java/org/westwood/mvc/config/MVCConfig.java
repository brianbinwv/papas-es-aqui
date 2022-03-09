package org.westwood.mvc.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;



@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "org.westwood.mvc" })
public class MVCConfig implements WebMvcConfigurer {


   @Bean
   public ViewResolver viewResolver() {
      InternalResourceViewResolver bean = new InternalResourceViewResolver();

      bean.setViewClass(JstlView.class);
      bean.setPrefix("/WEB-INF/views/");
      bean.setSuffix(".jsp");

      return bean;
   }
   

// <!-- Add support for reading web resources: css, images, js, etc ... -->
// <mvc:resources location="/resources/" mapping="/resources/**"></mvc:resources>   
   
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry
           .addResourceHandler("/resources/**")
           .addResourceLocations("/resources/");
   }
   
   
   
}



