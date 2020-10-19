package com.test.eip.eipexample.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Test {
    private List<PersonDTO> results;
}
