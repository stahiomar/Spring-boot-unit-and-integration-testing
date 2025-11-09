package com.example.test;


import com.example.test.user.User;
import com.example.test.user.UserRepository;
import com.example.test.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository; // fake repo

    @InjectMocks
    private UserService userService; // real service, fake repo injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserReturnsUser() {
        // arrange
        User fakeUser = new User(1L, "Omar");
        when(userRepository.findById(1L)).thenReturn(Optional.of(fakeUser));

        // act
        User result = userService.getUser(1L);

        // assert
        assertEquals("Omar", result.getName());
        verify(userRepository, times(1)).findById(1L); // to make sure that findById is called once
    }

    @Test
    void testGetUserThrowsWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUser(99L));
    }

    @Test
    void testSaveInvokedOnce() {
        User fakeUser = new User(1L, "Omar");

        when(userRepository.save(fakeUser)).thenReturn(fakeUser);

        userService.saveUser(fakeUser); // this triggers the repository call

        verify(userRepository, times(1)).save(fakeUser); // ✅ now this will pass
    }
}