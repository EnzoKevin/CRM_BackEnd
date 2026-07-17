package com.crm.MVP.modules.User.Mapper;

import com.crm.MVP.modules.User.DTO.UserRequestDTO;
import com.crm.MVP.modules.User.DTO.UserResponseDTO;
import com.crm.MVP.modules.User.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        updateEntity(dto, user);
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.isAdmin(),
                user.getIdioma(),
                user.getFusoHorario(),
                user.getFormatoDeData(),
                user.isNotificacoesEmail(),
                user.isAlertasWeb(),
                user.isResumoSemanal()
        );
    }

    public void updateEntity(UserRequestDTO dto, User user) {
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setAdmin(dto.getAdmin());
        user.setIdioma(dto.getIdioma());
        user.setFusoHorario(dto.getFusoHorario());
        user.setFormatoDeData(dto.getFormatoDeData());
        user.setNotificacoesEmail(dto.getNotificacoesEmail());
        user.setAlertasWeb(dto.getAlertasWeb());
        user.setResumoSemanal(dto.getResumoSemanal());
    }
}
