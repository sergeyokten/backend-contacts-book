package oktenweb.backendcontactsbook.models;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCredentials {
    String username;
    String password;
}
