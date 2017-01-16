package com.github.apycazo.codex.spring.rest.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleEntry
{
    private Integer id;
    private String serial;
    private String name;
    private Boolean active;

}
