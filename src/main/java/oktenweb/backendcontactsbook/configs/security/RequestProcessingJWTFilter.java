package oktenweb.backendcontactsbook.configs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class RequestProcessingJWTFilter extends GenericFilterBean {

    // react on every url (but we can change it if implement another filter)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;
//

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // and check presents of token in header Authorization
        String token = httpServletRequest.getHeader("Authorization");
        // if present
        if (token != null) {
            System.out.println("!!!!!!!!!!!!!!!!");
            // parse it and retrive body subject from
            Claims body = Jwts.parser()
                    .setSigningKey("yes".getBytes())
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();
            String user = body
                    .getSubject();
            System.out.println(body.get("password"));
            System.out.println(user);

            //after parse of token we create Authentication object
            authentication = new UsernamePasswordAuthenticationToken(user, "asd", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        // and set it to global security context
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request, response);
    }
}
