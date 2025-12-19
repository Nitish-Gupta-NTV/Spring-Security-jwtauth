package com.SpringSecurity.demo.Filter;

import com.SpringSecurity.demo.service.JWTService;
import com.SpringSecurity.demo.service.MyUserDeatileService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("reach the dofilterinternal");
        String authFilter=request.getHeader("Authorization");
        // it means reuest contain the token
        String username=null;
        String token=null;
        if(authFilter!=null&&authFilter.startsWith("Bearer "))
        {
            token=authFilter.substring(7);
            username=jwtService.extractUsernamefromToken(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=context.getBean(MyUserDeatileService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken auttoken=new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                auttoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auttoken);

            }

        }
        filterChain.doFilter(request,response);
    }
}
