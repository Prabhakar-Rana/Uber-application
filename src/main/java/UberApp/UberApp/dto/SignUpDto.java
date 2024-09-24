package UberApp.UberApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpDto {
    private String name;
    private String email;
    private String password;
}
