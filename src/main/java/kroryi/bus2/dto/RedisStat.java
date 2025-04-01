package kroryi.bus2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisStat {
    private Long id;
    private LocalDateTime timestamp; // LocalDateTime → String
    private Double memoryUsageMb;

}