package oktenweb.backendcontactsbook.dao;

import oktenweb.backendcontactsbook.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDAO extends JpaRepository<Contact, Integer> {
}
