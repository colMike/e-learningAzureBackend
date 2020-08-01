package com.michaelmbugua.expenseTrackerApi.resources;

import com.michaelmbugua.expenseTrackerApi.Constants;
import com.michaelmbugua.expenseTrackerApi.domain.User;
import com.michaelmbugua.expenseTrackerApi.services.UserService;
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
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");


        User user = userService.registerUser(firstName, lastName, email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }


    @PostMapping("/email")
    public ResponseEntity<Map<String, Boolean>> getEmail(@RequestBody Map<String, Object> userMap) {

        String email = (String) userMap.get("email");
        Boolean emailTaken = userService.checkEmail(email);

        Map<String, Boolean> map = new HashMap<>();
        map.put("available", emailTaken);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }


//    @PostMapping("/email")
//    public ResponseEntity<Map<String, Boolean>> getEmail(@RequestBody Map<String, Object> userMap) {
//
//        String email = (String) userMap.get("email");
//        Boolean emailTaken = userService.checkEmail(email);
//
//        Map<String, Boolean> map = new HashMap<>();
//        map.put("available", emailTaken);
//
//        return new ResponseEntity<>(map, HttpStatus.OK);
//
//    }


    private Map<String, Object> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        Date expiration = new Date(timestamp + Constants.TOKEN_VALIDITY);

        System.out.println(timestamp);
        System.out.println(expiration);

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(expiration)
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expiresIn", expiration);

        return map;
    }


}
