package com.fachter.backend.config.auth;

import com.fachter.backend.services.auth.DefaultUserDetailsService;
import com.fachter.backend.utils.JsonWebTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    private final DefaultUserDetailsService userDetailsService;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    public JwtRequestFilter(DefaultUserDetailsService userDetailsService, JsonWebTokenUtil jsonWebTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        setAuthenticationContextFromJwt(request);
        chain.doFilter(request, response);
    }

    private void setAuthenticationContextFromJwt(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            return;

        String jwt = authorizationHeader.substring(7);
        String username = jsonWebTokenUtil.extractUsername(jwt);

        if (username == null)
            return;

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!jsonWebTokenUtil.validateToken(jwt, userDetails))
            return;


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
