package com.atmosware.belatrix.managmentService.business.mappers;

import com.atmosware.belatrix.managmentService.business.dto.requests.organization.CreateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.requests.organization.UpdateOrganizationRequest;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.CreateOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.DeleteOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.GetAllOrganizationResponse;
import com.atmosware.belatrix.managmentService.business.dto.responses.organization.UpdateOrganizationResponse;
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
    date = "2024-08-13T15:48:44+0300",
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

        return organization;
    }

    @Override
    public CreateOrganizationResponse toCreateOrganizationResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        UUID id = null;
        String organizationName = null;
        LocalDate createdDate = null;

        id = organization.getId();
        organizationName = organization.getOrganizationName();
        createdDate = xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( organization.getCreatedDate() ) );

        CreateOrganizationResponse createOrganizationResponse = new CreateOrganizationResponse( id, organizationName, createdDate );

        return createOrganizationResponse;
    }

    @Override
    public Organization toOrganization(UpdateOrganizationRequest updateOrganizationRequest) {
        if ( updateOrganizationRequest == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setOrganizationName( updateOrganizationRequest.organizationName() );

        return organization;
    }

    @Override
    public UpdateOrganizationResponse toUpdateOrganizationResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        UUID id = null;
        String organizationName = null;
        LocalDateTime createdDate = null;
        LocalDateTime updatedDate = null;

        id = organization.getId();
        organizationName = organization.getOrganizationName();
        createdDate = organization.getCreatedDate();
        updatedDate = organization.getUpdatedDate();

        UpdateOrganizationResponse updateOrganizationResponse = new UpdateOrganizationResponse( id, organizationName, createdDate, updatedDate );

        return updateOrganizationResponse;
    }

    @Override
    public DeleteOrganizationResponse toDeleteOrganizationResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        UUID id = null;
        String organizationName = null;
        LocalDateTime createdDate = null;
        LocalDateTime updatedDate = null;
        LocalDateTime deletedDate = null;

        id = organization.getId();
        organizationName = organization.getOrganizationName();
        createdDate = organization.getCreatedDate();
        updatedDate = organization.getUpdatedDate();
        deletedDate = organization.getDeletedDate();

        DeleteOrganizationResponse deleteOrganizationResponse = new DeleteOrganizationResponse( id, organizationName, createdDate, updatedDate, deletedDate );

        return deleteOrganizationResponse;
    }

    @Override
    public GetAllOrganizationResponse toGetAllOrganizationResponse(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        UUID id = null;
        String organizationName = null;
        LocalDateTime createdDate = null;
        LocalDateTime updatedDate = null;

        id = organization.getId();
        organizationName = organization.getOrganizationName();
        createdDate = organization.getCreatedDate();
        updatedDate = organization.getUpdatedDate();

        GetAllOrganizationResponse getAllOrganizationResponse = new GetAllOrganizationResponse( id, organizationName, createdDate, updatedDate );

        return getAllOrganizationResponse;
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
