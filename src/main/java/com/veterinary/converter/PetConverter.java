package com.veterinary.converter;

import com.veterinary.dto.CustomerDto;
import com.veterinary.dto.PetDto;
import com.veterinary.model.Pet;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.Optional;

@Data(staticConstructor = "newInstance")
public class PetConverter implements Converter<Pet, PetDto> {

    @Override
    public PetDto convert(Pet pet) {
        if (Objects.isNull(pet)) {
            return null;
        }
        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .entryDate(pet.getEntryDate())
                .category(pet.getCategory())
                .weight(pet.getWeight())
                .build();
    }
}
