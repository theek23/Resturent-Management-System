package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class delivery {
    private String delId;
    private String type;
    private String delAddress;
    private String number;
    private String date;
}
