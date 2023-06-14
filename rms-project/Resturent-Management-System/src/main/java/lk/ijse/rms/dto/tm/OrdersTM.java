package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrdersTM {
    private String orderId;
    private String custName;
    private String custPhone;
    private String orderStatus;
    private Double orderPrice;
    private String empId;
    private Integer qty;
    private String benchId;
    private String takeawayId;
    private String delId;
    private String time;
}
