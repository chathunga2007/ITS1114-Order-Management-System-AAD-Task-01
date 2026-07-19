package lk.ijse.OrderManagementSystem.security;

import lk.ijse.OrderManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<lk.ijse.OrderManagementSystem.entity.User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Cannot find " + username);

        return User.builder()
                .username(optionalUser.get().getUsername())
                .password(optionalUser.get().getPassword())
                .roles(String.valueOf(optionalUser.get().getRole()))
                .build();
    }
}