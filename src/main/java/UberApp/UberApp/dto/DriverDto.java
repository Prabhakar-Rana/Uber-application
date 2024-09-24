package UberApp.UberApp.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DriverDto {
    private long id;
    private UserDto user;
    private double rating;
    private boolean available;
    private String vehicleId;
}
