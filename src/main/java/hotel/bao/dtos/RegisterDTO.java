package hotel.bao.dtos;

import hotel.bao.entities.Role;

import java.util.Set;

public record RegisterDTO(String login, String password, Set<Role> roles) { }

