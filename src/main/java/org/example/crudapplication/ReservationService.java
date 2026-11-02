package org.example.crudapplication;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.crudapplication.Exceptions.ReservationNotFoundException;
import org.example.crudapplication.Exceptions.ReservationRequestIllegalArgumentException;
import org.example.crudapplication.Exceptions.ReservationRequestIllegalStateException;
import org.example.crudapplication.availability.ReservationAvailabilityService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;

    private final ReservationRepo reservationRepo;

    private final ReservationAvailabilityService reservationAvailabilityService;

    public ReservationService(
            ReservationMapper reservationMapper
            ,ReservationRepo reservationRepo
            ,ReservationAvailabilityService reservationAvailabilityService) {

        this.reservationMapper = reservationMapper;
        this.reservationRepo = reservationRepo;
        this.reservationAvailabilityService = reservationAvailabilityService;
    }

    public ReservationResponseModel getReservationById(Long id){

        ReservationEntity reservationEntity = reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("reservation not found id=" + id)
                );

        return reservationMapper.toDomain(reservationEntity);
    }

    public List<ReservationResponseModel> searchAllByFilter(
            ReservationSearchFilterModel filter
    ){
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 10;

        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        List<ReservationEntity> responseEntities = reservationRepo.searchAllByFilter(
                filter.roomId()
                ,filter.userId()
                ,pageable
        );

        return responseEntities.stream()
                .map(reservationMapper::toDomain)
                .toList();
    }
    
    public ReservationResponseModel createReservation(
            ReservationRequestModel reservationToCreate
    ){

        if(!reservationToCreate.endDate().isAfter(reservationToCreate.startDate())){
            throw new ReservationRequestIllegalArgumentException("Start date must 1 day earlier than end date");
        }

        var entityToSave = reservationMapper.toEntity(reservationToCreate);
        entityToSave.setStatus(ReservationStatus.PENDING);
        var savedEntity = reservationRepo.save(entityToSave);
        return reservationMapper.toDomain(savedEntity);
    }

    public ReservationResponseModel updateReservation(
            Long id
            ,ReservationRequestModel reservationToUpdate
    ){

        var reservationEntity = reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Not found id=" + id));

        if(reservationEntity.getStatus() == ReservationStatus.PENDING){
            throw new ReservationRequestIllegalStateException
                    ("Cannot modify reservation: status=" + reservationEntity.getStatus());
        }

        if(!reservationToUpdate.endDate().isAfter(reservationToUpdate.startDate())){
            throw new ReservationRequestIllegalArgumentException("Start date must 1 day earlier than end date");
        }

        var entityToUpdate = reservationMapper.toEntity(reservationToUpdate);
        entityToUpdate.setStatus(ReservationStatus.PENDING);
        var entityToSave = reservationRepo.save(entityToUpdate);
        return reservationMapper.toDomain(entityToSave);
    }

    @Transactional
    public void cancelReservation(Long id){

        var reservationEntity = reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Not found id=" + id));

        if(reservationEntity.getStatus() == ReservationStatus.APPROVED){
            throw new ReservationRequestIllegalStateException
                    ("Cannot cancel confirmed reservation,contact with manager,please");
        }

        if(reservationEntity.getStatus() == ReservationStatus.CANCELLED){
            throw new ReservationRequestIllegalStateException
                    ("Cannot cancel cancelled reservation: status=" + reservationEntity.getStatus());
        }

        reservationRepo.setStatus(id, ReservationStatus.CANCELLED);
        log.info("Reservation was cancelled id={}",id);

//        reservationEntity.setStatus(ReservationStatus.CANCELLED);
//        log.info("Reservation was cancelled id=" + id);

    }

    public ReservationResponseModel approveReservation(Long id){

        var reservationEntity = reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Not found id=" + id));

        if(reservationEntity.getStatus() == ReservationStatus.APPROVED){
            throw new ReservationRequestIllegalStateException
                    ("Reservation already has been confirmed");
        }

        if(reservationEntity.getStatus() == ReservationStatus.CANCELLED){
            throw new ReservationRequestIllegalStateException
                    ("Reservation is cancelled");
        }

        var available = reservationAvailabilityService.isReservationAvailable(
                reservationEntity.getRoomId(),
                reservationEntity.getStartDate(),
                reservationEntity.getEndDate()
        );

        if(!available){
            throw new ReservationRequestIllegalStateException
                    ("Cannot confirm reservation because conflict id=" + id);
        }

        reservationEntity.setStatus(ReservationStatus.APPROVED);
        var approvedReservation = reservationRepo.save(reservationEntity);
        return reservationMapper.toDomain(approvedReservation);


    }
}
