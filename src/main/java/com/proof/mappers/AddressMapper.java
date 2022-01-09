package com.proof.mappers;

import com.proof.api.dtos.AddressDto;
import com.proof.persistence.entities.Address;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toDto(Address address);

    Address toEntity(AddressDto addressDto);
}
