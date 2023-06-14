package lk.ijse.rms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class bench {
    private String benchId;
    private Integer capacity;
    private String benchStatus;
}
