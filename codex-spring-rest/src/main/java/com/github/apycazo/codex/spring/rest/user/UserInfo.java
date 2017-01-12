package com.github.apycazo.codex.spring.rest.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo
{
    interface Summary {}

    @NotNull
    @Min(value = 0)
    @JsonView(UserInfo.Summary.class)
    private int id;

    @NotEmpty
    @Length(max = 32)
    @JsonView(UserInfo.Summary.class)

    private String username;

    private List<String> roles;

    private Long createdOn;

    private Long updatedOn;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long internalId = Long.MAX_VALUE;
}
