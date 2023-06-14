package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailSummeryTM {
    private String itemDescription;
    private Integer qty;
    private Double price;
}