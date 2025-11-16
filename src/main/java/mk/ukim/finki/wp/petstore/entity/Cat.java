package mk.ukim.finki.wp.petstore.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CAT")
@SuperBuilder
@NoArgsConstructor
public class Cat extends Pet {

    @Override
    public BigDecimal calculatePrice() {

        return BigDecimal.valueOf(getAgeInYears());
    }

    @Override
    public String getPetType() {
        return "CAT";
    }
}
