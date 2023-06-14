package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalaryTM {
    private String salaryId;
    private String empId;
    private String dateTime;
    private Double amount;
    private Double OT;
}