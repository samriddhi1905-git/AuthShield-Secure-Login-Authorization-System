package authsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStats {

    private long total;
    private long approved;
    private long rejected;
    private long pending;

}