package et.gov.customs.profile.service.services;


import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ReportCommandHandler {

    private final ReportReceivedHelper reportReceivedHelper;


    public ReportCommandHandler(ReportReceivedHelper reportReceivedHelper) {
        this.reportReceivedHelper = reportReceivedHelper;

    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesByTransportationNumberInNumber(  startDate,
                endDate,transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber, String riskLevel) {
        return reportReceivedHelper.findPassengerProfilesByTransportationNumberInDetail(  startDate,
                endDate,transportationNumber, riskLevel);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInNumber(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByTransportationDateInNumber(  startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInDetail(Date startDate, Date endDate, String riskLevel) {
        return reportReceivedHelper.findPassengerProfilesByTransportationDateInDetail(  startDate,
                endDate, riskLevel);
    }



    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInNumber(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesOffenceByTransportationNumberInNumber(  startDate,
                endDate, transportationNumber);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInDetail(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesOffenceByTransportationNumberInDetail(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationDateInNumber(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesOffenceByTransportationDateInNumber(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByDepartureCountryDateInNumber(Date startDate, Date endDate, String departureCountry) {
        return reportReceivedHelper.findPassengerProfilesOffenceByDepartureCountryDateInNumber(  startDate,
                endDate, departureCountry);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaInDetail(Date startDate, Date endDate, String documentNumber, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesByRiskCriteriaInDetail(  startDate,
                endDate, documentNumber, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaFrequencyInNumber(Date startDate, Date endDate){
        return reportReceivedHelper.findPassengerProfilesByRiskCriteriaFrequencyInNumber(startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaSelectivityInDetail(Date startDate, Date endDate, String transportationNumber) {
        return reportReceivedHelper.findPassengerProfilesByRiskCriteriaSelectivityInDetail(  startDate,
                endDate, transportationNumber);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskLevelFrequencyInNumber(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByRiskLevelFrequencyInNumber(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }


    public List<ReportPassengerRiskDTO> findPassengerProfilesByRiskSelectivityLevelInDetail(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByRiskSelectivityLevelInDetail(  startDate,
                endDate);
    }

    // Dashboard

    public List<ReportPassengerRiskDTO> findSummaryOfFrequencyRiskLevel(Date startDate, Date endDate) {
        return reportReceivedHelper.findSummaryOfFrequencyRiskLevel(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findSummaryManuallyRerouted(Date startDate, Date endDate) {
        return reportReceivedHelper.findSummaryManuallyRerouted(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findSelectRiskSummaryRerouted(Date startDate, Date endDate) {
        return reportReceivedHelper.findSelectRiskSummaryRerouted(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findReroutedRandomSelection(Date startDate, Date endDate) {
        return reportReceivedHelper.findReroutedRandomSelection(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> passengerProfilesByTransportationDateInNumberForDashboard(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByTransportationDateInNumber(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> passengerProfilesOffenceByTransportationDateInNumberForDashboard(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesOffenceByTransportationDateInNumber(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> passengerProfilesByRiskLevelFrequencyInNumberForDashboard(Date startDate, Date endDate) {
        return reportReceivedHelper.findSummaryOfFrequencyRiskLevel(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> passengerProfilesByFrequencyOfEachCriteriaForDashboard(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }

    public List<ReportPassengerRiskDTO> findRiskLevelByGender() {
        return reportReceivedHelper.findRiskLevelByGender( );
    }

    public List<ReportPassengerRiskDTO> findRiskLevelByAgeGroup() {
        return reportReceivedHelper.findRiskLevelByAgeGroup( );
    }

}


