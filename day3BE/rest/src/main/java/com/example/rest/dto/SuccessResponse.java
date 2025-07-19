package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    private String message;
    private String status;
    private LocalDateTime timestamp;
    private T data;

    // Constructor for simple success without data
    public SuccessResponse(String message) {
        this.message = message;
        this.status = "success";
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for success with data
    public SuccessResponse(String message, T data) {
        this.message = message;
        this.status = "success";
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}