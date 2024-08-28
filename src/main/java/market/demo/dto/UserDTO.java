package market.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String userCode;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private int status;
    private Long roleId;
}
