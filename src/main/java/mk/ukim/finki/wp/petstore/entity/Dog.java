package mk.ukim.finki.wp.petstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("DOG")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Dog extends Pet {


    private Integer rating;

    @Override
    public BigDecimal calculatePrice() {

        return BigDecimal.valueOf(getAgeInYears()).add(BigDecimal.valueOf(rating));
    }

    @Override
    public String getPetType() {
        return "DOG";
    }
}