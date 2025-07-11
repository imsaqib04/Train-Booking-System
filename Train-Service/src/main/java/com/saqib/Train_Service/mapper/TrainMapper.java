package com.saqib.Train_Service.mapper;

import com.saqib.Train_Service.dto.*;
import com.saqib.Train_Service.enums.TrainStatus;
import com.saqib.Train_Service.enums.TrainType;
import com.saqib.Train_Service.model.IntermediateStation;
import com.saqib.Train_Service.model.Train;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TrainMapper {

    /* ───────────── DTO ➜ Entity ───────────── */
    public static Train toEntity(TrainRequestDTO dto) {
        if (dto == null) return null;

        Train train = new Train();
        copyRequestToEntity(dto, train);
        return train;
    }

    /* copy into existing entity (for update) */
    public static void copyRequestToEntity(TrainRequestDTO dto, Train train) {
        if (dto == null || train == null) return;

        train.setTrainId(dto.getTrainId());
        train.setTrainName(dto.getTrainName());
        train.setPassengerCapacity(dto.getPassengerCapacity());
        train.setTrainType(dto.getTrainType());          // already enum
        train.setSource(dto.getSource());
        train.setDestination(dto.getDestination());
        train.setTravelDate(dto.getTravelDate());
        train.setArrivalTime(dto.getArrivalTime());
        train.setDepartureTime(dto.getDepartureTime());
        train.setAvailableSeats(dto.getAvailableSeats());

        train.setSeats2S (dto.getSeats2S ());
        train.setSeatsSl (dto.getSeatsSl ());
        train.setSeats3Ac (dto.getSeats3Ac ());
        train.setSeats2Ac(dto.getSeats2Ac ());
        train.setSeats1Ac(dto.getSeats1Ac ());

        train.setRunningDays(dto.getRunningDays());
        train.setStatus(dto.getStatus());

        train.setFare2S(dto.getFare2S());
        train.setFareSl(dto.getFareSl());
        train.setFare3Ac(dto.getFare3Ac());
        train.setFare2Ac(dto.getFare2Ac());
        train.setFare1Ac(dto.getFare1Ac());

        /* null‑safe — only overwrite if provided */
        if (dto.getDistanceInKm() != null) {
            train.setDistanceInKm(dto.getDistanceInKm());
        }
    }

    /* ───────────── Entity ➜ ResponseDTO ───────────── */
    public static TrainResponseDTO toResponse(Train train) {
        if (train == null) return null;

        TrainResponseDTO dto = new TrainResponseDTO();
//        dto.setId(train.getId());
        dto.setTrainId(train.getTrainId());
        dto.setTrainName(train.getTrainName());
        dto.setPassengerCapacity(train.getPassengerCapacity());
        dto.setTrainType( TrainType.valueOf ( train.getTrainType() ) );
        dto.setSource(train.getSource());
        dto.setDestination(train.getDestination());
        dto.setTravelDate(train.getTravelDate());
        dto.setArrivalTime(train.getArrivalTime());
        dto.setDepartureTime(train.getDepartureTime());
        dto.setAvailableSeats(train.getAvailableSeats());

        dto.setClass2S(train.getSeats2S ());
        dto.setClassSl(train.getSeatsSl());
        dto.setClass3Ac(train.getSeats3Ac());
        dto.setClass2Ac(train.getSeats2Ac());
        dto.setClass1Ac(train.getSeats1Ac());

        dto.setRunningDays(train.getRunningDays());
        dto.setStatus( TrainStatus.valueOf ( train.getStatus() ) );

        dto.setFare2S(train.getFare2S());
        dto.setFareSl(train.getFareSl());
        dto.setFare3Ac(train.getFare3Ac());
        dto.setFare2Ac(train.getFare2Ac());
        dto.setFare1Ac(train.getFare1Ac());

        dto.setDistanceInKm(train.getDistanceInKm());

        /* nested stations */
        List<IntermediateStationDTO> stationDTOs = new ArrayList<>();
        if (train.getIntermediateStations() != null) {
            for (IntermediateStation s : train.getIntermediateStations()) {
                IntermediateStationDTO sdto = new IntermediateStationDTO();
                sdto.setId(s.getId());
                sdto.setStationName(s.getStationName());
                if (s.getArrivalTime() != null) {
                    sdto.setArrivalTime( LocalTime.parse ( s.getArrivalTime().toString() ) );
                }
                if (s.getDepartureTime() != null) {
                    sdto.setDepartureTime( LocalTime.parse ( s.getDepartureTime().toString() ) );
                }
                sdto.setStopNumber(s.getStopNumber());
                stationDTOs.add(sdto);
            }
        }
        dto.setIntermediateStations(stationDTOs);

        return dto;
    }

    /* ───────────── Bulk helpers ───────────── */

    public static List<TrainResponseDTO> toResponseList(List<Train> trains) {
        List<TrainResponseDTO> list = new ArrayList<>();
        if (trains != null) {
            for (Train t : trains) {
                list.add(toResponse(t));
            }
        }
        return list;
    }

    public static List<Train> toEntityList(List<TrainRequestDTO> dtos) {
        List<Train> list = new ArrayList<>();
        if (dtos != null) {
            for (TrainRequestDTO dto : dtos) {
                list.add(toEntity(dto));
            }
        }
        return list;
    }
}
