package com.example.sm.convertos;

import java.util.List;

public interface Convertor<T, DTO> {

    void convertToEntity(T entity, DTO dto);

    DTO convertToDTO(T entity);

    List<DTO> convertAllToDTO(List<T> entities);
}
