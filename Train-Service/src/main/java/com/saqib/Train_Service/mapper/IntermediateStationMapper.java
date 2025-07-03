package com.saqib.Train_Service.mapper;

import com.saqib.Train_Service.dto.IntermediateStationDTO;
import com.saqib.Train_Service.model.IntermediateStation;

public class IntermediateStationMapper {

    public static IntermediateStation toEntity(IntermediateStationDTO dto) {
        if (dto == null) return null;
        IntermediateStation s = new IntermediateStation();
        copyDtoToEntity(dto, s);
        return s;
    }

    public static void copyDtoToEntity(IntermediateStationDTO dto, IntermediateStation s) {
        s.setStationName(dto.getStationName());
        s.setArrivalTime(dto.getArrivalTime());
        s.setDepartureTime(dto.getDepartureTime());
        if (dto.getStopNumber() != null) {
            s.setStopNumber(dto.getStopNumber());
        }
    }

    public static IntermediateStationDTO toDto(IntermediateStation s) {
        if (s == null) return null;
        IntermediateStationDTO d = new IntermediateStationDTO();
        d.setId(s.getId());
        d.setStationName(s.getStationName());
        d.setArrivalTime(s.getArrivalTime());
        d.setDepartureTime(s.getDepartureTime());
        d.setStopNumber(s.getStopNumber());
        return d;
    }
}
