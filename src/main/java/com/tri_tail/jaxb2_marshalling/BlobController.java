package com.tri_tail.jaxb2_marshalling;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class BlobController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleUpload(@RequestBody BlobRequest request) {
        return new ResponseEntity<>("Data received successfully", HttpStatus.OK);
    }
}