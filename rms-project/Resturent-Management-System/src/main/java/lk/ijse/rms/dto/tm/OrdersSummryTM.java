package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrdersSummryTM extends OrdersTM {
    private String orderId;
    private Integer qty;
    private Double orderPrice;
    private String orderStatus;
    private String custName;
}
