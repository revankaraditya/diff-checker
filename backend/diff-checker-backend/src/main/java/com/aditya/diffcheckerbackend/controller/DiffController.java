package com.aditya.diffcheckerbackend.controller;

import com.aditya.diffcheckerbackend.model.DiffRequest;
import com.aditya.diffcheckerbackend.model.DiffResponse;
import com.aditya.diffcheckerbackend.service.DiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diff")
@CrossOrigin
@RequiredArgsConstructor
public class DiffController {

    private final DiffService diffService;

    @PostMapping
    public DiffResponse compare(@RequestBody DiffRequest request) {
        return diffService.compare(request);
    }
}