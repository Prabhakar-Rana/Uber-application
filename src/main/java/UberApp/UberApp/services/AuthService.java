package UberApp.UberApp.services;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.SignUpDto;
import UberApp.UberApp.dto.UserDto;

public interface AuthService {
    String[] login(String email, String password);
    UserDto signUp(SignUpDto signupDto);
    DriverDto onboardNewDriver(Long userId, String vehicleId);

}
