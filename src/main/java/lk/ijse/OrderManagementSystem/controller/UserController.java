package lk.ijse.OrderManagementSystem.controller;

import lk.ijse.OrderManagementSystem.constant.CommonResponse;
import lk.ijse.OrderManagementSystem.dto.UserDTO;
import lk.ijse.OrderManagementSystem.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static lk.ijse.OrderManagementSystem.constant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.OrderManagementSystem.constant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateUser(@RequestBody UserDTO userDTO) {
        UserDTO userDTOs = userService.updateUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, userDTOs, SUCCESS_MESSAGE);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getUserDetails() {
        List<UserDTO> userDTOS = userService.getUserDetails();
        return new CommonResponse(OPERATION_SUCCESS, userDTOS, SUCCESS_MESSAGE);
    }
}