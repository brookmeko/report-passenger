package et.gov.customs.profile.service.data.customsOperation.adapter;

import et.gov.customs.profile.service.data.customsOperation.entity.PassengerProfileEntity;
import et.gov.customs.profile.service.data.customsOperation.entity.RiskAnalysisResultEntity;
import et.gov.customs.profile.service.data.customsOperation.mapper.PassengerProfileDataAccessMapper;
import et.gov.customs.profile.service.data.customsOperation.mapper.RiskAnalysisResultDataAccessMapper;
import et.gov.customs.profile.service.data.customsOperation.repository.PassengerProfileJpaRepository;
import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.entity.PassengerProfile;
import et.gov.customs.profile.service.domain.ports.output.repository.PassengerProfileRepository;
import et.gov.customs.profile.service.domain.valueObject.OverrideType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PassengerProfileRepositoryImpl implements PassengerProfileRepository {

    private final PassengerProfileJpaRepository passengerProfileJpaRepository;
    private final PassengerProfileDataAccessMapper passengerProfileDataAccessMapper;
    private final RiskAnalysisResultDataAccessMapper riskAnalysisResultDataAccessMapper;

    public PassengerProfileRepositoryImpl(PassengerProfileJpaRepository passengerProfileJpaRepository,
                                          PassengerProfileDataAccessMapper passengerProfileDataAccessMapper,
                                          RiskAnalysisResultDataAccessMapper riskAnalysisResultDataAccessMapper) {
        this.passengerProfileJpaRepository = passengerProfileJpaRepository;
        this.passengerProfileDataAccessMapper = passengerProfileDataAccessMapper;
        this.riskAnalysisResultDataAccessMapper = riskAnalysisResultDataAccessMapper;
    }

    @Override
    public List<PassengerProfile> getByDocumentNumber(String documentNUmber) {
       List<PassengerProfileEntity> passengerProfileEntities =
           passengerProfileJpaRepository.findAllByTravelDocuments(documentNUmber).get();
        if(passengerProfileEntities.isEmpty()){
            return null;
        }
        return passengerProfileEntities.stream()
            .map(passengerProfileEntity ->
                passengerProfileDataAccessMapper.passengerProfileEntityToPassengerProfile(passengerProfileEntity))
            .collect(Collectors.toList());
    }

    @Override
    public List<PassengerProfile> getByBaggageId(String baggageId) {
        List<PassengerProfileEntity> passengerProfileEntities =
            passengerProfileJpaRepository.findAllByBaggageId(baggageId).get();
        if(passengerProfileEntities.isEmpty()){
            return null;
        }
        return passengerProfileEntities.stream()
            .map(passengerProfileEntity ->
                passengerProfileDataAccessMapper.passengerProfileEntityToPassengerProfile(passengerProfileEntity))
            .collect(Collectors.toList());
    }

    @Override
    public List<PassengerProfile> getPassengerByFlightNumberAndDocumentNumber(String flightNumber,
                                                                              String documentNumber) {

        Optional<List<PassengerProfileEntity>> optionalPassengerProfileEntities =
            passengerProfileJpaRepository.findPassengerByDocumentNumberAndFlight(flightNumber, documentNumber);

        if(optionalPassengerProfileEntities.isEmpty()) {
            return null;
        }
        return optionalPassengerProfileEntities.get().stream()
            .map(passengerProfileEntity ->
                passengerProfileDataAccessMapper.passengerProfileEntityToPassengerProfile(passengerProfileEntity))
            .collect(Collectors.toList());

    }

    @Override
    public List<PassengerProfile> getPassengerByCustomsOperationTrackingIdAndDocumentNumber(
        String customsOperationTrackingId, String documentNumber) {
        Optional<List<PassengerProfileEntity>> optionalPassengerProfileEntities =
            passengerProfileJpaRepository.findPassengerByCustomsOperationTrackingIdAndDocumentNumber(
                customsOperationTrackingId, documentNumber);

        if(optionalPassengerProfileEntities.isEmpty()) {
            return null;
        }
        return optionalPassengerProfileEntities.get().stream()
            .map(passengerProfileEntity ->
                passengerProfileDataAccessMapper.passengerProfileEntityToPassengerProfile(passengerProfileEntity))
            .collect(Collectors.toList());
    }

    @Override
    public void setRiskAnalysisResult(PassengerProfile passengerProfile) {
        PassengerProfileEntity passengerProfileEntity = passengerProfileJpaRepository.getReferenceById(
            passengerProfile.getId().getValue());

        if (passengerProfileEntity== null) {
            return ;
        }

        if(!(passengerProfile.getRiskComment() == null)){
            passengerProfileEntity.setOverrideType(OverrideType.RANDOM_SELECTION);
        }
        passengerProfileEntity.setRiskComment(passengerProfile.getRiskComment());
        passengerProfileEntity.setRiskLevel(passengerProfile.getRiskLevel());
        passengerProfileEntity.setRiskValue(passengerProfile.getRiskValue());

        List<RiskAnalysisResultEntity> riskAnalysisResults = passengerProfile.getRiskAnalysisResults()
            .stream()
            .map(riskAnalysisResult ->
                riskAnalysisResultDataAccessMapper.riskAnalysisResultToRiskAnalysisResultEntity(riskAnalysisResult)
            ).collect(Collectors.toList());

        riskAnalysisResults.forEach(r -> {
            r.setPassengerProfile(passengerProfileEntity);
            r.setId(UUID.randomUUID());
        });

        passengerProfileEntity.setRiskAnalysisResults(
            riskAnalysisResults
        );

        passengerProfileJpaRepository.save(passengerProfileEntity);
    }

    @Override
    public void reroutePassengerProfile(PassengerProfile passengerProfile) {
        PassengerProfileEntity passengerProfileEntity = passengerProfileJpaRepository.getReferenceById(
            passengerProfile.getId().getValue());

        if (passengerProfileEntity == null) {
            return ;
        }

        passengerProfileEntity.setRerouteComment(passengerProfile.getRerouteComment());
        passengerProfileEntity.setOverrideType(passengerProfile.getOverrideType());

        passengerProfileJpaRepository.save(passengerProfileEntity);
    }

    @Override
    public void markLeftOutPassengerBaggage(PassengerProfile passengerProfile) {
        PassengerProfileEntity passengerProfileEntity = passengerProfileJpaRepository.getReferenceById(
            passengerProfile.getId().getValue());

        UUID lostBaggageId = passengerProfile.getPassengerBags().get(0).getId().getValue();
        if (passengerProfileEntity == null) {
            return ;
        }

        passengerProfileEntity.getPassengerBags().forEach((passengerBag) -> {
            if(passengerBag.getId().equals(lostBaggageId)) {
                passengerBag.setLeftOut(true);
            }
        });

        passengerProfileJpaRepository.save(passengerProfileEntity);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesByTransportationNumberInNumber(  startDate,
                endDate,transportationNumber);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber, String riskLevel) {
        return passengerProfileJpaRepository.findPassengerProfilesByTransportationNumberInDetail(  startDate,
                endDate,transportationNumber, riskLevel);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInNumber(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByTransportationDateInNumber(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInDetail(Date startDate, Date endDate, String riskLevel) {
        return passengerProfileJpaRepository.findPassengerProfilesByTransportationDateInDetail(  startDate,
                endDate, riskLevel);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByTransportationNumberInNumber(  startDate,
                endDate, transportationNumber);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByTransportationNumberInDetail(  startDate,
                endDate, transportationNumber);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationDateInNumber(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByTransportationDateInNumber(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByDepartureCountryDateInNumber(Date startDate, Date endDate, String departureCountry) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByDepartureCountryDateInNumber(  startDate,
                endDate, departureCountry);
    }



    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(  startDate,
                endDate, transportationNumber);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(  startDate,
                endDate, transportationNumber);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaInDetail(Date startDate, Date endDate, String documentNumber, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesByRiskCriteriaInDetail(  startDate,
                endDate, documentNumber, transportationNumber);
    }



    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaFrequencyInNumber(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByRiskCriteriaFrequencyInNumber(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaSelectivityInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileJpaRepository.findPassengerProfilesByRiskCriteriaSelectivityInDetail(  startDate,
                endDate, transportationNumber);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskLevelFrequencyInNumber(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByRiskLevelFrequencyInNumber(  startDate,
                endDate);
    }


    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskSelectivityLevelInDetail(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByRiskSelectivityLevelInDetail(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findSummaryOfFrequencyRiskLevel(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findSummaryOfFrequencyRiskLevel(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findSummaryManuallyRerouted(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findSummaryManuallyRerouted(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findSelectRiskSummaryRerouted(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findSelectRiskSummaryRerouted(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findReroutedRandomSelection(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findReroutedRandomSelection(  startDate,
                endDate);
    }

    @Override
    public List<ReportPassengerRiskDTO> findRiskLevelByGender() {
        return passengerProfileJpaRepository.findRiskLevelByGender( );
    }

    @Override
    public List<ReportPassengerRiskDTO> findRiskLevelByAgeGroup() {
        return passengerProfileJpaRepository.findRiskLevelByAgeGroup( );
    }
}
