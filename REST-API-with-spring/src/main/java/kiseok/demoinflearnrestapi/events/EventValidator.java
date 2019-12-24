package kiseok.demoinflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors)  {
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0)  {
            errors.reject("wrongPrices", "Values for prices are wrong");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
           endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
           endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()))    {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong.");
        }

        // TODO BeginEnrollmentDateTime
        LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
        if(beginEnrollmentDateTime.isAfter(eventDto.getCloseEnrollmentDateTime()) ||
            beginEnrollmentDateTime.isAfter(eventDto.getBeginEventDateTime()) ||
            beginEnrollmentDateTime.isAfter(eventDto.getEndEventDateTime()))    {
            errors.rejectValue("beginEnrollmentDateTime", "wrongValue", "beginEnrollmentDateTime is wrong.");
        }


        // TODO BeginEventDateTime
        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        if(beginEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ||
            beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            beginEventDateTime.isAfter(eventDto.getEndEventDateTime()))    {
            errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong.");
        }

        // TODO CloseEnrollmentDateTime
        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        if(closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) ||
            closeEnrollmentDateTime.isAfter(eventDto.getBeginEventDateTime()) ||
            closeEnrollmentDateTime.isAfter(eventDto.getEndEventDateTime())) {
            errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong.");
        }

    }

}
