package org.example.repository.mapper;

public interface BaseMapper<T, E> {

    T toDTO(E entity);

    E toEntity(T dto);
}
