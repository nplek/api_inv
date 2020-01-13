package com.rookycode.api_inv.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO<T> {
    private String status;

    @Builder.Default
    private String message = "Success!";
    private List<String> details;
    private T body;
}