package com.SpringSecurity.demo.Congig;
import com.SpringSecurity.demo.Filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        System.out.println("reach the securityfilterchain");
       return http.csrf(AbstractHttpConfigurer::disable).
        authorizeHttpRequests(request->request
                .requestMatchers("/api/register/users","/api/login-test")
                .permitAll()
                .anyRequest()
                .authenticated())
               // .formLogin(Customizer.withDefaults())
               // .httpBasic(Customizer.withDefaults())
        .sessionManagement(
                session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user1=User.withDefaultPasswordEncoder()
//                .username("yash")
//                .password("y@12345")
//                .roles("User")
//                .build();
//        UserDetails user2=User.withDefaultPasswordEncoder()
//                .username("Ram")
//                .password("r@12345")
//                .roles("Admin")
//                .build();
//    return new InMemoryUserDetailsManager(user1,user2);
//    }
    // this is the loophole for the hardcode user


    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(8));
        System.out.println("reach the authentication provider");
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
       return config.getAuthenticationManager();

    }
}
