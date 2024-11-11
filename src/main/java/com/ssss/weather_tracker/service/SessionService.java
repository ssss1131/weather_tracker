package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.model.Session;
import com.ssss.weather_tracker.model.User;
import com.ssss.weather_tracker.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public UUID createSession(User user, int sessionMaxAge) {
        Session session = Session.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(sessionMaxAge))
                .build();
        sessionRepository.save(session);

        return session.getId();
    }


    public void refreshSession(UUID sessionId, int sessionMaxAge) {
        sessionRepository.updateTime(sessionId, LocalDateTime.now().plusSeconds(sessionMaxAge));
    }

    public UUID refreshSession(User user, int sessionMaxAge){
        Session session = sessionRepository.findByUser(user);
        refreshSession(session.getId(), sessionMaxAge);
        return session.getId();
    }

    public Optional<Session> getSessionWithUser(String id) {
       return Optional.ofNullable(sessionRepository.findById(UUID.fromString(id)));
    }

    public boolean isNearExpiration(Session session) {
        LocalDateTime now = LocalDateTime.now();
        return session.getExpiresAt().isBefore(now.plusMinutes(10));
    }
}
