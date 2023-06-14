package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Orders {
    private String orderId;
    private String custName;
    private String custPhone;
    private String orderStatus;
    private Double orderPrice;
    private Integer qty;
    private String empId;
    private String benchId;
    private String takeawayId;
    private String delId;
    private String time;
}
