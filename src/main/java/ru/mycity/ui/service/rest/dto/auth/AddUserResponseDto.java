package ru.mycity.ui.service.rest.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mycity.ui.service.rest.dto.ResultDto;

public class AddUserResponseDto {

    @JsonProperty("result")
    private ResultDto resultDto;

    public ResultDto getResultDto() {
        return resultDto;
    }

    public void setResultDto(ResultDto resultDto) {
        this.resultDto = resultDto;
    }
}
