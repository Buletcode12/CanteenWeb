package nirmalya.aathithya.webmodule.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestInterceptor()).excludePathPatterns("/index-assets/**","/index-get-breadcrumb-data",
				"/assets/**", "/extend/**", "/css/**", "/datatables/**", "/FileUpload/**", "/download/**", "/js/**",
				"/login", "/logout", "/register", "/", "/account/viewbank","/document/**", "/access-denied", "/order-status","/sales",
				"/restaurant/kitchen-staff-order-details", "/restaurant/kitchen-staff-order-details-modal", "/error",
				"/recruitment/offer-letter-pdf","/master/payslip-pdf-download","/employee/birthday-wish","/production/**","/employee/**", "/javascript/**");
	}

}
