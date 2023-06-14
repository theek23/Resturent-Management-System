package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTM {
    private String empId;
    private String name;
    private String phone;
    private String type;
    private String date;
}
