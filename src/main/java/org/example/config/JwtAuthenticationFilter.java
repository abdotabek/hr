package org.example.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.jwt.JwtDTO;
import org.example.entity.Employee;
import org.example.exception.ExceptionUtil;
import org.example.repository.EmployeeRepository;
import org.example.repository.TokenStoreRepository;
import org.example.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    TokenStoreRepository tokenStoreRepository;
    EmployeeRepository employeeRepository;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SecurityConfig.AUTH_WHITELIST)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continue the filter chain
            return;
        }
        try {
            final String token = header.substring(7).trim();

            if (!JwtUtil.isValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }

            JwtDTO jwtDTO = JwtUtil.decode(token);

            if (!"access".equals(jwtDTO.getTokenType())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Token type is not allowed for this operation");
                return;
            }
            Optional<Employee> optionalEmployee = employeeRepository.findByPhoneNumber(jwtDTO.getUserName());
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                if (GeneralStatus.BLOCK == employee.getStatus()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Employee is blocked");
                    return;
                }
            }

            String phone = jwtDTO.getUserName();
            String role = jwtDTO.getRole();
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(phone, null, List.of(authority));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response); // Continue the filter chain

        } catch (JwtException | UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed");
        }
    }
}
