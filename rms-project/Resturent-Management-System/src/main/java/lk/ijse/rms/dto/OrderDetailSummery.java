package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailSummery {
    private String itemDescription;
    private Integer qty;
    private Double price;
}
