package com.veterinary.converter;

import com.veterinary.dto.TreatmentDto;
import com.veterinary.model.Treatment;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

@Data(staticConstructor = "newInstance")
public class TreatmentDtoConverter implements Converter<TreatmentDto, Treatment>{

        @Override
        public Treatment convert(TreatmentDto treatmentDto) {
        if (Objects.isNull(treatmentDto)) {
            return null;
        }
        return Treatment.builder()
                .id(treatmentDto.getId())
                .price(treatmentDto.getPrice())
                .date(treatmentDto.getDate())
                .status(treatmentDto.getStatus())
                .type(treatmentDto.getType())
                .build();
    }
}
