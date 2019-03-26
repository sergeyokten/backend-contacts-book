package oktenweb.backendcontactsbook.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import oktenweb.backendcontactsbook.models.AccountCredentials;
import oktenweb.backendcontactsbook.services.UserService;
import oktenweb.backendcontactsbook.utils.CheckService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private AccountCredentials creds;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private CheckService checkService;

    public LoginFilter(String url, AuthenticationManager authManager, UserService userService, PasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.checkService = new CheckService(userService, passwordEncoder);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        creds = new ObjectMapper()
                .readValue(httpServletRequest.getInputStream(), AccountCredentials.class);

        System.out.println(creds);


        return checkService.retriveAuthentication(creds.getUsername(), creds.getPassword());


    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        // if in prev method we was authenticate
        // we create token

        String jwtoken = Jwts.builder()
                .setSubject(auth.getName())
                .signWith(SignatureAlgorithm.HS512, "yes".getBytes())
//                .setExpiration(new Date(System.currentTimeMillis() + 2000000))
                .compact();
        //and add it to header
        res.addHeader("Authorization", "Bearer " + jwtoken);
        res.setHeader("Access-Control-Expose-Headers", "Authorization");
        System.out.println("token was setted to headers");

    }
}

