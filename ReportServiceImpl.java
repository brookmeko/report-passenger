package et.gov.customs.profile.service.services;


import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.ports.output.repository.ReportPassengerProfileService;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportPassengerProfileService {

    private final ReportCommandHandler reportCommandHandler;

    public ReportServiceImpl(ReportCommandHandler reportCommandHandler) {
        this.reportCommandHandler = reportCommandHandler;

    }


    private ResponseEntity<byte[]> generateReport(List<ReportPassengerRiskDTO> reportList, String filePath){
        try {
            Locale locale = new Locale("am"); // Amharic

            ClassPathResource resource = new ClassPathResource(filePath);

            JasperReport jasperReport = null;

            jasperReport = JasperCompileManager.compileReport(resource.getInputStream());


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, locale);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Export the report to PDF stream with UTF-8 encoding and Amharic font
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            outputStream.close();

            byte[] reportBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(reportBytes.length);

            return ResponseEntity.ok().headers(headers).body(reportBytes);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationNumberInNumber( String startDate, String endDate, String transportationNumber,String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByTransportationNumberInNumber(sDate, eDate,transportationNumber);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfileByTransportationNumberInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfileByTransportationNumberInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfileByTransportationNumberInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationNumberInDetail( String startDate, String endDate, String transportationNumber, String riskLevel)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByTransportationNumberInDetail(sDate, eDate,transportationNumber, riskLevel);

        String filePath = "report/rptPassengerProfilesByTransportationNumberInDetail.jrxml";


        return generateReport(reportList,filePath);

    }


    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationDateInNumber( String startDate, String endDate, String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByTransportationDateInNumber(sDate, eDate);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfileByTransportationDateInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfileByTransportationDateInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfileByTransportationDateInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByTransportationDateInDetail( String startDate, String endDate, String riskLevel)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByTransportationDateInDetail(sDate, eDate, riskLevel);

        String filePath = "report/rptPassengerProfilesByTransportationDateInDetail.jrxml";


        return generateReport(reportList,filePath);

    }



    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationNumberInNumber( String startDate, String endDate, String transportationNumber,String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByTransportationNumberInNumber(sDate, eDate,transportationNumber);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationNumberInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationNumberInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationNumberInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationNumberInDetail( String startDate, String endDate, String transportationNumber)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByTransportationNumberInDetail(sDate, eDate,transportationNumber);

        String filePath = "report/rptPassengerProfilesOffenceByTransportationNumberInDetail.jrxml";


        return generateReport(reportList,filePath);

    }



    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByTransportationDateInNumber( String startDate, String endDate, String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByTransportationDateInNumber(sDate, eDate);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationDateInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationDateInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesOffenceByTransportationDateInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }


    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByDepartureCountryDateInNumber( String startDate, String endDate, String departureCountry, String reportFormat )
    {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());



        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByDepartureCountryDateInNumber(sDate, eDate, departureCountry);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesOffenceByDepartureCountryDateInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesOffenceByDepartureCountryDateInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesOffenceByDepartureCountryDateInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }



    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber( String startDate, String endDate, String transportationNumber,String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(sDate, eDate,transportationNumber);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesOffenceByRandomSelectionCriteriaInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesOffenceByRandomSelectionCriteriaInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesOffenceByRandomSelectionCriteriaInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail( String startDate, String endDate, String transportationNumber)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(sDate, eDate,transportationNumber);

        String filePath = "report/rptPassengerProfilesOffenceByRandomSelectionCriteriaInDetail.jrxml";


        return generateReport(reportList,filePath);

    }




    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaInDetail( String startDate, String endDate, String documentNumber, String transportationNumber)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByRiskCriteriaInDetail(sDate, eDate, documentNumber, transportationNumber);

        String filePath = "report/rptPassengerProfilesByRiskCriteriaInDetail.jrxml";


        return generateReport(reportList,filePath);

    }



    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaFrequencyInNumber( String startDate, String endDate, String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByRiskCriteriaFrequencyInNumber(sDate, eDate);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesByRiskCriteriaFrequencyInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesByRiskCriteriaFrequencyInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesByRiskCriteriaFrequencyInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }


    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByRiskCriteriaSelectivityInDetail( String startDate, String endDate, String transportationNumber)
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByRiskCriteriaSelectivityInDetail(sDate, eDate, transportationNumber);

        String filePath = "report/rptPassengerProfilesByRiskCriteriaSelectivityInDetail.jrxml";


        return generateReport(reportList,filePath);

    }


    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByRiskLevelFrequencyInNumber( String startDate, String endDate, String reportFormat )
    {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByRiskLevelFrequencyInNumber(sDate, eDate);

        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesByRiskLevelFrequencyInNumberTable.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesByRiskLevelFrequencyInNumberChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesByRiskLevelFrequencyInNumberTableAndChart.jrxml";
        }

        return generateReport(reportList,filePath);

    }



    @Override
    @SneakyThrows
    public    List<ReportPassengerRiskDTO>  passengersProfileByTransportationDateInNumberForDashboard(String startDate, String endDate) {
        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.passengerProfilesByTransportationDateInNumberForDashboard(sDate, eDate);
        return  reportList;
    }

    @Override
    @SneakyThrows
    public List<ReportPassengerRiskDTO> passengerProfilesOffenceByTransportationDateInNumberForDashboard(String startDate, String endDate) {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());

        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.passengerProfilesOffenceByTransportationDateInNumberForDashboard(sDate, eDate);
        return  reportList;
    }

    @Override
    @SneakyThrows
    public List<ReportPassengerRiskDTO> passengerProfilesByRiskLevelFrequencyInNumberForDashboard(String startDate, String endDate) {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());

        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.passengerProfilesByRiskLevelFrequencyInNumberForDashboard(sDate, eDate);
        return  reportList;
    }

    @Override
    @SneakyThrows
    public List<ReportPassengerRiskDTO> passengerProfilesByFrequencyOfEachCriteriaForDashboard(String startDate, String endDate) {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());

        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.passengerProfilesByFrequencyOfEachCriteriaForDashboard(sDate, eDate);
        return  reportList;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByFrequencyOfEachCriteria(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByFrequencyOfEachCriteria(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteria.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteriaChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteriaChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);


    }

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByRiskSelectivityLevelInDetail(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByRiskSelectivityLevelInDetail(sDate, eDate);

        String filePath = "report/rptPassengerProfilesByRiskSelectivityLevelInDetail.jrxml";


        return generateReport(reportList,filePath);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findSummaryOfFrequencyRiskLevel(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findSummaryOfFrequencyRiskLevel(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptSummaryOfFrequencyRiskLevel.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptSummaryOfFrequencyRiskLevelChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptSummaryOfFrequencyRiskLevelChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findSummaryManuallyRerouted(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findSummaryManuallyRerouted(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptSummaryManuallyReroutedTable.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptSummaryManuallyReroutedChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptSummaryManuallyReroutedChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findSelectRiskSummaryRerouted(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findSelectRiskSummaryRerouted(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfileBySelectRiskSummaryRerouted.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptSummaryManuallyReroutedChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptSummaryManuallyReroutedChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);

    }


    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findReroutedRandomSelection(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findReroutedRandomSelection(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfileReroutedRandomSelection.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptSummaryManuallyReroutedChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptSummaryManuallyReroutedChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);

    }

    @Override
    @SneakyThrows
    public List<ReportPassengerRiskDTO> findRiskLevelByGender() {

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findRiskLevelByGender();
        return  reportList;
    }

    @Override
    @SneakyThrows
    public List<ReportPassengerRiskDTO> findRiskLevelByAgeGroup() {

        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findRiskLevelByAgeGroup();
        return  reportList;
    }


}
