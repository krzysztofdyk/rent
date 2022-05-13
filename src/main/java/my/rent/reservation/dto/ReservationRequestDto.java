package my.rent.reservation.dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequestDto {
    @NonNull
    private String houseName;

    @NonNull
    private String tenantName;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;
}
