package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class deliveryTM {
    private String delId;
    private String type;
    private String delAddress;
    private String number;
    private String date;
}
