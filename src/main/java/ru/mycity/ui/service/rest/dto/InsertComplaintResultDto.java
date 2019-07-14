package ru.mycity.ui.service.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertComplaintResultDto {
    private ResultDto result;
    @JsonProperty(value = "complaint_id")
    private long complaintId;

    public ResultDto getResult() {
        return result;
    }

    public void setResult(ResultDto result) {
        this.result = result;
    }

    public long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(long complaintId) {
        this.complaintId = complaintId;
    }
}
