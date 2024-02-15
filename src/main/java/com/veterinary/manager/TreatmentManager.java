package com.veterinary.manager;

import com.veterinary.converter.TreatmentConverter;
import com.veterinary.converter.TreatmentDtoConverter;
import com.veterinary.dto.TreatmentDto;
import com.veterinary.model.Treatment;
import com.veterinary.repository.TreatmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class TreatmentManager {

    private TreatmentRepository treatmentRepository;

    public List<TreatmentDto> findAll(){
        return treatmentRepository.findAll()
                .stream()
                .map(treatment -> TreatmentConverter.newInstance().convert(treatment))
                .collect(Collectors.toList());
    }

    public List<Treatment> findByIds(List<Long> ids){
        List<Treatment> treatments = treatmentRepository.findByIdIn(ids);
        return treatments;
    }

    public TreatmentDto saveTreatment(TreatmentDto treatmentDto){
        Treatment treatment = TreatmentDtoConverter.newInstance().convert(treatmentDto);
        Treatment savedTreatment = treatmentRepository.save(treatment);
        return TreatmentConverter.newInstance().convert(savedTreatment);
    }

    public TreatmentDto updateTreatment(TreatmentDto treatmentDto){
        Optional<Treatment> oldTreatment = treatmentRepository.findById(treatmentDto.getId());
        if(oldTreatment.isPresent()){
            Treatment newTreatment = oldTreatment.get();
            newTreatment.setPrice(treatmentDto.getPrice());
            newTreatment.setDate(treatmentDto.getDate());
            newTreatment.setStatus(treatmentDto.getStatus());
            newTreatment.setType(treatmentDto.getType());
            Treatment savedNewTreatment = treatmentRepository.save(newTreatment);
            return TreatmentConverter.newInstance().convert(savedNewTreatment);
        }
        return null;
    }

    public void deleteTreatment(Long id){
        treatmentRepository.deleteById(id);
    }
}



