package UberApp.UberApp.controllers;

import UberApp.UberApp.dto.*;
import UberApp.UberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signUp")
    ResponseEntity<UserDto> signUp(@RequestBody  SignUpDto signUpDto){
        System.out.println("signUp-DTO = "+ signUpDto);
       return new ResponseEntity<>(authService.signUp(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest,
                                           HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("loginreq = "+loginRequest);
        String tokens[] = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        Cookie cookie = new Cookie("token", tokens[1]);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        System.out.println("loginres = "+tokens[0]);
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    @Secured("ROLE_Admin")
    @PostMapping("/onBoard/{riderId}")
    DriverDto onboardNewDriver(@PathVariable Long riderId, @RequestBody OnboardDriverDto onboardDriverDto){
        return authService.onboardNewDriver(riderId, onboardDriverDto.getVehicleId());
    }
}
