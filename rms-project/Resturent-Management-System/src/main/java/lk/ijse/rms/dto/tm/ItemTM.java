package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemTM {
    private String itemId;
    private String name;
    private Double unitPrice;
    private Integer qtyOnHand;
    private String catId;
}
