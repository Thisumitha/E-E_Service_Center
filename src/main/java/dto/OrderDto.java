package dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDto {
    private String orderId;
    private String date;
    private String time;
    private String cusCode;
    private List<OrderDetailsDto> list;
}