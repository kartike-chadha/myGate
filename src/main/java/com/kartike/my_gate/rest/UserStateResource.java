package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.UserStateDTO;
import com.kartike.my_gate.service.UserStateService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/userStates", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserStateResource {

    private final UserStateService userStateService;

    public UserStateResource(final UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @GetMapping
    public ResponseEntity<List<UserStateDTO>> getAllUserStates() {
        return ResponseEntity.ok(userStateService.findAll());
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<UserStateDTO> getUserState(
            @PathVariable(name = "stateId") final Integer stateId) {
        return ResponseEntity.ok(userStateService.get(stateId));
    }

    @PostMapping
    public ResponseEntity<Integer> createUserState(
            @RequestBody @Valid final UserStateDTO userStateDTO) {
        final Integer createdStateId = userStateService.create(userStateDTO);
        return new ResponseEntity<>(createdStateId, HttpStatus.CREATED);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<Integer> updateUserState(
            @PathVariable(name = "stateId") final Integer stateId,
            @RequestBody @Valid final UserStateDTO userStateDTO) {
        userStateService.update(stateId, userStateDTO);
        return ResponseEntity.ok(stateId);
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<Void> deleteUserState(
            @PathVariable(name = "stateId") final Integer stateId) {
        userStateService.delete(stateId);
        return ResponseEntity.noContent().build();
    }

}
