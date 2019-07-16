package com.galaxy.authentication.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.galaxy.authentication.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.galaxy.authentication.security.JwtAuthenticationEntryPoint;
import com.galaxy.authentication.security.JwtAuthorizationTokenFilter;
import com.galaxy.authentication.service.jwt.JwtTokenService;
import com.galaxy.authentication.service.jwt.JwtUserDetailsServiceImpl;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProperties authProps;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider(authProps.getIsAuthenticatePassword()));
        auth.userDetailsService(jwtUserDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // cors
                .cors().and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(authProps.getJwt().getLoginPath() + "/**").permitAll()
                .antMatchers(authProps.getJwt().getSsoPath() + "/**").permitAll().anyRequest().authenticated();
        // Custom JWT based security filter
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(),
                jwtTokenService, authProps.getJwt().getHeader());
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        // required to set for H2 else H2 Console will be blank.
        httpSecurity.headers().frameOptions().sameOrigin()
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> ignorePaths = Optional.ofNullable(authProps.getJwt().getIgnorePaths()).orElse(new ArrayList<>());
        // 添加swagger相关的例外
        ignorePaths.add("/v2/api-docs");
        // 添加Actuator相关的例外
        ignorePaths.add("/actuator/**");
        // 添加获取公钥接口的例外
        ignorePaths.add("/security/publicKey/**");

        web.ignoring().antMatchers(ignorePaths.toArray(new String[ignorePaths.size()]));
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}
