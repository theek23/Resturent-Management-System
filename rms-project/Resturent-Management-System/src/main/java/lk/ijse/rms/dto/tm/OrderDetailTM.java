package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailTM {
    private String orderId;
    private String itemId;
    private Integer qty;
    private String date;
    private Double unitPrice;
}
