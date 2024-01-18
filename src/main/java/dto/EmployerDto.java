package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class EmployerDto {
    private String code;
    private String name;
    private Integer number;
    private String email;
    private AccessDto access;
    private String password;
}
