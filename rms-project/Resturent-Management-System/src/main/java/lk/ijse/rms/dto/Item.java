package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private String itemId;
    private String name;
    private Double unitPrice;
    private Integer qtyOnHand;
    private String catId;
}
