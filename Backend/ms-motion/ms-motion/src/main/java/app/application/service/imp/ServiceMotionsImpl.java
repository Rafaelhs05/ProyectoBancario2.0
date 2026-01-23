package app.application.service.imp;

import app.application.mapper.MapperMotion;
import app.application.service.ServiceMotions;
import app.domain.model.Motion;
import app.domain.model.StatusMotion;
import app.domain.model.TypeMotion;
import app.domain.repository.RepositoryMotion;
import app.web.dto.RequestMotionDto;
import app.web.dto.ResponseMotionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceMotionsImpl implements ServiceMotions {

    private final RepositoryMotion repositoryMotion;

    private final MapperMotion mapperMotion;

    @Override
    public ResponseMotionDto createMotion(RequestMotionDto requestMotionDto) {

        Motion motion = mapperMotion.toEntity(requestMotionDto);
        motion.setStatus(StatusMotion.COMPLETADO);
        motion.setMotionDate(LocalDateTime.now());
        Motion register = repositoryMotion.save(motion);
        return mapperMotion.toDto(register);
    }

    @Override
    public List<ResponseMotionDto> listMotionStateDone(StatusMotion status) {
        return repositoryMotion.findByStatus(status).stream().map(mapperMotion::toDto).toList();
    }

    @Override
    public List<ResponseMotionDto> listMotionByAccount(String numAccount) {
        return repositoryMotion.findByOriginAccount(numAccount).stream().map(mapperMotion::toDto).toList();
    }

    @Override
    public List<ResponseMotionDto> listMotionByType(TypeMotion type) {
        return repositoryMotion.findByMotionType(type).stream().map(mapperMotion::toDto).toList();
    }

    @Override
    public List<ResponseMotionDto> listMotionDateRange(String numAccount, LocalDate starDate, LocalDate endDate) {
        return List.of();
    }

}
