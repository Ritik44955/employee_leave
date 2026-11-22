
package com.management.employee_leave.manage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Id
    private int id;

    private String mobile_no;

    @Email(message = "Please enter a valid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
