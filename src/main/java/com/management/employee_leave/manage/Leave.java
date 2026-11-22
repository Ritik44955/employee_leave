package com.management.employee_leave.manage;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leaves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    @Id
    private Integer id;
    private int employeeId;

    @NotBlank(message = "From date cannot be empty")
    private String fromDate;

    @NotBlank(message = "To date cannot be empty")
    private String toDate;

    @NotBlank(message = "Reason cannot be empty")
    private String reason;
    private String status;
}