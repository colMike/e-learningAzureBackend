package com.michaelmbugua.expenseTrackerApi.resources;

import com.michaelmbugua.expenseTrackerApi.Constants;
import com.michaelmbugua.expenseTrackerApi.domain.Parent;
import com.michaelmbugua.expenseTrackerApi.services.ParentService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/parents")
public class ParentResource {

    @Autowired
    ParentService parentService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerParent(@RequestBody Map<String, Object> parentMap) {
        String firstName = (String) parentMap.get("firstName");
        String lastName = (String) parentMap.get("lastName");
        String email = (String) parentMap.get("email");
        String password = (String) parentMap.get("password");


        Parent parent = parentService.registerParent(firstName, lastName, email, password);

        return new ResponseEntity<>(generateJWTToken(parent), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginParent(@RequestBody Map<String, Object> parentMap) {
        String email = (String) parentMap.get("email");
        String password = (String) parentMap.get("password");

        Parent parent = parentService.validateParent(email, password);

        return new ResponseEntity<>(generateJWTToken(parent), HttpStatus.OK);
    }


    @PostMapping("/email")
    public ResponseEntity<Map<String, Boolean>> getEmail(@RequestBody Map<String, Object> parentMap) {

        String email = (String) parentMap.get("email");
        Boolean emailTaken = parentService.checkEmail(email);

        Map<String, Boolean> map = new HashMap<>();
        map.put("available", emailTaken);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }


//    @PostMapping("/email")
//    public ResponseEntity<Map<String, Boolean>> getEmail(@RequestBody Map<String, Object> parentMap) {
//
//        String email = (String) parentMap.get("email");
//        Boolean emailTaken = parentService.checkEmail(email);
//
//        Map<String, Boolean> map = new HashMap<>();
//        map.put("available", emailTaken);
//
//        return new ResponseEntity<>(map, HttpStatus.OK);
//
//    }


    private Map<String, Object> generateJWTToken(Parent parent) {
        long timestamp = System.currentTimeMillis();
        Date expiration = new Date(timestamp + Constants.TOKEN_VALIDITY);

        System.out.println(timestamp);
        System.out.println(expiration);

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(expiration)
                .claim("parentId", parent.getParentId())
                .claim("email", parent.getEmail())
                .claim("firstName", parent.getFirstName())
                .claim("lastName", parent.getLastName())
                .compact();

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expiresIn", expiration);

        return map;
    }


}
