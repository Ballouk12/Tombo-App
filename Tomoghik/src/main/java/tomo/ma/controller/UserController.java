package tomo.ma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tomo.ma.config.JwtUtil;
import tomo.ma.dto.LoginRequestDTO;
import tomo.ma.model.AUser;
import tomo.ma.service.UserService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("signup")
    public AUser signUp(@RequestBody AUser user) {
      return userService.creatUser(user).orElse(null) ;
    }

    /*@PostMapping("signin")
    public AUser login(@RequestBody LoginRequestDTO user) {
        return userService.verifyConnetion(user.getEmail() ,user.getPassword()).orElse(null) ;
    }*/

    @PostMapping("signin")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO user) {
        Optional<AUser> authUser = userService.verifyConnetion(user.getEmail(), user.getPassword());
        if (authUser.isPresent()) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok().body(Map.of("token", token ,"user" , authUser.orElse(null)   ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}
