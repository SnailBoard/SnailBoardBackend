package ua.comsys.kpi.snailboard.user.facade;

import org.springframework.context.annotation.Bean;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;

public interface AuthFacade {
    void createUser(RegistrationRequest registrationRequest);
    AuthResponse authUser(AuthRequest request);
}
