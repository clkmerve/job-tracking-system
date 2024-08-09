package com.sunny.project.business.concretes;


import com.sunny.project.dataAccess.SystemUserRepo;
import com.sunny.project.dto.LoginDTO;
import com.sunny.project.entities.SystemUser;
import com.sunny.project.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private SystemUserRepo systemUserRepo;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, SystemUserRepo systemUserRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.systemUserRepo = systemUserRepo;
    }

    public String login(LoginDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    public SystemUser getUserInfo(){
        // Şu anki kimlik doğrulama bilgilerini alın
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        SystemUser systemUser = systemUserRepo.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return systemUser;
    }

    public Long getUserId(){
        // Şu anki kimlik doğrulama bilgilerini alın
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            String username = authentication.getName();

            SystemUser user = systemUserRepo.findByUsernameOrEmail(username, username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return user.getId();
        }else{
            return 0L;
        }
    }
}