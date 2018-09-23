package ru.voprostion.app.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class BaseDto {
    protected Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.util.Date dateCreated;
}
