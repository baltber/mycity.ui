package ru.mycity.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.auth.AuthUserRequestDto;
import ru.mycity.ui.service.rest.dto.auth.AuthUserResponseDto;
import ru.mycity.ui.service.rest.dto.auth.UserDto;

import java.util.List;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CoreService coreService;


private List<AuthUserResponseDto> list;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        AuthUserResponseDto responseDto = coreService.authUser(new AuthUserRequestDto(s));
        if (responseDto !=null){
            return toUserDetails(responseDto);
        } else {
            throw new UsernameNotFoundException("User not Found");
        }
    }

    private UserDetails toUserDetails(AuthUserResponseDto responseDto) {
        UserDto userDto = responseDto.getUserDto();
        return User.withUsername(userDto.getUserName())
                .password(userDto.getPassword())
                .roles(userDto.getRole()).build();
    }
}
