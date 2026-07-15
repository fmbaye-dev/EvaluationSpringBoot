package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services.JwtService;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;


     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          String header =
                  request.getHeader("Authorization");


          if(header == null || !header.startsWith("Bearer ")){

               filterChain.doFilter(request,response);
               return;
          }


          String token = header.substring(7);


          String email = jwtService.extraireEmail(token);


          UsernamePasswordAuthenticationToken auth =
                  new UsernamePasswordAuthenticationToken(
                          email,
                          null,
                          new ArrayList<>()
                  );


          SecurityContextHolder
                  .getContext()
                  .setAuthentication(auth);


          filterChain.doFilter(request,response);

     }
}
