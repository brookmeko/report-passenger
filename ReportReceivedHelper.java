package et.gov.customs.profile.service.services;
import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.ports.output.repository.PassengerProfileRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ReportReceivedHelper {

    private final PassengerProfileRepository passengerProfileRepository;

    public ReportReceivedHelper(PassengerProfileRepository passengerProfileRepository) {
        this.passengerProfileRepository = passengerProfileRepository;
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesByTransportationNumberInNumber(  startDate,
                endDate,transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber, String riskLevel) {
        return passengerProfileRepository.findPassengerProfilesByTransportationNumberInDetail(  startDate,
                endDate,transportationNumber, riskLevel);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInNumber(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesByTransportationDateInNumber(  startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInDetail(Date startDate, Date endDate, String riskLevel) {
        return passengerProfileRepository.findPassengerProfilesByTransportationDateInDetail(  startDate,
                endDate, riskLevel);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesOffenceByTransportationNumberInNumber(  startDate,
                endDate, transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesOffenceByTransportationNumberInDetail(  startDate,
                endDate, transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationDateInNumber(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesOffenceByTransportationDateInNumber(  startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByDepartureCountryDateInNumber(Date startDate, Date endDate, String departureCountry) {
        return passengerProfileRepository.findPassengerProfilesOffenceByDepartureCountryDateInNumber(  startDate,
                endDate, departureCountry);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaInDetail(Date startDate, Date endDate, String documentNumber, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesByRiskCriteriaInDetail(  startDate,
                endDate, documentNumber, transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaFrequencyInNumber(Date startDate, Date endDate){
        return passengerProfileRepository.findPassengerProfilesByRiskCriteriaFrequencyInNumber(startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaSelectivityInDetail(Date startDate, Date endDate, String transportationNumber) {
        return passengerProfileRepository.findPassengerProfilesByRiskCriteriaSelectivityInDetail(  startDate,
                endDate, transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskLevelFrequencyInNumber(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesByRiskLevelFrequencyInNumber(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskSelectivityLevelInDetail(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesByRiskSelectivityLevelInDetail(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findSummaryOfFrequencyRiskLevel(Date startDate, Date endDate) {
        return passengerProfileRepository.findSummaryOfFrequencyRiskLevel(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findSummaryManuallyRerouted(Date startDate, Date endDate) {
        return passengerProfileRepository.findSummaryManuallyRerouted(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findSelectRiskSummaryRerouted(Date startDate, Date endDate) {
        return passengerProfileRepository.findSelectRiskSummaryRerouted(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findReroutedRandomSelection(Date startDate, Date endDate) {
        return passengerProfileRepository.findReroutedRandomSelection(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findRiskLevelByGender() {
        return passengerProfileRepository.findRiskLevelByGender();
    }

    public List<ReportPassengerRiskDTO> findRiskLevelByAgeGroup() {
        return passengerProfileRepository.findRiskLevelByAgeGroup();
    }

}
