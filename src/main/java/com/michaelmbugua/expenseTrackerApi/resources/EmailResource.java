package com.michaelmbugua.expenseTrackerApi.resources;

import com.michaelmbugua.expenseTrackerApi.Constants;
import com.michaelmbugua.expenseTrackerApi.domain.User;
import com.michaelmbugua.expenseTrackerApi.exceptions.EtBadRequestException;
import com.michaelmbugua.expenseTrackerApi.services.UserService;
import com.sendgrid.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/teachers/background")
public class EmailResource {

    @PostMapping("/sendDetailsAsEmail")
    public ResponseEntity<Map<String, Response>> sendDocsForCheck(@RequestBody Map<String, Object> emailMap){


        String name = emailMap.get("firstName") + " " + emailMap.get("lastName");
        String phoneNumber = emailMap.get("phoneNumber") + " ";


        //  Set up Email
        Email to = new Email("michaelngigi76@gmail.com");
        String subject = "Teachers' Documents for Conducting Background Checks";
        Email from = new Email("mikewriter76@gmail.com");

        Attachments attachment = new Attachments();

        long millis = System.currentTimeMillis();

        java.util.Date date=new java.util.Date(millis);

        Content content = new Content("text/plain" , "Documents for " + name + ", with Phone Number: " + phoneNumber + " for Background Check. Submitted on " + date);
        Mail mail = new Mail(from, subject, to, content);



        emailMap.forEach((k, v)-> {

            if (v.getClass().getSimpleName().equals("LinkedHashMap")) {

                Map<String, Object> kMap;
                kMap = (Map<String, Object>) emailMap.get(k);

                String kFile = (String) kMap.get("value");
                String kFileName = (String) kMap.get("filename");
                String kType = (String) kMap.get("filetype");


                // Different Document Attachments

//                String originalInput = kFile;
//                byte[] result = Base64.getDecoder().decode(originalInput);

//                System.out.println(Arrays.toString(result));

                attachment.setContent(kFile);
                System.out.println(kFile);
                attachment.setFilename(kFileName);
                System.out.println(kFileName);
                attachment.setType(kType);
                System.out.println(kType);

                mail.addAttachments(attachment);
            }
//            System.out.println(k + ":" + v);
//            System.out.println(k + ":" + v.getClass().getSimpleName());
                }
        );



//        Map<String, String> map = new HashMap<>();
//        map.put("message", "response");
//
//        return new ResponseEntity<>(map, HttpStatus.OK);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

            Map<String, Response> map = new HashMap<>();
            map.put("message", response);

            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (IOException ex) {
            throw new EtBadRequestException("Imekataa kusend Email" + ex);
        }



    }


}
