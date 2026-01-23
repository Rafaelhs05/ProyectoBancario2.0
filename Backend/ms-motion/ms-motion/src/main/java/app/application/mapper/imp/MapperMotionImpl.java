package app.application.mapper.imp;

import app.application.mapper.MapperMotion;
import app.domain.model.Motion;
import app.web.dto.RequestMotionDto;
import app.web.dto.ResponseMotionDto;
import org.springframework.stereotype.Component;

@Component
public class MapperMotionImpl implements MapperMotion {
    @Override
    public Motion toEntity(RequestMotionDto requestMotionDto) {

        return Motion.builder()
                .motionType(requestMotionDto.getMotionType())
                .amount(requestMotionDto.getAmount())
                .currency(requestMotionDto.getCurrency())
                .originAccount(requestMotionDto.getOriginAccount())
                .destinationAccount(requestMotionDto.getDestinationAccount())
                .build();
    }

    @Override
    public ResponseMotionDto toDto(Motion motion) {

        return ResponseMotionDto.builder()
                .motionType(motion.getMotionType().toString())
                .amount(motion.getAmount())
                .currency(motion.getCurrency())
                .motionDate(motion.getMotionDate())
                .originAccount(motion.getOriginAccount())
                .destinationAccount(motion.getDestinationAccount())
                .status(motion.getStatus().toString())
                .build();
    }
}
