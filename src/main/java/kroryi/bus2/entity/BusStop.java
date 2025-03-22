package kroryi.bus2.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_stop")
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bs_id", unique = true)
    private String bsId;

    @Column(name = "bs_nm")
    private String bsNm;

    @Column(name = "x_pos")
    private Double xPos;

    @Column(name = "y_pos")
    private Double yPos;

}