package oktenweb.backendcontactsbook.services;

import oktenweb.backendcontactsbook.models.Contact;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageTransferService {

    public void transferImage(MultipartFile multipartFile, Contact contact) {

        String imageName = "avatar" + contact.getSurname() + multipartFile.getOriginalFilename();
        System.out.println(multipartFile.getContentType());
        System.out.println(imageName);
        try {

            String path = System.getProperty("user.home") + File.separator + "images" + File.separator;
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            multipartFile.transferTo(new File(path + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
