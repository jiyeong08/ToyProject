package com.toy.controller;

import com.toy.exception.CustomException;
import com.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.toy.dto.UserDto;
import java.util.Map;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleCustomException(CustomException ex) {
        return ex.getMessage();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> postUserJoin(@RequestBody UserDto joinData) {
        log.info("[UserController] postUserJoin start");
        userService.userJoin(joinData);
        log.info("[UserController] postUserJoin succeeded");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserList(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(defaultValue = "joinDate") String sort) {
        log.info("[UserController] getUserList start");
        Map<String, Object> response = userService.userList(page, pageSize, sort);
        log.info("[UserController] getUserList succeeded");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserInfo(@PathVariable String userId,
                                                  @RequestBody UserDto infoData) {
        log.info("[UserController] updateUserInfo start");
        UserDto result = userService.userInfo(userId, infoData);
        log.info("[UserController] updateUserInfo succeeded");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
