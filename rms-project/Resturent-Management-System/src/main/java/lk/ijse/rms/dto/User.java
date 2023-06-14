package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String userId;
    private String empId;
    private String username;
    private String password;
}
