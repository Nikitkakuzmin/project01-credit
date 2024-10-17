package kz.nik.creditcreditsservice.api;


import kz.nik.creditcreditsservice.client.AuthClient;
import kz.nik.creditcreditsservice.dto.CreditDto;



import kz.nik.creditcreditsservice.service.CreditService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/credit")
public class CreditController {
    private final CreditService creditService;
    private final AuthClient authClient;



    @GetMapping
    public ResponseEntity<List<CreditDto>> getCredits(@RequestHeader("Authorization") String token) {
        if (!authClient.isAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CreditDto> credits = creditService.getCredits();
        return ResponseEntity.ok(credits);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<CreditDto> getCredit(@PathVariable(name = "id") Long id,
                                               @RequestHeader("Authorization") String token) {
        if (!authClient.isAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CreditDto creditDto = creditService.getCredit(id);
        return ResponseEntity.ok(creditDto);
    }

    @PostMapping(value = "/add-credit")
    public ResponseEntity<CreditDto> addCredit(@RequestBody CreditDto creditDto,
                                               @RequestHeader("Authorization") String token) {
        if (!authClient.isAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CreditDto addedCredit = creditService.addCredit(creditDto, token);
        return ResponseEntity.ok(addedCredit);
    }

    @PutMapping(value = "/update-credit")
    public ResponseEntity<CreditDto> updateCredit(@RequestBody CreditDto creditDto,
                                                  @RequestHeader("Authorization") String token) {
        if (!authClient.isAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CreditDto updatedCredit = creditService.updateCredit(creditDto, token);
        return ResponseEntity.ok(updatedCredit);
    }

    @DeleteMapping(value = "/delete-credit/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable(name = "id") Long id,
                                             @RequestHeader("Authorization") String token) {
        if (!authClient.isAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }



}












