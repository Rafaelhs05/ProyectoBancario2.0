package app.application.mapper;

import app.domain.model.Motion;
import app.web.dto.RequestMotionDto;
import app.web.dto.ResponseMotionDto;

public interface MapperMotion {

    Motion toEntity(RequestMotionDto requestMotionDto);

    ResponseMotionDto toDto(Motion motion);

}
