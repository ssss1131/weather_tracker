package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.config.TestConfig;
import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.exception.AuthenticationException;
import com.ssss.weather_tracker.exception.UserAlreadyExistsException;
import com.ssss.weather_tracker.model.Session;
import com.ssss.weather_tracker.repository.SessionRepository;
import com.ssss.weather_tracker.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"session.cookie.max-age = 20"})
@ContextConfiguration(classes = {TestConfig.class, AuthenticationService.class, UserRepository.class, SessionService.class, SessionRepository.class})
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SessionService sessionService;

    private static UserLoginDto userLoginDto;
    private static UserRegistrationDto userRegistrationDto;

    @BeforeAll
    static void setUp() {
        userLoginDto = new UserLoginDto("test", "test");
        userRegistrationDto = new UserRegistrationDto("test", "test", "test");

    }

    @AfterEach
    void clear() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM User").executeUpdate();
            session.createMutationQuery("DELETE FROM Session").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void register_NewUser_CreateNewSession() {
        UUID uuid = authenticationService.register(userRegistrationDto);
        Optional<Session> session = sessionService.getSessionWithUser(String.valueOf(uuid));
        assertTrue(session.isPresent());
        assertEquals(session.get().getUser().getLogin(), userRegistrationDto.getLogin());
    }

    @Test
    void register_ExistingUser_ThrowException() {
        authenticationService.register(userRegistrationDto);
        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(userRegistrationDto));
    }


    @Test
    void authenticate_ExistingUser_CanLogin() {
        authenticationService.register(userRegistrationDto);
        assertDoesNotThrow(() -> authenticationService.authenticate(userLoginDto));
    }

    @Test
    void authenticate_ExistingUser_ShouldRefreshSession() {
        UUID uuid = authenticationService.register(userRegistrationDto);
        Session sessionBeforeLogin = sessionService.getSessionWithUser(String.valueOf(uuid)).get();
        UUID optionalUUID = authenticationService.authenticate(userLoginDto);
        Session sessionAfterLogin = sessionService.getSessionWithUser(optionalUUID.toString()).get();
        assertTrue(sessionBeforeLogin.getExpiresAt().isBefore(sessionAfterLogin.getExpiresAt()));
    }

    @Test
    void authenticate_WrongPassword_CantLogin() {
        authenticationService.register(userRegistrationDto);
        UserLoginDto userLoginDto1 = new UserLoginDto("test", "wrong pass");
        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(userLoginDto1));
    }

    @Test
    void authenticate_NotExistingUser_CantLogin() {
        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(userLoginDto));
    }


}