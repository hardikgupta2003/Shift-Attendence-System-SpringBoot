package com.hardik.auth.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hardik.auth.service.CustomUserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService    userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ Authorization header nikaalo
        String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        // 2️⃣ Header format check karo
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer " hata diya
            username = jwtUtil.extractUsername(jwt);
        }

        // 3️⃣ Agar user pehle se authenticated nahi hai
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = userDetailsService.loadUserByUsername(username);

            // 4️⃣ Token valid hai ya nahi check karo
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // 5️⃣ Authentication object banao
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // 6️⃣ SecurityContext me set karo
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7️⃣ Request ko aage bhejo
        filterChain.doFilter(request, response);
    }
}
