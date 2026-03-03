package com.aditya.diffcheckerbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiffChunk {
    private String value;
    private String type; // SAME, ADDED, REMOVED
}