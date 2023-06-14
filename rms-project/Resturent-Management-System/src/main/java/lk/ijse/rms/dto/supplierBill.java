package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class supplierBill {
    private String billId;
    private String itemId;
    private String supId;
    private String date;
    private String paymentStatus;
    private Double price;
}
