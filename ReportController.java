package et.gov.customs.profile.service.application.controllers;

import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.ports.output.repository.ReportPassengerProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/customsOperations/passenger/report", produces = "application/vnd.api.v1+json")
public class ReportController {

    private final ReportPassengerProfileService reportPassengerProfileService;

    public ReportController(ReportPassengerProfileService reportPassengerProfileService) {

        this.reportPassengerProfileService = reportPassengerProfileService;
    }


    @GetMapping("/transportationNumber/inNumber/{startDate}/{endDate}/{transportationNumber}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationNumberInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesByTransportationNumberInNumber(startDate,endDate,transportationNumber,reportFormat);
    }



    @GetMapping("/transportationNumber/inDetail/{startDate}/{endDate}/{transportationNumber}/{riskLevel}")
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationNumberInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber,
            @PathVariable String riskLevel) {


        return reportPassengerProfileService.findPassengerProfilesByTransportationNumberInDetail(startDate,endDate,transportationNumber, riskLevel);
    }


    @GetMapping("/transportationDate/inNumber/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationDateInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesByTransportationDateInNumber(startDate,endDate,reportFormat);
    }



    @GetMapping("/transportationDate/inDetail/{startDate}/{endDate}/{riskLevel}")
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationDateInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String riskLevel) {


        return reportPassengerProfileService.findPassengerProfilesByTransportationDateInDetail(startDate,endDate, riskLevel);
    }


    @GetMapping("/offence/transportationNumber/inNumber/{startDate}/{endDate}/{transportationNumber}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationNumberInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByTransportationNumberInNumber(startDate,endDate,transportationNumber,reportFormat);
    }



    @GetMapping("/offence/transportationNumber/inDetail/{startDate}/{endDate}/{transportationNumber}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationNumberInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByTransportationNumberInDetail(startDate,endDate,transportationNumber);
    }



    @GetMapping("/offence/transportationDate/inNumber/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationDateInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByTransportationDateInNumber(startDate,endDate,reportFormat);
    }



    @GetMapping("/offence/departureCountry/transportationDate/inNumber/{startDate}/{endDate}/{departureCountry}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByDepartureCountryDateInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String departureCountry,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByDepartureCountryDateInNumber(startDate,endDate,departureCountry, reportFormat);
    }



    @GetMapping("/randomSelection/offence/transportationNumber/inNumber/{startDate}/{endDate}/{transportationNumber}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(startDate,endDate,transportationNumber,reportFormat);
    }


    @GetMapping("/randomSelection/offence/transportationNumber/inDetail/{startDate}/{endDate}/{transportationNumber}")
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber) {


        return reportPassengerProfileService.findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(startDate,endDate,transportationNumber);
    }




    @GetMapping("/riskCriteria/transportationNumber/inDetail/{startDate}/{endDate}/{documentNumber}/{transportationNumber}")
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String documentNumber,
            @PathVariable  String transportationNumber) {


        return reportPassengerProfileService.findPassengerProfilesByRiskCriteriaInDetail(startDate,endDate, documentNumber, transportationNumber);
    }


    @GetMapping("/riskCriteria/frequency/inNumber/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaFrequencyInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {


        return reportPassengerProfileService.findPassengerProfilesByRiskCriteriaFrequencyInNumber(startDate,endDate, reportFormat);
    }



    @GetMapping("/riskCriteria/selectivity/transportationNumber/inDetail/{startDate}/{endDate}/{transportationNumber}")
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaSelectivityInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable  String transportationNumber) {

        return reportPassengerProfileService.findPassengerProfilesByRiskCriteriaSelectivityInDetail(startDate, endDate, transportationNumber);
    }


    @GetMapping("/riskLevel/frequency/inNumber/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByRiskLevelFrequencyInNumber(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findPassengerProfilesByRiskLevelFrequencyInNumber(startDate,endDate, reportFormat);
    }

    @GetMapping("/riskLevel/frequencyOfEachCriteria/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByFrequencyOfEachCriteria(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findPassengerProfilesByFrequencyOfEachCriteria(startDate,endDate, reportFormat);
    }

    @GetMapping("/riskLevel/selectivityLevelInDetail/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByRiskSelectivityLevelInDetail(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findPassengerProfilesByRiskSelectivityLevelInDetail(startDate,endDate, reportFormat);
    }


    @GetMapping("/summary/frequencyRiskLevel/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findSummaryOfFrequencyRiskLevel(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findSummaryOfFrequencyRiskLevel(startDate,endDate, reportFormat);
    }

    @GetMapping("/rerouted/frequencyRiskLevel/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findSummaryManuallyRerouted(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findSummaryManuallyRerouted(startDate,endDate, reportFormat);
    }

    @GetMapping("/rerouted/selectRisk/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findSelectRiskSummaryRerouted(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findSelectRiskSummaryRerouted(startDate,endDate, reportFormat);
    }

    @GetMapping("/rerouted/randomSelection/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findReroutedRandomSelection(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findReroutedRandomSelection(startDate,endDate, reportFormat);
    }

    @GetMapping("/transportationDate/inNumber/dashboard/{startDate}/{endDate}")
    public ResponseEntity<List<ReportPassengerRiskDTO> > passengerProfilesByTransportationDateInNumberForDashboard(
            @PathVariable String startDate,
            @PathVariable String endDate) {

        return ResponseEntity.ok(reportPassengerProfileService
                .passengersProfileByTransportationDateInNumberForDashboard(startDate,endDate));
    }

    @GetMapping("/offence/transportationDate/inNumber/dashboard/{startDate}/{endDate}")
    public ResponseEntity<List<ReportPassengerRiskDTO>> passengerProfilesOffenceByTransportationDateInNumberForDashboard(
            @PathVariable String startDate,
            @PathVariable String endDate) {

        return ResponseEntity.ok(reportPassengerProfileService
                .passengerProfilesOffenceByTransportationDateInNumberForDashboard(startDate,endDate));
    }

    @GetMapping("/riskCriteria/frequency/inNumber/dashboard/{startDate}/{endDate}")
    public ResponseEntity<List<ReportPassengerRiskDTO>> passengerProfilesByRiskLevelFrequencyForDashboard(
            @PathVariable String startDate,
            @PathVariable String endDate) {

        return ResponseEntity.ok(reportPassengerProfileService
                .passengerProfilesByRiskLevelFrequencyInNumberForDashboard(startDate,endDate));
    }

    @GetMapping("/riskCriteria/frequencyOfEachCriteria/inNumber/dashboard/{startDate}/{endDate}")
    public ResponseEntity<List<ReportPassengerRiskDTO>> passengerProfilesByFrequencyOfEachCriteriaForDashboard(
            @PathVariable String startDate,
            @PathVariable String endDate) {

        return ResponseEntity.ok(reportPassengerProfileService
                .passengerProfilesByFrequencyOfEachCriteriaForDashboard(startDate,endDate));
    }

    @GetMapping("/dashboard/getByGender")
    public ResponseEntity<List<ReportPassengerRiskDTO>> findByGender() {

        return ResponseEntity.ok(reportPassengerProfileService.findRiskLevelByGender());
    }

    @GetMapping("/dashboard/getByAgeGroup")
    public ResponseEntity<List<ReportPassengerRiskDTO>> findRiskLevelByAgeGroup() {

        return ResponseEntity.ok(reportPassengerProfileService.findRiskLevelByAgeGroup());
    }
}
