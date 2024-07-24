package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.UUID;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T17:44:57+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrganizationMapperImpl implements OrganizationMapper {

    private final DatatypeFactory datatypeFactory;

    public OrganizationMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Organization toOrganization(CreateOrganizationRequest createOrganizationRequest) {
        if ( createOrganizationRequest == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setOrganizationName( createOrganizationRequest.organizationName() );
        organization.setEmail( createOrganizationRequest.email() );
        organization.setPassword( createOrganizationRequest.password() );

        return organization;
    }

    @Override
    public CreateOrganizationResponse toCreateOrganizationResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        UUID id = null;
        String organizationName = null;
        String email = null;
        String password = null;
        LocalDate createdDate = null;

        id = organization.getId();
        organizationName = organization.getOrganizationName();
        email = organization.getEmail();
        password = organization.getPassword();
        createdDate = xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( organization.getCreatedDate() ) );

        CreateOrganizationResponse createOrganizationResponse = new CreateOrganizationResponse( id, organizationName, email, password, createdDate );

        return createOrganizationResponse;
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }
}
