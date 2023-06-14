package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String empId;
    private String name;
    private String phone;
    private String type;
    private String date;
}
