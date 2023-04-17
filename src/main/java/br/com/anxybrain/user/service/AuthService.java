package br.com.anxybrain.user.service;

import br.com.anxybrain.exception.BusinessException;
import br.com.anxybrain.exception.SecurityException;
import br.com.anxybrain.file.repository.FileRepository;
import br.com.anxybrain.user.domain.NotificationEmail;
import br.com.anxybrain.user.domain.User;
import br.com.anxybrain.user.domain.VerificationToken;
import br.com.anxybrain.user.repository.UserRepository;
import br.com.anxybrain.user.repository.VerificationTokenRepository;
import br.com.anxybrain.user.request.LoginRequest;
import br.com.anxybrain.user.request.RefreshTokenRequest;
import br.com.anxybrain.user.request.RegisterRequest;
import br.com.anxybrain.user.request.UserProfileRequest;
import br.com.anxybrain.user.response.AuthenticationResponse;
import br.com.anxybrain.user.response.UserResponse;
import br.com.anxybrain.user.service.security.JWTProvider;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.Tags;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final MongoTemplate mongoTemplate;

    public void signup(RegisterRequest registerRequest, Boolean isAHealthProfessional) {

        Optional<User> findByUser = userRepository.findByUserName(registerRequest.getUserName());

        validationUserName(findByUser);

        if(isAHealthProfessional){
            User userProfessional = User.toRegisterProfessionalRequest(registerRequest);
            userProfessional.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userProfessional.setEnabled(false);
            userProfessional.setCreated(Instant.now());
            userProfessional.setIsAHealthProfessional(isAHealthProfessional);

            userRepository.save(userProfessional);

            String token = generateVerificationToken(userProfessional);
            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    userProfessional.getEmail(), "Thank you for signing up to Anxy brain app, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token));
        }else {

            User user = User.toRegisterRequest(registerRequest);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEnabled(false);
            user.setCreated(Instant.now());
            user.setIsAHealthProfessional(isAHealthProfessional);

            userRepository.save(user);

            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    user.getEmail(), "Thank you for signing up to Anxy brain app, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token));
        }
    }


    private void validationUserName(Optional<User> findByUser) {
        if (!findByUser.isEmpty()) {
            throw new SecurityException("The user name already exists, try another one");
        }
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String userName = verificationToken.getUser().getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SecurityException("User not found with name - " + userName));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SecurityException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(loginRequest.getUserName())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUserName());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(refreshTokenRequest.getUserName())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public void uploadImageForProfile(MultipartFile file) throws IOException {

        User currentUser = getCurrentUser();

        if(Objects.nonNull(currentUser.getFile())){
            File convertFile = new File(currentUser.getFile().getPath());
            convertFile.delete();
            fileRepository.delete(currentUser.getFile());
        }

        if(!file.isEmpty()) {
            currentUser.setFile(fileRepository.save(createImageProfile(file)));
        }

        userRepository.save(currentUser);
    }

    public br.com.anxybrain.file.model.File createImageProfile(MultipartFile file) throws IOException {

        String path = "src/main/resources/files/profiles"
                + "/" + getCurrentUser().getUserName()
                + "/" + UUID.randomUUID()
                + "-" + file.getOriginalFilename();

        File convertFile = new File(path);

        if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdir();
        }

        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
            return br.com.anxybrain.file.model.File.toFile(path);
        } catch (Exception exe) {
            exe.printStackTrace();
        }

        return null;
    }

    public void profile(UserProfileRequest userProfileRequest) {

        User currentUser = getCurrentUser();
        currentUser.setBio(userProfileRequest.getBio());
        currentUser.setUrl(userProfileRequest.getUrl());

        userRepository.save(currentUser);
    }

    public void deleteImageProfile() {

        User currentUser = getCurrentUser();

        br.com.anxybrain.file.model.File fileById = fileRepository.findById(currentUser.getFile().getId()).orElseThrow(() -> new BusinessException("File not found"));

        File convertFile = new File(currentUser.getFile().getPath());
        convertFile.delete();

        fileRepository.delete(fileById);
    }

    public UserResponse getProfile() {

        User currentUser = getCurrentUser();

        User user = userRepository.findByUserName(currentUser.getUserName()).orElseThrow(() -> new SecurityException("User not found"));

        return UserResponse.toUserResponse(user);
    }

    public UserResponse getProfileUserName(String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SecurityException("User not found"));

        return UserResponse.toUserResponse(user);

    }

    public List<UserResponse> getProfileUserNameList(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").regex(".*" +userName+ ".*"));
        query.addCriteria(Criteria.where("isAHealthProfessional").in(false));
        query.addCriteria(Criteria.where("enabled").in(true));
        List<User> users = mongoTemplate.find(query, User.class);

        return users.stream().map(UserResponse::toUserResponse).collect(Collectors.toList());

    }
}

