package com.saqib.Train_Service.Service;

import com.saqib.Train_Service.dto.AvailableSeatsDTO;
import com.saqib.Train_Service.dto.SeatUpdateRequest;
import com.saqib.Train_Service.dto.TrainRequestDTO;
import com.saqib.Train_Service.dto.TrainResponseDTO;
import com.saqib.Train_Service.exception.SeatUnavailableException;
import com.saqib.Train_Service.mapper.TrainMapper;
import com.saqib.Train_Service.model.Train;
import com.saqib.Train_Service.repo.TrainRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private final TrainRepository repo;

    public TrainService(TrainRepository repo) {
        this.repo = repo;
    }

    // ────────────────────────── CREATE ──────────────────────────

    public TrainResponseDTO create(TrainRequestDTO dto) {
        Train saved = repo.save(TrainMapper.toEntity(dto));
        return TrainMapper.toResponse(saved);
    }

    public List<TrainResponseDTO> createBatch(List<TrainRequestDTO> list) {
        List<Train> entities = TrainMapper.toEntityList(list);
        return TrainMapper.toResponseList(repo.saveAll(entities));
    }

    // ────────────────────────── READ ──────────────────────────

    public List<TrainResponseDTO> getAll() {
        return TrainMapper.toResponseList(repo.findAll());
    }

    public TrainResponseDTO getOne(Long trainId) {
        Train train = repo.findByTrainId(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));
        return TrainMapper.toResponse(train);
    }

    // ────────────────────────── UPDATE ──────────────────────────

    public TrainResponseDTO update(Long trainId, TrainRequestDTO dto) {
        Train train = repo.findByTrainId(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));
        TrainMapper.copyRequestToEntity(dto, train);
        return TrainMapper.toResponse(repo.save(train));
    }

    // ────────────────────────── DELETE ──────────────────────────

    public void delete(Long trainId) {
        if (!repo.existsByTrainId(trainId)) {
            throw new IllegalArgumentException("Train not found");
        }
        repo.deleteByTrainId(trainId);
    }

    // ───────────────────── Seat management ─────────────────────

    public int getTotalSeats(Long trainId) {
        Train t = repo.findByTrainId(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));
        return t.getPassengerCapacity();
    }

    @Transactional
    public void reduceSeats(Long trainId, int seats) {
        Train t = repo.findByTrainIdForUpdate(trainId)   // PESSIMISTIC_WRITE query
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));
        int updated = t.getAvailableSeats() - seats;
        if (updated < 0) throw new IllegalArgumentException("Insufficient seats");
        t.setAvailableSeats(updated);
        repo.save(t);
    }

    @Transactional
    public void increaseSeats(Long trainId, int seats) {
        Train t = repo.findByTrainIdForUpdate(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));
        t.setAvailableSeats(t.getAvailableSeats() + seats);
        repo.save(t);
    }

    // ─────────────────────── Searches ───────────────────────

    public List<TrainResponseDTO> findByName(String trainName) {
        return TrainMapper.toResponseList(repo.findByTrainNameIgnoreCase(trainName));
    }

    public List<TrainResponseDTO> findByRoute(String source, String destination) {
        return TrainMapper.toResponseList(repo.findBySourceAndDestination(source, destination));
    }

    // TrainService.java

    public AvailableSeatsDTO getAvailableSeats(Long trainId) {
        Train t = repo.findByTrainId(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));

        AvailableSeatsDTO dto = new AvailableSeatsDTO();
        dto.setTotal(t.getAvailableSeats());
        dto.setClass2S(t.getClass2S());
        dto.setClassSl(t.getClassSl());
        dto.setClass3Ac(t.getClass3Ac());
        dto.setClass2Ac(t.getClass2Ac());
        dto.setClass1Ac(t.getClass1Ac());
        return dto;
    }


    ///  here i add
    @Transactional
    public void reduceSeats(Long trainId, SeatUpdateRequest req) {
        Train t = repo.findByTrainIdForUpdate(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));

        int seats = req.getSeats();
        switch (req.getCoachClass()) {
            case SITTING:
                validate(t.getClass2S (), seats);
                t.setClass2S(t.getClass2S() - seats);
                break;
            case SL:
                validate(t.getClassSl(), seats);
                t.setClassSl(t.getClassSl() - seats);
                break;
            case AC3:
                validate(t.getClass3Ac(), seats);
                t.setClass3Ac(t.getClass3Ac() - seats);
                break;
            case AC2:
                validate(t.getClass2Ac(), seats);
                t.setClass2Ac(t.getClass2Ac() - seats);
                break;
            case AC1:
                validate(t.getClass1Ac(), seats);
                t.setClass1Ac(t.getClass1Ac() - seats);
                break;
        }
        t.setAvailableSeats(totalLeft(t));   // sync grand‑total
        repo.save(t);
    }

    @Transactional
    public void increaseSeats(Long trainId, SeatUpdateRequest req) {
        Train t = repo.findByTrainIdForUpdate(trainId)
                .orElseThrow(() -> new IllegalArgumentException("Train not found"));

        int seats = req.getSeats();
        switch (req.getCoachClass()) {
            case SITTING: t.setClass2S (t.getClass2Ac () + seats); break;
            case SL:        t.setClassSl (t.getClassSl () + seats); break;
            case AC3:            t.setClass3Ac(t.getClass3Ac () + seats); break;
            case AC2:            t.setClass2Ac(t.getClass2Ac() + seats); break;
            case AC1:            t.setClass1Ac(t.getClass1Ac() + seats); break;
        }
        t.setAvailableSeats(totalLeft(t));
        repo.save(t);
    }

    private void validate(int available, int requested) {
        if (available < requested) {
            throw new SeatUnavailableException ("Seats unavailable in selected class");
        }
    }


    private int totalLeft(Train t) {
        return t.getClass2S () + t.getClassSl() + t.getClass3Ac()
                + t.getClass2Ac() + t.getClass1Ac();
    }

}
