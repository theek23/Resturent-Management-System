package lk.ijse.rms.dto.tm;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rms.dto.Items;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrdersSummry2TM {
    private String orderId;
    private Double price;
    private String time;
    private String tableId;
    private String takeawayId;
    private String status;
}