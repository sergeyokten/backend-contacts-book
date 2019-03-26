package oktenweb.backendcontactsbook.configs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import oktenweb.backendcontactsbook.utils.CheckService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestProcessingJWTFilter extends GenericFilterBean {


    CheckService checkService;

    public RequestProcessingJWTFilter(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        this.checkService = new CheckService(userDetailsService, passwordEncoder);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        // if present
        if (token != null) {
            Claims body = Jwts.parser()
                    .setSigningKey("yes".getBytes())
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();
            String usernameFromJWT = body
                    .getSubject();
            System.out.println(body.get("password") + " password");
            System.out.println(usernameFromJWT + " user");

            //after parse of token we create Authentication object
            authentication = checkService.retriveAuthentication(usernameFromJWT);
        }
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
