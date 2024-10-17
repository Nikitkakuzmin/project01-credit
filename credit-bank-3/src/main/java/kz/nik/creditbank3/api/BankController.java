package kz.nik.creditbank3.api;

import kz.nik.creditbank3.dto.CreditInfoDto;
import kz.nik.creditbank3.dto.UserCreateDto;
import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bank")
public class BankController {

    private final UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserCreateDto userCreateDto) {
        userService.addUser(userCreateDto);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getUserByEmail(@RequestParam String email) {
        UserInfoDto userInfoDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/credit-info")
    public ResponseEntity<CreditInfoDto> getCreditInfo(@RequestParam String username) {
        CreditInfoDto creditInfoDto = userService.getCreditInfo(username);
        return ResponseEntity.ok(creditInfoDto);
    }

}
