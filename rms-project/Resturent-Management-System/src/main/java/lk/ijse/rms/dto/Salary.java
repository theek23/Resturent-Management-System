package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Salary{
    private String salaryId;
    private String empId;
    private String dateTime;
    private Double amount;
    private Double OT;

}
