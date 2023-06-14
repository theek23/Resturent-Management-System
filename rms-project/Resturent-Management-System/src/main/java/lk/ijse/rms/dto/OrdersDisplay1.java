package lk.ijse.rms.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrdersDisplay1 {
    private String orderId;
    private List<Items> items;
    private Integer qty;
    private String time;
    private String status;
}
