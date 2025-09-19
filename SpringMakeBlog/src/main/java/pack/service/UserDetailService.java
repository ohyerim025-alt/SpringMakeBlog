package pack.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pack.domain.User;
import pack.repository.UserRepository;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
   
    // 사용자 이름(email)으로 사용자의 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}

//https://github.com/shinsunyoung/springboot-developer-2rd/blob/main/chapter8/src/main/java/me/shinsunyoung/springbootdeveloper/service/UserDetailService.java