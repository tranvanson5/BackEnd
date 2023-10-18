package com.example.backend.authen.service.auth;


import com.example.backend.authen.constain.RoleName;
import com.example.backend.authen.model.Role;
import com.example.backend.authen.payload.request.RestPassword;
import com.example.backend.authen.payload.request.SignInForm;
import com.example.backend.authen.payload.request.SignUpForm;
import com.example.backend.authen.payload.response.JwtReponse;
import com.example.backend.authen.repository.RoleRepository;
import com.example.backend.authen.service.jwt.JwtProvider;
import com.example.backend.authen.service.userdetail.UserPrinciple;
import com.example.backend.user.constain.UserStatus;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.utils.generateOtp.GenerateOtp;
import com.example.backend.utils.sendMail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthSerivceImp implements AuthService{
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleReposetory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenerateOtp generateOtp;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HttpSession session;
    @Autowired
    private JwtProvider tokenProvider;
    @Override
    public ResponseEntity<?> SignIn(SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtReponse(jwt, userDetails.getId(),userDetails.getName(),userDetails.getUsername(),userDetails.getEmail(),userDetails.getAuthorities()));
    }

    @Override
    public ResponseEntity<?> SignUp(SignUpForm signUpForm) {
        if(!userRepository.existsByEmail(signUpForm.getEmail())&&!userRepository.existsByUsername(signUpForm.getUsername())){
            User user= new User();
            user.setName(signUpForm.getName());
            user.setUsername(signUpForm.getUsername());
            user.setEmail(signUpForm.getEmail());
            user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
            Set<Role> roleSet= new HashSet<>();
            Role userRole = roleReposetory.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roleSet.add(userRole);
            user.setRoles(roleSet);
            user.setCreateAt(LocalDateTime.now());
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            return new ResponseEntity<>("create thành công",HttpStatus.OK);
        }
        return new ResponseEntity<>("Request không hợp lệ",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> generateOtpForgotPassword(String email) {
        if (!userRepository.existsByEmail(email)){
            return new ResponseEntity<>("email không tồn tại trong hệ thống",HttpStatus.BAD_REQUEST);
        }
        session.setAttribute(email,generateOtp.generateOtp());
        emailService.sendEmail(email,"Quên mật khẩu","Otp của bạn là: " + session.getAttribute(email));
        return new ResponseEntity<>("Tạo otp thành công",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> veritifyOtpForgotPassword(String email, String otp) {
        String storedOtp = (String) session.getAttribute(email); // Lấy OTP từ session
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return new ResponseEntity<>("OTP không chính xác", HttpStatus.BAD_REQUEST);
        }
//        session.removeAttribute(email); // Xóa OTP khỏi session
        User user= userRepository.getByEmail(email);
        String jwt = jwtProvider.generatePasswordResetToken(user.getUsername());
        session.setAttribute(jwt,true);
        return new ResponseEntity<>(new JwtReponse(jwt, user.getId(),user.getName(),user.getUsername(),user.getEmail(),null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(RestPassword password, HttpServletRequest request) {
        String jwt = null;

        // Lấy JWT từ Header "Authorization"
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Bỏ "Bearer " để lấy JWT
        }
//        Boolean sessionValue = (Boolean) session.getAttribute(jwt);
//        if (sessionValue == null || sessionValue == false) {
//            return new ResponseEntity<>("Jwt đã sử dụng",HttpStatus.BAD_REQUEST);
//        }
        session.setAttribute(jwt,false);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user= userRepository.findByUsername(username).get();
        boolean matches = passwordEncoder.matches(password.getPassword(), user.getPassword());
        if(matches==false){
            return new ResponseEntity<>("Mật khẩu không khớp", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(password.getNewPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Đổi mật khẩu thành công", HttpStatus.OK);
    }
}
