package se325.assignment01.concert.service.mapper;

import se325.assignment01.concert.common.dto.UserDTO;
import se325.assignment01.concert.service.domain.User;

public class UserMapper {
    public static User toDomainModel(UserDTO dtoUser) {
       User user = new User(
                dtoUser.getUsername(),
                dtoUser.getPassword()
                );

        return user;
    }

    public static se325.assignment01.concert.common.dto.UserDTO toDto(User user) {
        se325.assignment01.concert.common.dto.UserDTO dtoParolee =
                new UserDTO(
                        user.getUsername(),
                        user.getPassword());
        return dtoParolee;
    }
}
