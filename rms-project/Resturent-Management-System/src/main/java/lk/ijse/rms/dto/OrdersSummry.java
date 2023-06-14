package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrdersSummry extends Orders {
    private String orderId;
    private Integer qty;
    private Double orderPrice;
    private String orderStatus;
    private String custName;
}
