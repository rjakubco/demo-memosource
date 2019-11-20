package org.test.memsource.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.test.memsource.Application;
import org.test.memsource.dto.UserRegistrationDto;
import org.test.memsource.model.User;
import org.test.memsource.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testSaveUser() {
        String username = "username";
        String password = "password";
        String token = "token";
        when(tokenService.getToken(any())).thenReturn(token);
        userService.save(new UserRegistrationDto(username, password, password));

        verify(userRepository, times(1)).save(any());
        verify(tokenService, times(1)).getToken(any());

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedArgument = argumentCaptor.<User>getValue();
        assertEquals(username, capturedArgument.getUsername());
        assertEquals(token, capturedArgument.getToken());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadByUsernameWithNotExistingUser() {
        when(userRepository.findByUsername(any())).thenReturn(null);

        userService.loadUserByUsername("notExisting");
    }

    @Test
    public void testSunnyLoadByUsername() {
        String username = "username";
        String password = "password";
        when(userRepository.findByUsername(any())).thenReturn(new User(UUID.randomUUID(), username, password, password, "token"));

        UserDetails userDetails = userService.loadUserByUsername("notExisting");

        assertEquals(username, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("user")));
        assertEquals(password, userDetails.getPassword());
    }
}
