package com.example.backend.user.service.user;

import com.example.backend.authen.service.userdetail.UserPrinciple;
import com.example.backend.user.model.User;
import com.example.backend.user.payload.request.ChangePasswordForm;
import com.example.backend.user.payload.request.UserFormCreate;
import com.example.backend.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceimp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> getProfileUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = ((UserPrinciple) authentication.getPrincipal()).getId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("Người dùng không tồn tại", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordForm changePasswordForm) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String id = principal.getId();

        // Find the user in the database
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }
        User user = optionalUser.get();

        // Check if the old password is correct
        if (!passwordEncoder.matches(changePasswordForm.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng");
        }

        // Encode the new password and save it to the database
        user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Cập nhập mật khẩu thành công");
    }

    @Override
    public ResponseEntity<?> updateProfile(UserFormCreate profileForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String userId = userPrinciple.getId();

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }

        User user = optionalUser.get();
        BeanUtils.copyProperties(profileForm, user);

        userRepository.save(user);

        return ResponseEntity.ok("Cập nhật thông tin thành công");
    }

}
