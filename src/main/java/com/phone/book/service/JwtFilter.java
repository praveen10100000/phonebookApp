package com.phone.book.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private Service service ;

    @Autowired
    private JwtUtil jwtUtil ; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("token");

        String name = null;
        String jwt = null ;
        System.out.println(authorizationHeader);
        if(authorizationHeader != null)
        {
            try {
                jwt = authorizationHeader;
                name = jwtUtil.extractEmail(jwt) ;
                System.out.println(name);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            
        }
        
        if(name != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            System.out.println(name);
            UserDetails userDetails = this.service.loadUserByUsername(name) ;
            if(jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()) ;
                usernamePasswordAuthenticationToken
                   .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request , response) ;
    }
    
}
