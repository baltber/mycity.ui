package ru.mycity.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.AuthUserRequestDto;
import ru.mycity.ui.service.rest.dto.AuthUserResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CoreService coreService;


private List<AuthUserResponseDto> list;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        AuthUserResponseDto responseDto = coreService.authUser(new AuthUserRequestDto(s));
//        if (responseDto !=null){
//            return toUserDetails(responseDto);
//        }


        AuthUserResponseDto responseDto = new AuthUserResponseDto();
        responseDto.setUserName("admin");
        responseDto.setPassword(new BCryptPasswordEncoder().encode("admin"));
        responseDto.setRole("admin");
        list = new ArrayList<>();
        list.add(responseDto);

        Optional<AuthUserResponseDto> user = list.stream()
                .filter(u -> u.getUserName().equals(s))
                .findFirst();

        if (user.isPresent()){
            return toUserDetails(user.get());
        }

        return null;
    }

    private UserDetails toUserDetails(AuthUserResponseDto responseDto) {
        return User.withUsername(responseDto.getUserName())
                .password(responseDto.getPassword())
                .roles(responseDto.getRole()).build();
    }
}
