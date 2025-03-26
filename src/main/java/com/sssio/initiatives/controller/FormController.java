package com.sssio.initiatives.controller;

import com.sssio.initiatives.request.FormResponses;
import com.sssio.initiatives.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("user/form/submit")
    public ResponseEntity<Map<String, Object>> submitResponse(@RequestBody FormResponses formResponse,
                                                              @RequestParam(value = "userDetails") String userDetails) {
        try {
            // Save the response and get the responseId
            Long responseId = formService.saveResponse(formResponse, userDetails);

            // Return the responseId in the response body
            Map<String, Object> response = new HashMap<>();
            response.put("responseId", responseId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error while submitting the response"));
        }
    }

    @PutMapping("user/form/update/{responseId}")
    public ResponseEntity<Map<String, Object>> updateResponse(@PathVariable Long responseId,
                                                              @RequestBody FormResponses formResponses,
                                                              @RequestParam(value = "userDetails") String userDetails) {
        if(responseId == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("error", "Response Id cannot be null"));
        }
        try {
            // Update the response
            formService.updateResponse(responseId, formResponses, userDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("responseId", responseId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error while updating the response"));
        }
    }

    @GetMapping("user/form/get")
    public ResponseEntity<FormResponses> getResponse(@RequestParam(value = "responseId", required = false) Long responseId) {
        if(responseId == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        try {
            FormResponses responseData = formService.getResponse(responseId);
            if (responseData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
