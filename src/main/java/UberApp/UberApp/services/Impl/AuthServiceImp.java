package UberApp.UberApp.services.Impl;

import UberApp.UberApp.dto.DriverDto;
import UberApp.UberApp.dto.SignUpDto;
import UberApp.UberApp.dto.UserDto;
import UberApp.UberApp.entities.Driver;
import UberApp.UberApp.entities.User;
import UberApp.UberApp.entities.Wallet;
import UberApp.UberApp.entities.enums.Role;
import UberApp.UberApp.repositories.UserRepository;
import UberApp.UberApp.security.JWTService;
import UberApp.UberApp.services.AuthService;
import UberApp.UberApp.services.DriverService;
import UberApp.UberApp.services.RiderService;
import UberApp.UberApp.services.WalletService;
import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        //String tokens[] = new String[2];
        try{
            System.out.println("email="+email + password);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            System.out.println("authentication="+authentication);
            User user = (User) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            System.out.println("accessToken = "+accessToken);
            return new String[]{accessToken, refreshToken};
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or password !");
        }

    }

    @Override
    public UserDto signUp(SignUpDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(user != null){
            throw new RuntimeException("User is already signup"+ signupDto.getEmail());
        }
        User mappeduser = modelMapper.map(signupDto, User.class);
        mappeduser.setRoles(Set.of(Role.Rider));
       // System.out.println("mapped-user="+mappeduser);
        mappeduser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User savedUser = userRepository.save(mappeduser);
        riderService.createNewRider(savedUser);

        //WALLET Related service
        walletService.createNewWallet(savedUser);
        System.out.println("mapped-value="+savedUser);
        System.out.println("mapped-val="+modelMapper.map(savedUser, UserDto.class));
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User is not present in DB with id :" + userId));
        if(user.getRoles().contains(Role.Driver))
            throw new IllegalArgumentException("Rider is already driver !");
        Driver driver = Driver.builder()
                .user(user)
                .rating(0.0)
                .available(true)
                .vehicleId(vehicleId)
                .build();
        user.getRoles().add(Role.Driver);
        userRepository.save(user);
        Driver saveDriver = driverService.createNewDriver(driver);
        return modelMapper.map(saveDriver, DriverDto.class);
    }
}
