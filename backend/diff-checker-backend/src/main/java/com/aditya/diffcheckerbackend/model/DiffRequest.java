package com.aditya.diffcheckerbackend.model;

import lombok.Data;

@Data
public class DiffRequest {
    private String text1;
    private String text2;
    private String mode; // WORD, CHARACTER, LINE
    private boolean caseSensitive;
}