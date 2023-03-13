package tn.workbot.coco_marketplace.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.workbot.coco_marketplace.services.auth.ApplicationUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    private static  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/auth",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/store").access("hasRole('SELLER')")
                .antMatchers("/PromotionCode/**").access("hasRole('SELLER')")
                .antMatchers("/productCategory/**").access("hasRole('SELLER')")
                .antMatchers("/Pickup/**").access("hasRole('SELLER')")
                .antMatchers(" /buyer/productQuantity/**").access("hasRole('BUYER')")
                .antMatchers("/Privilege/**").access("hasRole('MODERATOR')")
                .antMatchers("/user/**").permitAll()
//                .antMatchers("/**/displayRegistration").permitAll()
//                .antMatchers("/**/registreUser").permitAll()
//                .antMatchers("/**/confirm-account").permitAll()
                .antMatchers("/Registre/**").permitAll()
                .antMatchers("/**/changePass").permitAll()
                .antMatchers("/**/resetPassword").permitAll()
                .antMatchers("/**/checkEmail").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Ne pas de creer de session ( api REST )

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//hachage

    public static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}

