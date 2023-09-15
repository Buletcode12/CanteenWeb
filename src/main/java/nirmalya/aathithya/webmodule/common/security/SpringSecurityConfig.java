/**
 *  Configures Web Security
 */
package nirmalya.aathithya.webmodule.common.security;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;
import nirmalya.aathithya.webmodule.user.service.CustomUserDetailsService;

/**
 * @author Nirmalya Labs
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	HttpSession session;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
//	@Autowired
//	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
//	}
	
	 @Autowired
	 public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
       authenticationManagerBuilder.userDetailsService(customUserDetailsService)
       							.passwordEncoder(passwordEncoder);
   }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	public void urlCheck() {
		
		
		
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/oauth/token").permitAll()
				.antMatchers("/api-docs/**").permitAll()
				.antMatchers("/index-assets/**").permitAll()
				
				.antMatchers("/register").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/term-and-condition").permitAll()
				.antMatchers("/forgot-password").permitAll()
				.antMatchers("/get-otp").permitAll()
				.antMatchers("/save-new-password").permitAll()
				.antMatchers("/order-status").permitAll()
				.antMatchers("/restaurant/kitchen-staff-order-details").permitAll()
				.antMatchers("/restaurant/kitchen-staff-order-details-modal").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/extend/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/nirmalyaRest/**").permitAll()
				.antMatchers("/datatables/**").permitAll()
				.antMatchers("/FileUpload/**").permitAll()
				.antMatchers("/document/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/about-us").permitAll()
				.antMatchers("/contact").permitAll()
				.antMatchers("/javascript/**").permitAll()
				
				
				
				.antMatchers("/recruitment/**").permitAll()
				.antMatchers("/recruitment/offer-letter-pdf/").permitAll()
				.antMatchers("/index-get-breadcrumb-data").permitAll()
				.antMatchers("/master/advanceManagement").permitAll()
				.antMatchers("/layout/**").permitAll()
 
				.antMatchers("/master/**").permitAll()
				.antMatchers("/master/payslip-pdf-download").permitAll()
				.antMatchers("/employee/**").permitAll()
				.antMatchers("/employee/birthday-wish").permitAll()
				
				.antMatchers("/master/view-payslip-mobile/**").permitAll()
				.antMatchers("/master/getEmployeeListsSlip/**").permitAll()
				
				.antMatchers("/master/view-tax-mobile/**").permitAll()
				.antMatchers("/account/**").permitAll()
				.antMatchers("/account/viewbank/**").permitAll()
				
				.antMatchers("/sales/**").permitAll()
				.antMatchers("/inventory/**").permitAll()
				.antMatchers("/purchase/**").permitAll()
				.antMatchers("/employee/**").permitAll()
				.antMatchers("/gatepass/**").permitAll()
				
				.and().formLogin().loginPage("/login").permitAll().successHandler(customAuthenticationSuccessHandler)
				.and().authorizeRequests().antMatchers("/**").authenticated()
				;
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1).expiredUrl("/login?expired");

	}

//	@Bean
//	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();
//	}


//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}

	

}
