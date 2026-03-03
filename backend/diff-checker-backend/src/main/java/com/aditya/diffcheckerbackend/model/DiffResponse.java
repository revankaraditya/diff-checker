package com.aditya.diffcheckerbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DiffResponse {
    private List<DiffChunk> chunks;
}
