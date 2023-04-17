package br.com.anxybrain.user.controller;

import br.com.anxybrain.post.request.PostRequest;
import br.com.anxybrain.post.response.PostResponse;
import br.com.anxybrain.user.domain.User;
import br.com.anxybrain.user.request.LoginRequest;
import br.com.anxybrain.user.request.RefreshTokenRequest;
import br.com.anxybrain.user.request.RegisterRequest;
import br.com.anxybrain.user.request.UserProfileRequest;
import br.com.anxybrain.user.response.AuthenticationResponse;
import br.com.anxybrain.user.response.UserResponse;
import br.com.anxybrain.user.service.AuthService;
import br.com.anxybrain.user.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup/{isAHealthProfessional}")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest, @PathVariable Boolean isAHealthProfessional) {
        authService.signup(registerRequest, isAHealthProfessional);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }

    @PostMapping("/imageProfile")
    public ResponseEntity<Void> uploadImageForProfile(@RequestPart MultipartFile file) throws IOException {
        authService.uploadImageForProfile(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> profile(@RequestBody UserProfileRequest userProfileRequest) throws IOException {
        authService.profile(userProfileRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {
        return new ResponseEntity<>(authService.getProfile(), OK);
    }

    @GetMapping("/profileUserName/{userName}")
    public ResponseEntity<UserResponse> getProfileUserName(@PathVariable String userName) {
        return new ResponseEntity<>(authService.getProfileUserName(userName), OK);
    }

    @GetMapping("/profileUserNameList/{userName}")
    public ResponseEntity<List<UserResponse>> getProfileUserNameList(@PathVariable String userName) {
        return new ResponseEntity<>(authService.getProfileUserNameList(userName), OK);
    }

    @DeleteMapping("/deleteImageProfile")
    public ResponseEntity<Void> deleteImageProfile() throws IOException {
        authService.deleteImageProfile();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
