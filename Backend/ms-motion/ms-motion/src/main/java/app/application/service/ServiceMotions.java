package app.application.service;

import app.domain.model.StatusMotion;
import app.domain.model.TypeMotion;
import app.web.dto.RequestMotionDto;
import app.web.dto.ResponseMotionDto;
import java.time.LocalDate;
import java.util.List;



public interface ServiceMotions {

    ResponseMotionDto createMotion(RequestMotionDto requestMotionDto);

    List<ResponseMotionDto> listMotionStateDone(StatusMotion status);

    List<ResponseMotionDto> listMotionByAccount(String numAccount);

    List<ResponseMotionDto> listMotionByType(TypeMotion type);

    List<ResponseMotionDto> listMotionDateRange(String numAccount, LocalDate starDate, LocalDate endDate);


}
