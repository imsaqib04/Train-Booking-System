package com.saqib.Train_Service.Service;

import com.saqib.Train_Service.dto.AvailableSeatsDTO;
import com.saqib.Train_Service.dto.SeatUpdateRequest;
import com.saqib.Train_Service.dto.TrainRequestDTO;
import com.saqib.Train_Service.dto.TrainResponseDTO;
import com.saqib.Train_Service.exception.SeatUnavailableException;
import com.saqib.Train_Service.mapper.TrainMapper;
import com.saqib.Train_Service.model.Train;
import com.saqib.Train_Service.repo.IntermediateStationRepository;
import com.saqib.Train_Service.repo.TrainRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrainService {

    @Autowired
    private TrainRepository repo;

    @Autowired
    private IntermediateStationRepository intermediateStationRepository;

    // ────────────────────────── CREATE ──────────────────────────

    public TrainResponseDTO create(TrainRequestDTO dto) {
        Train saved = repo.save ( TrainMapper.toEntity ( dto ) );
        return TrainMapper.toResponse ( saved );
    }

    public List<TrainResponseDTO> createBatch(List<TrainRequestDTO> list) {
        List<Train> entities = TrainMapper.toEntityList ( list );
        return TrainMapper.toResponseList ( repo.saveAll ( entities ) );
    }

    // ────────────────────────── READ ──────────────────────────

    public List<TrainResponseDTO> getAll() {
        return TrainMapper.toResponseList ( repo.findAll () );
    }

    public TrainResponseDTO getOne(Long trainId) {
        Train train = repo.findByTrainId ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );
        return TrainMapper.toResponse ( train );
    }

    // ────────────────────────── UPDATE ──────────────────────────

    public TrainResponseDTO update(Long trainId, TrainRequestDTO dto) {
        Train train = repo.findByTrainId ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );
        TrainMapper.copyRequestToEntity ( dto, train );
        return TrainMapper.toResponse ( repo.save ( train ) );
    }

    // ────────────────────────── DELETE ──────────────────────────

    public void delete(Long trainId) {
        if (!repo.existsByTrainId ( trainId )) {
            throw new IllegalArgumentException ( "Train not found" );
        }
        repo.deleteByTrainId ( trainId );
    }

    // ───────────────────── Seat management ─────────────────────

    public int getTotalSeats(Long trainId) {
        Train t = repo.findByTrainId ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );
        return t.getPassengerCapacity ();
    }

    @Transactional
    public void reduceSeats(Long trainId, int seats) {
        Train t = repo.findByTrainIdForUpdate ( trainId )   // PESSIMISTIC_WRITE query
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );
        int updated = t.getAvailableSeats () - seats;
        if (updated < 0) throw new IllegalArgumentException ( "Insufficient seats" );
        t.setAvailableSeats ( updated );
        repo.save ( t );
    }

    @Transactional
    public void increaseSeats(Long trainId, int seats) {
        Train t = repo.findByTrainIdForUpdate ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );
        t.setAvailableSeats ( t.getAvailableSeats () + seats );
        repo.save ( t );
    }

    // ─────────────────────── Searches ───────────────────────

    public List<TrainResponseDTO> findByName(String trainName) {
        return TrainMapper.toResponseList ( repo.findByTrainNameIgnoreCase ( trainName ) );
    }

    public List<TrainResponseDTO> findByRoute(String source, String destination) {
        return TrainMapper.toResponseList ( repo.findBySourceAndDestination ( source, destination ) );
    }

    // TrainService.java

    public AvailableSeatsDTO getAvailableSeats(Long trainId) {
        Train t = repo.findByTrainId ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );

        AvailableSeatsDTO dto = new AvailableSeatsDTO ();
        dto.setTotal ( t.getAvailableSeats () );
        dto.setClass2S ( t.getSeats2S () );
        dto.setClassSl ( t.getSeatsSl () );
        dto.setClass3Ac ( t.getSeats3Ac () );
        dto.setClass2Ac ( t.getSeats2Ac () );
        dto.setClass1Ac ( t.getSeats1Ac () );
        return dto;
    }


    ///  here i add
    @Transactional
    public void reduceSeats(Long trainId, SeatUpdateRequest req) {
        Train t = repo.findByTrainIdForUpdate ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );

        int seats = req.getSeats ();
        switch (req.getCoachClass ()) {
            case SITTING:
                validate ( t.getSeats2S (), seats );
                t.setSeats2S ( t.getSeats2S () - seats );
                break;
            case SL:
                validate ( t.getSeatsSl (), seats );
                t.setSeatsSl ( t.getSeatsSl () - seats );
                break;
            case AC3:
                validate ( t.getSeats3Ac (), seats );
                t.setSeats3Ac ( t.getSeats3Ac () - seats );
                break;
            case AC2:
                validate ( t.getSeats2Ac (), seats );
                t.setSeats2Ac ( t.getSeats2Ac () - seats );
                break;
            case AC1:
                validate ( t.getSeats1Ac (), seats );
                t.setSeats1Ac ( t.getSeats1Ac () - seats );
                break;
        }
        t.setAvailableSeats ( totalLeft ( t ) );   // sync grand‑total
        repo.save ( t );
    }

    @Transactional
    public void increaseSeats(Long trainId, SeatUpdateRequest req) {
        Train t = repo.findByTrainIdForUpdate ( trainId )
                .orElseThrow ( () -> new IllegalArgumentException ( "Train not found" ) );

        int seats = req.getSeats ();
        switch (req.getCoachClass ()) {
            case SITTING:
                t.setSeats2S ( t.getSeats2S () + seats );
                break;
            case SL:
                t.setSeatsSl ( t.getSeatsSl () + seats );
                break;
            case AC3:
                t.setSeats3Ac ( t.getSeats3Ac () + seats );
                break;
            case AC2:
                t.setSeats2Ac ( t.getSeats2Ac () + seats );
                break;
            case AC1:
                t.setSeats1Ac ( t.getSeats1Ac () + seats );
                break;
        }
        t.setAvailableSeats ( totalLeft ( t ) );
        repo.save ( t );
    }

    private void validate(int available, int requested) {
        if (available < requested) {
            throw new SeatUnavailableException ( "Seats unavailable in selected class" );
        }
    }


    private int totalLeft(Train t) {
        return t.getSeats2S () + t.getSeatsSl () + t.getSeats3Ac ()
                + t.getSeats2Ac () + t.getSeats1Ac ();
    }


    public TrainService(IntermediateStationRepository stationRepo) {
        this.intermediateStationRepository = stationRepo;
    }

//    public Integer stopNumber(Long trainId, String stationName) {
//        return intermediateStationRepository.findStopNumber ( trainId, stationName )
//                .orElseThrow ();
//    }

    @Autowired
    private TrainRepository trainRepository;


    public int stopNumber(Long trainId, String stationName) {
        Long pk = trainRepository.findPkByTrainId(trainId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Train not found: " + trainId));

        return intermediateStationRepository.findStopNumber(pk, stationName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Station '"+stationName+"' not found for train " + trainId));
    }

}