package kz.nik.credituserservice.api;

import jakarta.servlet.http.HttpServletRequest;
import kz.nik.credituserservice.client.Bank1FeignClient;


import kz.nik.credituserservice.client.Bank2FeignClient;
import kz.nik.credituserservice.client.Bank3FeignClient;
import kz.nik.credituserservice.client.CreditFeignClient;
import kz.nik.credituserservice.dto.*;
import kz.nik.credituserservice.model.Role;
import kz.nik.credituserservice.service.UserService;
import kz.nik.credituserservice.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private  final Bank1FeignClient bank1FeignClient;
    private final Bank2FeignClient bank2FeignClient;
    private final Bank3FeignClient bank3FeignClient;
    private final CreditFeignClient creditFeignClient;



    @PostMapping(value = "/bank/add")
    public void  addUser(@RequestBody UserCreateDto userCreateDto){
        BankUserCreateDto bankUserCreateDto = BankUserCreateDto.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .username(userCreateDto.getUsername())
                .password(userCreateDto.getPassword())
                .build();
        bank1FeignClient.addUser(bankUserCreateDto);
        bank2FeignClient.addUser(bankUserCreateDto);
        bank3FeignClient.addUser(bankUserCreateDto);



        userService.addUser(userCreateDto);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token) {

        UserDto currentUser = userService.getCurrentUser(token);

        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserSignInDto userSignInDto) {

        return new ResponseEntity<>(userService.signIn(userSignInDto), HttpStatus.OK);
    }

    @PostMapping(value = "/update-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody UserDto userDto) {
        try {
            userService.changePassword(userDto.getEmail(), userDto.getPassword());
            return ResponseEntity.ok("Password changes successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error on changing password");
        }
    }

    @PostMapping(value = "/update-own-password/{ownPassword}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changeOwnPassword(@PathVariable(name = "ownPassword") String ownPassword, HttpServletRequest httpRequest) {
        try {

            String currentUser = UserUtil.getCurrentUserName();
            if (currentUser != null) {
                userService.changePassword(currentUser, ownPassword);
                return ResponseEntity.ok("Password changes successfully");
            }

        } catch (RuntimeException e) {
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error on changing password");
    }

    @PostMapping("/add-role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@RequestParam String username, @RequestParam String roleName,
                                                HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        userService.addRoleToUser(username, roleName, token);
        return ResponseEntity.ok("Role '" + roleName + "' added to user '" + username + "'");
    }

    @PostMapping("/remove-role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> removeRoleFromUser(@RequestParam String username, @RequestParam String roleName,
                                                     HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        userService.removeRoleFromUser(username, roleName, token);
        return ResponseEntity.ok("Role '" + roleName + "' removed from user '" + username + "'");
    }

    @PostMapping("/remove-user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> removeUser(@RequestParam String username, HttpServletRequest httpRequest, Role role) {
        String token = httpRequest.getHeader("Authorization");
        userService.removeUser(username, token, role);
        return ResponseEntity.ok("User '" + username + "' removed");
    }

    @PostMapping("/bank/credit-info/save")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> aggregateAndSaveCreditInfo(@RequestParam String username, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        userService.aggregateAndSaveCreditInfo(username,token);
        return ResponseEntity.ok("Credit information aggregated and saved successfully");
    }

    @PutMapping("/rate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> calculateAndSaveRate(@RequestParam String username,HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        userService.calculateAndSaveRate(username,token);
        return ResponseEntity.ok("Rate calculated and saved.");
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserDto> getUsers(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        return userService.getUsers(token);
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    public UserDto getUser(HttpServletRequest httpRequest, @PathVariable(name = "id") Long id) {
        String token = httpRequest.getHeader("Authorization");
        return userService.getUser(id, token);
    }

    @GetMapping("/available-credits")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CreditDto>> getAvailableCredits(@RequestHeader("Authorization") String token) {
        try {
            List<CreditDto> availableCredits = userService.getAvailableCredits(token);
            return ResponseEntity.ok(availableCredits);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/select-credit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> selectCredit(@RequestParam Long creditId, @RequestHeader("Authorization") String token) {
        try {
            userService.selectCreditForUser(creditId, token);
            return ResponseEntity.ok("Credit selected and added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error selecting credit: " + e.getMessage());
        }
    }

    @PostMapping("/pay-total-credit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> payTotalCredit(@RequestHeader("Authorization") String token,
                                                 @RequestParam int paymentAmount) {
        userService.payTotalCredit(token, paymentAmount);
        return ResponseEntity.ok("Total credit payment successful");
    }
}
