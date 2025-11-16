package mk.ukim.finki.wp.petstore.service;

import mk.ukim.finki.wp.petstore.entity.*;
import mk.ukim.finki.wp.petstore.repository.PetRepository;
import mk.ukim.finki.wp.petstore.repository.PurchaseHistoryRepository;
import mk.ukim.finki.wp.petstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetStoreService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final Random random = new Random();

    private static final String[] FIRST_NAMES = {
            "Jovan", "Marko", "Miki", "Sara", "David", "Ema", "Robert", "Lisa", "James", "Mary"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Petreski", "Jovanonski"
    };

    private static final String[] CAT_NAMES = {
            "Leo", "Mittens", "Shadow", "Luna", "Oliver", "Bella", "Simba", "Chloe"
    };

    private static final String[] DOG_NAMES = {
            "Max", "Buddy", "Charlie", "Rocky", "Duke", "Cooper", "Bear", "Zeus", "Apollo", "Rex"
    };

    private static final String[] CAT_DESCRIPTIONS = {
            "Playful tabby cat", "Elegant Persian cat", "Friendly Siamese cat", "Curious Bengal cat"
    };

    private static final String[] DOG_DESCRIPTIONS = {
            "Loyal Golden Retriever", "Energetic Labrador", "Protective German Shepherd",
            "Friendly Beagle", "Intelligent Border Collie"
    };

    @Transactional
    public List<User> createUsers() {
        log.info("Creating users...");
        userRepository.deleteAll();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .firstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)])
                    .lastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)])
                    .email(generateEmail(i))
                    .budget(BigDecimal.valueOf(random.nextInt(50) + 10))
                    .build();
            users.add(user);
        }

        List<User> savedUsers = userRepository.saveAll(users);
        log.info("Created {} users", savedUsers.size());
        return savedUsers;
    }

    @Transactional
    public List<Pet> createPets() {
        log.info("Creating pets...");
        petRepository.deleteAll();

        List<Pet> pets = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            Cat cat = Cat.builder()
                    .name(CAT_NAMES[random.nextInt(CAT_NAMES.length)] + i)
                    .description(CAT_DESCRIPTIONS[random.nextInt(CAT_DESCRIPTIONS.length)])
                    .dateOfBirth(generateRandomDate())
                    .build();
            pets.add(cat);
        }


        for (int i = 0; i < 10; i++) {
            Dog dog = Dog.builder()
                    .name(DOG_NAMES[random.nextInt(DOG_NAMES.length)] + i)
                    .description(DOG_DESCRIPTIONS[random.nextInt(DOG_DESCRIPTIONS.length)])
                    .dateOfBirth(generateRandomDate())
                    .rating(random.nextInt(11))
                    .build();
            pets.add(dog);
        }

        List<Pet> savedPets = petRepository.saveAll(pets);
        log.info("Created {} pets", savedPets.size());
        return savedPets;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }

    @Transactional
    public void buyPets() {
        log.info("Starting buy process for all users...");

        List<User> users = userRepository.findAll();
        List<Pet> availablePets = petRepository.findAvailablePets();

        int successfulPurchases = 0;
        int failedPurchases = 0;

        for (User user : users) {
            boolean purchased = false;

            for (Pet pet : availablePets) {
                if (pet.getOwner() == null) {
                    BigDecimal price = pet.calculatePrice();

                    if (user.getBudget().compareTo(price) >= 0) {

                        pet.setOwner(user);
                        user.setBudget(user.getBudget().subtract(price));

                        petRepository.save(pet);
                        userRepository.save(user);

                        if (pet instanceof Cat) {
                            System.out.println(String.format("Meow, cat %s has owner %s %s",
                                    pet.getName(), user.getFirstName(), user.getLastName()));
                        } else if (pet instanceof Dog) {
                            System.out.println(String.format("Woof, dog %s has owner %s %s",
                                    pet.getName(), user.getFirstName(), user.getLastName()));
                        }

                        purchased = true;
                        successfulPurchases++;
                        break;
                    }
                }
            }

            if (!purchased) {
                failedPurchases++;
                log.info("User {} {} could not buy any pet (budget: {})",
                        user.getFirstName(), user.getLastName(), user.getBudget());
            }
        }


        PurchaseHistory history = PurchaseHistory.builder()
                .executionDate(LocalDateTime.now())
                .successfulPurchases(successfulPurchases)
                .failedPurchases(failedPurchases)
                .build();
        purchaseHistoryRepository.save(history);

        log.info("Buy process completed. Successful: {}, Failed: {}",
                successfulPurchases, failedPurchases);
    }

    public List<PurchaseHistory> getHistoryLog() {
        return purchaseHistoryRepository.findAll();
    }

    private String generateEmail(int index) {
        return String.format("user%d@petstore.com", index);
    }

    private LocalDate generateRandomDate() {

        int yearsAgo = random.nextInt(10) + 1;
        int daysOffset = random.nextInt(365);
        return LocalDate.now().minusYears(yearsAgo).minusDays(daysOffset);
    }
}