package api.invest.mapper;

import api.invest.dto.RegistrationRequestDto;
import api.invest.entity.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    User toUser(RegistrationRequestDto registrationRequestDto);
}
