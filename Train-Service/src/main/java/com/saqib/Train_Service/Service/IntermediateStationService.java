package com.saqib.Train_Service.Service;

import com.saqib.Train_Service.dto.IntermediateStationDTO;
import com.saqib.Train_Service.mapper.IntermediateStationMapper;
import com.saqib.Train_Service.model.IntermediateStation;
import com.saqib.Train_Service.model.Train;
import com.saqib.Train_Service.repo.IntermediateStationRepository;
import com.saqib.Train_Service.repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntermediateStationService {

    @Autowired
    private IntermediateStationRepository stationRepo;

    @Autowired
    private TrainRepository trainRepo;

    /* Fetch ordered list */
    public List<IntermediateStationDTO> getByTrain(Long trainId) {
        List<IntermediateStation> list =
                stationRepo.findByTrainTrainIdOrderByStopNumberAsc(trainId);
        List<IntermediateStationDTO> dtos = new ArrayList<> ();
        for (IntermediateStation s : list) dtos.add( IntermediateStationMapper.toDto(s));
        return dtos;
    }

    /* Single add */
    public IntermediateStationDTO add(Long trainId, IntermediateStationDTO dto) {
        Train train = trainRepo.findByTrainId(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));

        IntermediateStation st = IntermediateStationMapper.toEntity(dto);
        st.setTrain(train);

        /* auto stopNumber if null */
        if (st.getStopNumber() == 0) {
            Integer maxStop = stationRepo.maxStopNumberByTrain(trainId).orElse(0);
            st.setStopNumber(maxStop + 1);
        }

        return IntermediateStationMapper.toDto(stationRepo.save(st));
    }

    /* Batch add */
    public List<IntermediateStationDTO> addBatch(Long trainId,
                                                 List<IntermediateStationDTO> dtoList) {
        List<IntermediateStationDTO> saved = new ArrayList<>();
        for (IntermediateStationDTO d : dtoList) {
            saved.add(add(trainId, d));    // reuse single method for validation + auto‑stop
        }
        return saved;
    }

    public void delete(Long stationId) {
        stationRepo.deleteById(stationId);
    }
}
