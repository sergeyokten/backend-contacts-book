package oktenweb.backendcontactsbook.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import oktenweb.backendcontactsbook.models.Contact;
import oktenweb.backendcontactsbook.services.ImageTransferService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUD_Controller {

    ImageTransferService transferService;

    @PostMapping("/saveContact")
    public String saveContact(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String number,
                              @RequestParam MultipartFile image) {

        Contact contact = new Contact();
        contact.setName(name);
        contact.setSurname(surname);

        if (image != null) {
            transferService.transferImage(image, contact);
        }
        return "saved";
    }


//    @GetMapping("/getAllContacts")
//    public byte[] getAllContacts() throws IOException {
//
//        File folder = new File(System.getProperty("user.home") + File.separator + "images" + File.separator);
//        File in = folder.listFiles()[0];
//        FileInputStream fileInputStream = new FileInputStream(in);
//
//        return IOUtils.toByteArray(fileInputStream);
//    }

    @GetMapping("/getAllContacts")
    public byte[] getAllContacts() throws IOException {

        File folder = new File(System.getProperty("user.home") + File.separator + "images" + File.separator);
        File in = folder.listFiles()[0];
        FileInputStream fileInputStream = new FileInputStream(in);

        return IOUtils.toByteArray(fileInputStream);
    }

}
