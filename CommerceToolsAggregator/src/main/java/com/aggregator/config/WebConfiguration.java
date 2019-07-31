package com.aggregator.config;

/**
 * Class to enable h2 registration for in-memonry db usage
 */
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class WebConfiguration {
	/**
	 * Registers the h2 servlet and adds console mapping for h2 in memory db
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
