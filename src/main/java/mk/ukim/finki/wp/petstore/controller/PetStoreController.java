package mk.ukim.finki.wp.petstore.controller;

import mk.ukim.finki.wp.petstore.entity.Pet;
import mk.ukim.finki.wp.petstore.entity.PurchaseHistory;
import mk.ukim.finki.wp.petstore.entity.User;
import mk.ukim.finki.wp.petstore.service.PetStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petstore")
@RequiredArgsConstructor
public class PetStoreController {

    private final PetStoreService petStoreService;

    @PostMapping("/create-users")
    public ResponseEntity<List<User>> createUsers() {
        List<User> users = petStoreService.createUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create-pets")
    public ResponseEntity<List<Pet>> createPets() {
        List<Pet> pets = petStoreService.createPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/list-users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = petStoreService.listUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/list-pets")
    public ResponseEntity<List<Pet>> listPets() {
        List<Pet> pets = petStoreService.listPets();
        return ResponseEntity.ok(pets);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyPets() {
        petStoreService.buyPets();
        return ResponseEntity.ok("Buy process completed. Check console for details.");
    }

    @GetMapping("/history-log")
    public ResponseEntity<List<PurchaseHistory>> getHistoryLog() {
        List<PurchaseHistory> history = petStoreService.getHistoryLog();
        return ResponseEntity.ok(history);
    }
}