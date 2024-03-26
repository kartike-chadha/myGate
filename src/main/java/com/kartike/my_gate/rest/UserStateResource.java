package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.UserStateDTO;
import com.kartike.my_gate.service.UserStateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/userStates", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserStateResource {

    private final UserStateServiceImpl userStateService;

    public UserStateResource(final UserStateServiceImpl userStateService) {
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
