package org.example.security_app.config;

import org.example.security_app.repositories.PeopleRepository;
import org.example.security_app.servcies.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
    @Bean
    public PersonDetailsService personDetailsService(PeopleRepository peopleRepository){
        return new PersonDetailsService(peopleRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                //.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/auth/register", "/error").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                                /*.anyRequest().permitAll()*/
                )
                .formLogin(formlogin -> formlogin
                        .defaultSuccessUrl("/hello")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout"));
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/images/**");
    }
/*    @Bean
    WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.DELETE).requestMatchers("/user");
    }*/
 /*   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}

