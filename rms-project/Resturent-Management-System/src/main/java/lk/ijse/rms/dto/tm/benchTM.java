package lk.ijse.rms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class benchTM {
    private String benchId;
    private Integer capacity;
    private String benchStatus;
}
