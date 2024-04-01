package et.gov.customs.profile.service.data.customsOperation.repository;

import et.gov.customs.profile.service.data.customsOperation.entity.CustomsOperationEntity;
import et.gov.customs.profile.service.data.customsOperation.entity.PassengerProfileEntity;
import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public interface PassengerProfileJpaRepository extends JpaRepository<PassengerProfileEntity, UUID> {
    Optional<List<PassengerProfileEntity>> findAllByTravelDocuments(String documentNumber);

    Optional<List<PassengerProfileEntity>> findAllByCustomsOperation(CustomsOperationEntity customsOperation);

    @Query("SELECT p FROM PassengerProfileEntity p LEFT JOIN FETCH p.passengerBags b WHERE b.bagId = :bagId")
    Optional<List<PassengerProfileEntity>> findAllByBaggageId(@Param("bagId") String bagId);

    @Query("SELECT p FROM PassengerProfileEntity p " +
        "LEFT JOIN FETCH p.customsOperation c " +
        "LEFT JOIN FETCH c.transportation t " +
        "LEFT JOIN FETCH p.travelDocuments td " +
        " WHERE t.transportationNumber = :flightNumber AND td.documentNumber = :documentNumber")
    Optional<List<PassengerProfileEntity>> findPassengerByDocumentNumberAndFlight(
        @Param("flightNumber") String flightNumber,@Param("documentNumber") String documentNumber
    );

    @Query("SELECT p FROM PassengerProfileEntity p " +
        "LEFT JOIN FETCH p.customsOperation c " +
        "LEFT JOIN FETCH p.travelDocuments td " +
        " WHERE c.customsOperationTrackingId = :customsOperationTrackingId AND td.documentNumber = :documentNumber")
    Optional<List<PassengerProfileEntity>> findPassengerByCustomsOperationTrackingIdAndDocumentNumber(
        @Param("customsOperationTrackingId") String customsOperationTrackingId,
        @Param("documentNumber") String documentNumber
    );



    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.transportations t, custom_profile_local.customs_operations c " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.risk_level is not null and t.transportation_number =:transportationNumber and  " +
            " t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByTransportationNumberInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInNumber(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByTransportationNumberInNumber(startDate.toString(), endDate.toString(), transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
             reportResult.setRiskLevel((String) row[0]);
             reportResult.setTransportationType((String) row[1]);
             reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

             reportResult.setCreatedDate(sDate);
             reportResult.setDepartFrom((String) row[4]);
             reportResult.setArriveTo((String) row[5]);
             reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = " select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name, " +
            "       p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and p.risk_level is not null and t.transportation_number =:transportationNumber and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and " +
            "        p.risk_level>=:riskLevel ", nativeQuery = true)

    List<Object[]> getPassengerProfilesByTransportationNumberInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("transportationNumber") String transportationNumber,
            @Param("riskLevel") String riskLevel
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationNumberInDetail(
            Date startDate,
            Date endDate,
            String transportationNumber,
            String riskLevel
    ) {

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByTransportationNumberInDetail(startDate.toString(), endDate.toString(), transportationNumber, riskLevel);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
             reportResult.setRiskLevel((String) row[0]);
             reportResult.setTransportationType((String) row[1]);
             reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

             reportResult.setCreatedDate(sDate);
             reportResult.setDepartFrom((String) row[4]);
             reportResult.setArriveTo((String) row[5]);
             reportResult.setFirstName((String) row[6]);
             reportResult.setMiddleName((String) row[7]);
             reportResult.setLastName((String) row[8]);
             reportResult.setGender((String) row[9]);

             formattedDate = targetDateFormat.format(row[10]);
             sDate = Timestamp.valueOf(formattedDate);
             reportResult.setBirthDate(sDate);
             reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }





    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.transportations t, custom_profile_local.customs_operations c " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id  and p.risk_level is not null and " +
            "      t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByTransportationDateInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInNumber(
            Date startDate,
            Date endDate
    ) {

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByTransportationDateInNumber(startDate.toString(), endDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {

            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);
            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name, " +
            "       p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id  and p.id = ptd.passenger_profile_id and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and " +
            "        p.risk_level>=:riskLevel ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByTransportationDateInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("riskLevel") String riskLevel
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByTransportationDateInDetail(
            Date startDate,
            Date endDate,
            String riskLevel
    ) {

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByTransportationDateInDetail(startDate.toString(), endDate.toString(), riskLevel);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setFirstName((String) row[6]);
            reportResult.setMiddleName((String) row[7]);
            reportResult.setLastName((String) row[8]);
            reportResult.setGender((String) row[9]);

            formattedDate = targetDateFormat.format(row[10]);
            sDate = Timestamp.valueOf(formattedDate);
            reportResult.setBirthDate(sDate);
            reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }



    //// Report by offence record by transportation number

    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, c.transportation_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and " +
            "        co.id=a.country_id and c.depart_from= co.country_code and co.country_name=e.departure_country  and p.id = ptd.passenger_profile_id and ptd.document_number=e.passport_number and " +
            "        t.transportation_number =:transportationNumber and " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and " +
            "        c.transportation_date>=:startDate and c.transportation_date<=:endDate " +
            "group by p.risk_level, t.transportation_type, t.transportation_number, c.transportation_date, c.depart_from, c.arrive_to ", nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByTransportationNumberInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInNumber(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByTransportationNumberInNumber(startDate.toString(), endDate.toString(), entryStartDate.toString(), entryEndDate.toString(),  transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name, " +
            "       p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and " +
            "        co.id=a.country_id and c.depart_from= co.country_code and co.country_name=e.departure_country and ptd.document_number=e.passport_number and " +
            "        t.transportation_number =:transportationNumber and " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and " +
            "        c.transportation_date>=:startDate and c.transportation_date<=:endDate   ", nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByTransportationNumberInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationNumberInDetail(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByTransportationNumberInDetail(startDate.toString(), endDate.toString(),entryStartDate.toString(),entryEndDate.toString(),   transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setFirstName((String) row[6]);
            reportResult.setMiddleName((String) row[7]);
            reportResult.setLastName((String) row[8]);
            reportResult.setGender((String) row[9]);

            formattedDate = targetDateFormat.format(row[10]);
            sDate = Timestamp.valueOf(formattedDate);
            reportResult.setBirthDate(sDate);
            reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }




    //// Report by offence record by transportation date

    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and " +
            "        co.id=a.country_id and c.depart_from=co.country_code and co.country_name=e.departure_country and ptd.document_number=e.passport_number and " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to ", nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByTransportationDateInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByTransportationDateInNumber(
            Date startDate,
            Date endDate
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByTransportationDateInNumber(startDate.toString(), endDate.toString(), entryStartDate.toString(), entryEndDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }




    ////////////// Date based passenger profile offence report by a particular departure country ///////////////////////////

    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and " +
            "        co.id=a.country_id and c.depart_from=co.country_code and co.country_name=e.departure_country and ptd.document_number=e.passport_number and " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and " +
            "      e.departure_country=:departureCountry  " +
            "group by p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to", nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByDepartureCountryDateInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate,
            @Param("departureCountry") String departureCountry

            );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByDepartureCountryDateInNumber(
            Date startDate,
            Date endDate,
            String  departureCountry
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByDepartureCountryDateInNumber(startDate.toString(), endDate.toString(), entryStartDate.toString(), entryEndDate.toString(), departureCountry);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// Passenger profile offence report by random selection criteria ///////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, count(p.risk_level) total_count,p.risk_comment, ptd.document_number  " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c,  " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e  " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and  " +
            "        co.id=a.country_id and c.depart_from=co.country_code and co.country_name=e.departure_country and ptd.document_number=e.passport_number and  " +
            "        t.transportation_number =:transportationNumber and  " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and  " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and  " +
            "        p.risk_comment= 'RANDOM_CONDITION'  " +
            "group by p.risk_level, p.risk_comment, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, ptd.document_number ", nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByRandomSelectionCriteriaInNumber(startDate.toString(), endDate.toString(), entryStartDate.toString(), entryEndDate.toString(),  transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setNumberOfOccurrence(((Number) row[6]).intValue());
            reportResult.setRiskComment((String) row[7]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name, " +
            "       p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number, p.risk_comment " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c,  " +
            "     custom_lookup_local.airport a, custom_lookup_local.country co, custom_analysis_local.ecms_profile e  " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and p.id = ptd.passenger_profile_id and  " +
            "        co.id=a.country_id and c.depart_from=co.country_code and co.country_name=e.departure_country and ptd.document_number=e.passport_number and  " +
            "        t.transportation_number =:transportationNumber and  " +
            "        e.entry_date>=:entryStartDate and e.entry_date<=:entryEndDate and  " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and  " +
            "        p.risk_comment= 'RANDOM_CONDITION'  " , nativeQuery = true)
    List<Object[]> getPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("entryStartDate") String entryStartDate,
            @Param("entryEndDate") String entryEndDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesOffenceByRandomSelectionCriteriaInDetail(startDate.toString(), endDate.toString(),entryStartDate.toString(), entryEndDate.toString(),  transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setFirstName((String) row[6]);
            reportResult.setMiddleName((String) row[7]);
            reportResult.setLastName((String) row[8]);
            reportResult.setGender((String) row[9]);

            formattedDate = targetDateFormat.format(row[10]);
            sDate = Timestamp.valueOf(formattedDate);
            reportResult.setBirthDate(sDate);
            reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setRiskComment((String) row[14]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);
            dtos.add( reportResult);
        }
        return dtos;
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// Passenger profile report by risk criteria ////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query(value = "select p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name, " +
            "       p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number, rs.criteria, rs.weight, " +
            "       rs.analysis, rs.score " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_profile_local.risk_analysis_result rs " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and " +
            "   p.id = rs.passenger_profile_id and  " +
            "        t.transportation_number =:transportationNumber and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate and " +
            "        ptd.document_number=:documentNumber ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByRiskCriteriaInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("documentNumber") String documentNumber,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaInDetail(
            Date startDate,
            Date endDate,
            String documentNumber,
            String transportationNumber
    ) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByRiskCriteriaInDetail(startDate.toString(), endDate.toString(), documentNumber, transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setFirstName((String) row[6]);
            reportResult.setMiddleName((String) row[7]);
            reportResult.setLastName((String) row[8]);
            reportResult.setGender((String) row[9]);

            formattedDate = targetDateFormat.format(row[10]);
            sDate = Timestamp.valueOf(formattedDate);
            reportResult.setBirthDate(sDate);
            reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setCriteria((String) row[14]);
            reportResult.setWeight(((Number) row[15]).intValue());
            reportResult.setAnalysis((String) row[16]);
            reportResult.setScore(((Number) row[17]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);
            dtos.add( reportResult);
        }
        return dtos;
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// Passenger profile report by risk criteria frequency //////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query(value = "select p.risk_level, rs.criteria, count(rs.criteria) frequency " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_profile_local.risk_analysis_result rs " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and " +
            "        p.id = rs.passenger_profile_id  and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level, rs.criteria  ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByRiskCriteriaFrequencyInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaFrequencyInNumber(
            Date startDate,
            Date endDate
    ) {


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByRiskCriteriaFrequencyInNumber(startDate.toString(), endDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);


            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[2]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setCriteria((String) row[1]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// Passenger profile report by risk criteria selectivity ////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query(value = "select  p.risk_level, t.transportation_type, t.transportation_number, t.created_date, c.depart_from, c.arrive_to, p.first_name," +
            "                   p.middle_name, p.last_name, p.gender, p.birth_date, p.birth_place, p.nationality, ptd.document_number, rs.criteria, rs.weight,  " +
            "                   rs.analysis, rs.score  " +
            "            from custom_profile_local.passenger_profiles p, custom_profile_local.passenger_travel_documents ptd," +
            "                 custom_profile_local.transportations t, custom_profile_local.customs_operations c," +
            "                 custom_profile_local.risk_analysis_result rs  " +
            "            where  c.id = p.customs_operation_id and p.id = rs.passenger_profile_id and p.id = ptd.passenger_profile_id and c.id = t.customs_operation_id and" +
            "        t.transportation_number =:transportationNumber and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByRiskCriteriaSelectivityInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("transportationNumber") String transportationNumber
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByRiskCriteriaSelectivityInDetail(
            Date startDate,
            Date endDate,
            String transportationNumber
    ) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date entryStartDate = calendar.getTime();

        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);
        Date entryEndDate = calendar.getTime();

        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByRiskCriteriaSelectivityInDetail(startDate.toString(), endDate.toString(), transportationNumber);

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);
            reportResult.setTransportationType((String) row[1]);
            reportResult.setTransportationNumber((String) row[2]);

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setDepartFrom((String) row[4]);
            reportResult.setArriveTo((String) row[5]);
            reportResult.setFirstName((String) row[6]);
            reportResult.setMiddleName((String) row[7]);
            reportResult.setLastName((String) row[8]);
            reportResult.setGender((String) row[9]);

            formattedDate = targetDateFormat.format(row[10]);
            sDate = Timestamp.valueOf(formattedDate);
            reportResult.setBirthDate(sDate);
            reportResult.setBirthPlace((String) row[11]);
            reportResult.setNationality((String) row[12]);
            reportResult.setDocumentNumber((String) row[13]);
            reportResult.setCriteria((String) row[14]);
            reportResult.setWeight(((Number) row[15]).intValue());

            reportResult.setAnalysis((String) row[16]);
            reportResult.setScore(((Number) row[17]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);
            dtos.add( reportResult);
        }
        return dtos;
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////// Passenger profile report by risk level frequency /////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Query(value = "select p.risk_level, rs.criteria, count(p.risk_level) as frequency " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_profile_local.risk_analysis_result rs " +
            "where  c.id = p.customs_operation_id and c.id = t.customs_operation_id and " +
            "        p.id = rs.passenger_profile_id  and " +
            "        t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level, rs.criteria ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByRiskLevelFrequencyInNumber(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findPassengerProfilesByRiskLevelFrequencyInNumber(
            Date startDate,
            Date endDate
    ) {


        List<Object[]> resultList = null;

        resultList = getPassengerProfilesByRiskLevelFrequencyInNumber(startDate.toString(), endDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);


            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(row[2]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setCriteria((String) row[1]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = "select  p.risk_level, rs.criteria ,COUNT(CASE WHEN rs.score > 0 THEN rs.score END) AS score_count " +
            " from custom_profile_local.passenger_profiles p, " +
            "     custom_profile_local.transportations t, custom_profile_local.customs_operations c, " +
            "     custom_profile_local.risk_analysis_result rs " +
            " where  c.id = p.customs_operation_id and p.id = rs.passenger_profile_id  and c.id = t.customs_operation_id  and " +
            "       t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by  p.risk_level,  rs.criteria ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByFrequencyOfEachCriteria(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    default List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(
            Date startDate,
            Date endDate

    ) {
        List<Object[]> resultList = null;
        resultList = getPassengerProfilesByFrequencyOfEachCriteria(startDate.toString(), endDate.toString());
        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
        int totalRed = 0;
        int totalGreen = 0;
        float totalRedPercentage=0.0f;
        float totalGreenPercentage=0.0f;
        float redPercentage=0.0f;
        float greenPercentage=0.0f;
        for (Object[] row : resultList) {
            ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();

            boolean criteriaFound = false;

            // Check if the criteria already exists in dtos
            for (ReportPassengerRiskDTO existingDTO : dtos) {
                if (existingDTO.getCriteria().equals((String) row[1])) {
                    criteriaFound = true;
                    if ("RED".equals(row[0])) {
                        existingDTO.setRedFrequency(((Number) row[2]).intValue());
                    } else if ("GREEN".equals(row[0])) {
                        existingDTO.setGreenFrequency( ((Number) row[2]).intValue());
                    }
                    // Update any other fields if needed
                    break;
                }
            }
            // If criteria is not found, set the frequencies and add to the list
            if (!criteriaFound) {
                reportResult.setCriteria((String) row[1]);
                if ("RED".equals(row[0])) {
                    reportResult.setRedFrequency(((Number) row[2]).intValue());
                } else if ("GREEN".equals(row[0])) {
                    reportResult.setGreenFrequency(((Number) row[2]).intValue());
                }
                // Set any other fields if needed
                dtos.add(reportResult);
            }
            totalRed += reportResult.getRedFrequency();
            totalGreen += reportResult.getGreenFrequency();
        }
        // update percentage
        for (ReportPassengerRiskDTO existingDTO : dtos) {
             redPercentage=0.0f;
             greenPercentage=0.0f;
            if (totalRed > 0) {
                redPercentage = (float) ((existingDTO.getRedFrequency() * 100.0) / totalRed);
                existingDTO.setRedPercentage(redPercentage);
                totalRedPercentage += redPercentage;
            } else {
                existingDTO.setRedPercentage(0.0f); // Set to 0 if totalRed is zero
            }
            if (totalGreen > 0) {
                greenPercentage = (float) ((existingDTO.getGreenFrequency() * 100.0) / totalGreen);
                existingDTO.setGreenPercentage(greenPercentage);
                totalGreenPercentage += greenPercentage;
            } else {
                existingDTO.setGreenPercentage(0.0f); // Set to 0 if totalGreen is zero
            }
        }
        ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();
        reportResult.setCriteria("Total");
        reportResult.setRedFrequency(totalRed);
        reportResult.setGreenFrequency(totalGreen);
        reportResult.setRedPercentage(totalRedPercentage);
        reportResult.setGreenPercentage(totalGreenPercentage);
        reportResult.setRptEndDate(endDate);
        reportResult.setRptStartDate(startDate);
        dtos.add(reportResult);
        return dtos;
    }

    @Query(value = "SELECT  " +
            "    ptd.document_number,  " +
            "    p.first_name,  p.middle_name, p.last_name,  " +
            "    p.risk_level, " +
            "    MAX(CASE WHEN rs.criteria = 'AGE_CONDITION' THEN rs.score ELSE 0 END) AS AGE_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'MODE_OF_PAYMENT_CONDITION' THEN rs.score ELSE 0 END) AS MODE_OF_PAYMENT_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'TRAVEL_FREQUENCY_CONDITION' THEN rs.score ELSE 0 END) AS TRAVEL_FREQUENCY_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'PURPOSE_OF_TRAVEL_CONDITION' THEN rs.score ELSE 0 END) AS PURPOSE_OF_TRAVEL_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'BAGGAGE_WEIGHT_CONDITION' THEN rs.score ELSE 0 END) AS BAGGAGE_WEIGHT_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'DEPARTURE_COUNTRY_CONDITION' THEN rs.score ELSE 0 END) AS DEPARTURE_COUNTRY_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'TRAVEL_HISTORY_CONDITION' THEN rs.score ELSE 0 END) AS TRAVEL_HISTORY_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'GENDER_CONDITION' THEN rs.score ELSE 0 END) AS GENDER_CONDITION,  " +
            "    MAX(CASE WHEN rs.criteria = 'TRANSIT_CONDITION' THEN rs.score ELSE 0 END) AS TRANSIT_CONDITION  " +
            "FROM  " +
            "    custom_profile_local.passenger_profiles p  " +
            "        JOIN  " +
            "    custom_profile_local.customs_operations c ON c.id = p.customs_operation_id  " +
            "        JOIN  " +
            "    custom_profile_local.passenger_travel_documents ptd ON p.id = ptd.passenger_profile_id  " +
            "        JOIN  " +
            "    custom_profile_local.risk_analysis_result rs ON p.id = rs.passenger_profile_id  " +
            "        JOIN  " +
            "    custom_profile_local.transportations t ON c.id = t.customs_operation_id  " +
            " where  t.created_date>=:startDate and t.created_date<=:endDate " +
            " GROUP BY  " +
            "    p.first_name,  p.middle_name, p.last_name, p.risk_level,  ptd.document_number ", nativeQuery = true)
    List<Object[]> getPassengerProfilesByRiskSelectivityLevelInDetail(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    default List<ReportPassengerRiskDTO> findPassengerProfilesByRiskSelectivityLevelInDetail(
            Date startDate,
            Date endDate
    ) {
        List<Object[]> resultList = null;
        resultList = getPassengerProfilesByRiskSelectivityLevelInDetail(startDate.toString(), endDate.toString());
        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
        int totalRed = 0;
        int totalGreen = 0;
        for (Object[] row : resultList) {
            ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();

            reportResult.setDocumentNumber((String) row[0]);
            reportResult.setFirstName((String) row[1]);
            reportResult.setMiddleName((String) row[2]);
            reportResult.setLastName((String) row[3]);
            reportResult.setRiskLevel((String) row[4]);
            reportResult.setAgeCondition(((Number) row[5]).intValue());
            reportResult.setModeOfPaymentCondition(((Number) row[6]).intValue());
            reportResult.setTravelFrequencyCondition(((Number) row[7]).intValue());
            reportResult.setPurposeOfTravelCondition(((Number) row[8]).intValue());
            reportResult.setBaggageWeightCondition(((Number) row[9]).intValue());
            reportResult.setDepartureCountryCondition(((Number) row[10]).intValue());
            reportResult.setTravelHistoryCondition(((Number) row[11]).intValue());
            reportResult.setGenderCondition(((Number) row[12]).intValue());
            reportResult.setTransitCondition(((Number) row[13]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add(reportResult);
        }

        for (ReportPassengerRiskDTO existingDTO : dtos) {
            if(totalRed>0) {
                existingDTO.setRedPercentage((existingDTO.getRedFrequency() * 100) / totalRed);
            }
            if(totalGreen>0) {
                existingDTO.setGreenPercentage((existingDTO.getGreenFrequency() * 100) / totalGreen);
            }
        }

        return dtos;
    }


    @Query(value = "select p.risk_level, count(p.risk_level) as Frequency " +
            "from custom_profile_local.passenger_profiles p, custom_profile_local.customs_operations c,custom_profile_local.transportations t " +
            "where  c.id = p.customs_operation_id and  c.id = t.customs_operation_id and " +
            "  t.created_date>=:startDate and t.created_date<=:endDate " +
            "group by p.risk_level", nativeQuery = true)
    List<Object[]> getSummaryOfFrequencyRiskLevel(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findSummaryOfFrequencyRiskLevel(
            Date startDate,
            Date endDate
    ) {
        {
            List<Object[]> resultList = null;
            resultList = getSummaryOfFrequencyRiskLevel(startDate.toString(), endDate.toString());
            List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
            int totalFrequency = 0;
            int totalGreen = 0;
            float totalPercentage = 0.0f;
            float totalGreenPercentage = 0.0f;
            float percentage = 0.0f;
            float greenPercentage = 0.0f;
            for (Object[] row : resultList) {

                String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
                DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
                String formattedDate = targetDateFormat.format(startDate);
                Timestamp sDate = Timestamp.valueOf(formattedDate);


                formattedDate = targetDateFormat.format(endDate);
                Timestamp eDate = Timestamp.valueOf(formattedDate);

                ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();

                reportResult.setRiskLevel((String) row[0]);

                 reportResult.setTotalFrequency(((Number) row[1]).intValue());

                reportResult.setStartDate(sDate);
                reportResult.setEndDate(eDate);

                    // Set any other fields if needed
                dtos.add(reportResult);

                totalFrequency += reportResult.getTotalFrequency();

            }
            // update percentage
            for (ReportPassengerRiskDTO existingDTO : dtos) {
                percentage = 0.0f;

                if (totalFrequency > 0) {
                    percentage = (float) ((existingDTO.getTotalFrequency() * 100.0) / totalFrequency);
                    existingDTO.setTotalPercentage(percentage);
                    totalPercentage += percentage;
                } else {
                    existingDTO.setTotalPercentage(0.0f); // Set to 0 if totalRed is zero
                }

            }
            ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel("Total");
            reportResult.setTotalFrequency(totalFrequency);
            reportResult.setTotalPercentage(totalPercentage);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add(reportResult);
            return dtos;
        }
    }

    @Query(value = "select pp.risk_level, count(pp.risk_level) as Frequency " +
            "from custom_profile_local.passenger_profiles pp " +
            "where pp.override_type IS NOT NULL and " +
            "  pp.created_date>=:startDate and pp.created_date<=:endDate " +
            "GROUP BY pp.risk_level", nativeQuery = true)
    List<Object[]> getSummaryManuallyRerouted(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findSummaryManuallyRerouted(
            Date startDate,
            Date endDate
    ) {


        List<Object[]> resultList = null;

        resultList = getSummaryManuallyRerouted(startDate.toString(), endDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        int totalFrequency = 0;
        int totalGreen = 0;
        float totalPercentage = 0.0f;
        float totalGreenPercentage = 0.0f;
        float percentage = 0.0f;
        float greenPercentage = 0.0f;

        for (Object[] row : resultList) {

            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            String formattedDate = targetDateFormat.format(startDate);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);

            reportResult.setCreatedDate(sDate);
            reportResult.setTotalFrequency(((Number) row[1]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);

            totalFrequency += reportResult.getTotalFrequency();

        }

        for (ReportPassengerRiskDTO existingDTO : dtos) {
            percentage = 0.0f;

            if (totalFrequency > 0) {
                percentage = (float) ((existingDTO.getTotalFrequency() * 100.0) / totalFrequency);
                existingDTO.setTotalPercentage(percentage);
                totalPercentage += percentage;
            } else {
                existingDTO.setTotalPercentage(0.0f); // Set to 0 if totalRed is zero
            }

        }
        ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();
        reportResult.setRiskLevel("Total");
        reportResult.setTotalFrequency(totalFrequency);
        reportResult.setTotalPercentage(totalPercentage);
        reportResult.setRptEndDate(endDate);
        reportResult.setRptStartDate(startDate);

        dtos.add(reportResult);
        return dtos;
    }

    @Query(value = "SELECT ptd.document_number, " +
            " p.first_name,  p.middle_name, p.last_name, p.override_type, p.risk_level, " +
            " MAX(CASE WHEN rs.criteria = 'AGE_CONDITION' THEN rs.score ELSE 0 END) AS AGE_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'MODE_OF_PAYMENT_CONDITION' THEN rs.score ELSE 0 END) AS MODE_OF_PAYMENT_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'TRAVEL_FREQUENCY_CONDITION' THEN rs.score ELSE 0 END) AS TRAVEL_FREQUENCY_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'PURPOSE_OF_TRAVEL_CONDITION' THEN rs.score ELSE 0 END) AS PURPOSE_OF_TRAVEL_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'BAGGAGE_WEIGHT_CONDITION' THEN rs.score ELSE 0 END) AS BAGGAGE_WEIGHT_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'DEPARTURE_COUNTRY_CONDITION' THEN rs.score ELSE 0 END) AS DEPARTURE_COUNTRY_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'TRAVEL_HISTORY_CONDITION' THEN rs.score ELSE 0 END) AS TRAVEL_HISTORY_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'GENDER_CONDITION' THEN rs.score ELSE 0 END) AS GENDER_CONDITION, " +
            " MAX(CASE WHEN rs.criteria = 'TRANSIT_CONDITION' THEN rs.score ELSE 0 END) AS TRANSIT_CONDITION " +
            "FROM " +
            "custom_profile_local.passenger_profiles p " +
            " JOIN " +
            "custom_profile_local.customs_operations c ON c.id = p.customs_operation_id " +
            " JOIN " +
            "custom_profile_local.passenger_travel_documents ptd ON p.id = ptd.passenger_profile_id " +
            " JOIN " +
            "custom_profile_local.risk_analysis_result rs ON p.id = rs.passenger_profile_id " +
            " JOIN " +
            "custom_profile_local.transportations t ON c.id = t.customs_operation_id " +
            " WHERE p.created_date>=:startDate and p.created_date<=:endDate and p.override_type IS NOT NULL " +
            " GROUP BY " +
            " p.first_name, p.middle_name, p.last_name, p.risk_level,  ptd.document_number, p.override_type", nativeQuery = true)
    List<Object[]> getSelectRiskSummaryRerouted(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findSelectRiskSummaryRerouted(
            Date startDate,
            Date endDate
    ) {
        List<Object[]> resultList = null;
        resultList = getSelectRiskSummaryRerouted(startDate.toString(), endDate.toString());
        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
        int totalRed = 0;
        int totalGreen = 0;
        for (Object[] row : resultList) {
            ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();

            reportResult.setDocumentNumber((String) row[0]);
            reportResult.setFirstName((String) row[1]);
            reportResult.setMiddleName((String) row[2]);
            reportResult.setLastName((String) row[3]);
            reportResult.setOverride_type((String) row[4]);
            reportResult.setRiskLevel((String) row[5]);
            reportResult.setAgeCondition(((Number) row[6]).intValue());
            reportResult.setModeOfPaymentCondition(((Number) row[7]).intValue());
            reportResult.setTravelFrequencyCondition(((Number) row[8]).intValue());
            reportResult.setPurposeOfTravelCondition(((Number) row[9]).intValue());
            reportResult.setBaggageWeightCondition(((Number) row[10]).intValue());
            reportResult.setDepartureCountryCondition(((Number) row[11]).intValue());
            reportResult.setTravelHistoryCondition(((Number) row[12]).intValue());
            reportResult.setGenderCondition(((Number) row[13]).intValue());
            reportResult.setTransitCondition(((Number) row[14]).intValue());
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add(reportResult);
        }

        for (ReportPassengerRiskDTO existingDTO : dtos) {
            if(totalRed>0) {
                existingDTO.setRedPercentage((existingDTO.getRedFrequency() * 100) / totalRed);
            }
            if(totalGreen>0) {
                existingDTO.setGreenPercentage((existingDTO.getGreenFrequency() * 100) / totalGreen);
            }
        }

        return dtos;
    }

    @Query(value = "SELECT pp.risk_level, pp.override_type,count(pp.risk_level) as frequency, pp.created_date " +
            "            FROM custom_profile_local.passenger_profiles pp  " +
            "            WHERE pp.created_date>=:startDate and pp.created_date<=:endDate and (pp.risk_level = 'GREEN'and pp.override_type = 'RANDOM_SELECTION') or " +
            "    (pp.risk_level = 'GREEN'and pp.override_type is null) " +
            "group by pp.risk_level, pp.override_type, pp.created_date ", nativeQuery = true)
    List<Object[]> getReroutedRandomSelection(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    default List<ReportPassengerRiskDTO> findReroutedRandomSelection(
            Date startDate,
            Date endDate
    ) {


        List<Object[]> resultList = null;

        resultList = getReroutedRandomSelection(startDate.toString(), endDate.toString());

        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            ReportPassengerRiskDTO  reportResult = new ReportPassengerRiskDTO();
            reportResult.setRiskLevel((String) row[0]);


            String targetFormat = "yyyy-MM-dd HH:mm:ss";  // Target date format
            DateFormat targetDateFormat = new SimpleDateFormat(targetFormat);
            reportResult.setNumberOfOccurrence(((Number) row[2]).intValue());

            String formattedDate = targetDateFormat.format(row[3]);
            Timestamp sDate = Timestamp.valueOf(formattedDate);

            reportResult.setCreatedDate(sDate);
            reportResult.setOverride_type((String) row[1]);
            reportResult.setRptEndDate(endDate);
            reportResult.setRptStartDate(startDate);

            dtos.add( reportResult);
        }
        return dtos;
    }


    @Query(value = "select gender, risk_level, count(risk_level) as Frequency " +
            "from custom_profile_local.passenger_profiles group by gender,risk_level",nativeQuery = true)
    List<Object[]> getRiskLevelByGender();

    default List<ReportPassengerRiskDTO> findRiskLevelByGender()
    {
        {
            List<Object[]> resultList = null;
            resultList = getRiskLevelByGender();
            List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
            int totalFrequency = 0;

            for (Object[] row : resultList) {
                // Check if the row has riskLevel and totalFrequency values, and riskLevel is not "Total"
                if (row.length >= 3 && row[0] != null && row[1] != null && row[2] != null
                        && !("Total".equals(row[1]))) {
                    ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();
                    reportResult.setGender((String) row[0]);
                    reportResult.setRiskLevel((String) row[1]);
                    reportResult.setTotalFrequency(((Number) row[2]).intValue());
                    // Set any other fields if needed
                    dtos.add(reportResult);
                    totalFrequency += reportResult.getTotalFrequency();
                }
            }
            return dtos;
        }
    }

    @Query(value = "SELECT CASE WHEN age < 20 THEN 'Under 20' " +
            "                WHEN age BETWEEN 20 AND 50 THEN '20-50' " +
            "                ELSE 'Above 50' END AS age_group, " +
            "       risk_level, " +
            "       COUNT(risk_level) AS Frequency " +
            "FROM custom_profile_local.passenger_profiles " +
            "GROUP BY age_group, risk_level", nativeQuery = true)
    List<Object[]> getRiskLevelByAgeGroup();

    default List<ReportPassengerRiskDTO> findRiskLevelByAgeGroup() {
        List<Object[]> resultList = getRiskLevelByAgeGroup();
        List<ReportPassengerRiskDTO> dtos = new ArrayList<>();
        int totalFrequency = 0;

        for (Object[] row : resultList) {
            // Check if the row has age_group, riskLevel, and totalFrequency values, and riskLevel is not "Total"
            if (row.length >= 3 && row[0] != null && row[1] != null && row[2] != null && !("Total".equals(row[1]))) {
                ReportPassengerRiskDTO reportResult = new ReportPassengerRiskDTO();
                reportResult.setAgeGroup(((String) row[0]));
                reportResult.setRiskLevel((String) row[1]);
                reportResult.setTotalFrequency(((Number) row[2]).intValue());
                // Set any other fields if needed
                dtos.add(reportResult);
                totalFrequency += reportResult.getTotalFrequency();
            }
        }
        return dtos;
    }



}
