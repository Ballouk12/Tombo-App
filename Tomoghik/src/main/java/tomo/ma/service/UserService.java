package tomo.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tomo.ma.model.AUser;
import tomo.ma.repositrory.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<AUser> verifyConnetion(String email , String password) {
        return Optional.ofNullable(userRepository.findByEmail(email)).filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public Optional<AUser> creatUser(AUser user) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return Optional.ofNullable(userRepository.save(user));
    }
    public boolean deleteUser(long id) {
        return userRepository.findById(id).map(user -> {userRepository.deleteById(id);return true;}).orElse(false);
    }
    public AUser updateUser(AUser user) {
        return userRepository.save(user);
    }

    public AUser loadUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

}
