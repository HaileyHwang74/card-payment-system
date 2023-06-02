package net.e4net.demo.Config;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.JWT.JwtAccessDeniedHandler;
import net.e4net.demo.JWT.JwtAuthenticationEntryPoint;
import net.e4net.demo.JWT.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/send-one").permitAll()
                .antMatchers("/money/**").permitAll()
                .antMatchers("/members").permitAll()
                .antMatchers("/findAddress").permitAll()
//                .antMatchers("/merchant/addMerchant").hasAnyRole("ADMIN","SELLER")
//                .antMatchers("/merchant").hasAnyRole("ADMIN","SELLER")
                .antMatchers("/merchant/**").permitAll()
                .antMatchers("/merchant").permitAll()
                .antMatchers("/moneyHistory").permitAll()
                .antMatchers("/moneyHistory/**").permitAll()
                .antMatchers("/api.iamport.kr/*").permitAll()



                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}