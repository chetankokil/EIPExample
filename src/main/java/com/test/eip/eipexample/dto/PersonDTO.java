package com.test.eip.eipexample.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonDTO {

    String gender;
    String email;
    String phone;
    Name name;
}
