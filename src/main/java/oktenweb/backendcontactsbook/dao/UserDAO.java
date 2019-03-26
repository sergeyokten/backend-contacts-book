package oktenweb.backendcontactsbook.dao;

import oktenweb.backendcontactsbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDAO extends JpaRepository<User, Integer>  {


    UserDetails findByUsername(String s);
}
