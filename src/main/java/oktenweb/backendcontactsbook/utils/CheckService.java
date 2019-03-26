package oktenweb.backendcontactsbook.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Data
public class CheckService {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;


    public UsernamePasswordAuthenticationToken retriveAuthentication(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("passwords matches");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            return usernamePasswordAuthenticationToken;
        } else {
            System.out.println("passwords does not matches");
        }
        return null;

    }


    public UsernamePasswordAuthenticationToken retriveAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails != null ? new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()) : null;

    }
}
