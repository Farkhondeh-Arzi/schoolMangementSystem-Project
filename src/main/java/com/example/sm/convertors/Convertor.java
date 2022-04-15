package com.example.sm.convertors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Convertor<T, DTO> {

    private List<Method> dtoDeclaredMethods;
    private List<Method> entityDeclaredMethods;

    public T convertToEntity(T entity, DTO dto) {

        convert(entity, dto, false);
        return entity;
    }

    public DTO convertToDTO(T entity, DTO dto) {

        convert(entity, dto, true);
        return dto;
    }

    private void convert(T entity, DTO dto, boolean entityToDTO) {

        obtainMethods(entity, dto);

        for (Method dtoMethod : dtoDeclaredMethods) {

            String dtoMethodName = dtoMethod.getName();
            String dtoStart = entityToDTO ? "set" : "get";
            String entityStart = entityToDTO ? "get" : "set";

            if (dtoMethodName.startsWith(dtoStart)) {

                for (Method entityMethod : entityDeclaredMethods) {

                    if (entityMethod.getName().startsWith(entityStart) &&
                            dtoMethod.getName().substring(3).equals(entityMethod.getName().substring(3))) {

                        try {

                            Object getterArgument = entityToDTO ? entity : dto;
                            Object setterArgument = entityToDTO ? dto : entity;

                            Object value = entityToDTO? entityMethod.invoke(getterArgument) : dtoMethod.invoke(getterArgument);
                            if (entityToDTO) {
                                dtoMethod.invoke(setterArgument, value);
                            } else {
                                entityMethod.invoke(setterArgument, value);
                            }
                            break;
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    private void obtainMethods(T entity, DTO dto) {
        dtoDeclaredMethods = Arrays.asList(dto.getClass().getDeclaredMethods());
        entityDeclaredMethods = Arrays.asList(entity.getClass().getDeclaredMethods());

        Class<?> superclass = entity.getClass().getSuperclass();
        String superclassPackageName = superclass.getName().substring(0, superclass.getName().lastIndexOf("."));
        String entityPackageName = entity.getClass().getName().substring(0, superclass.getName().lastIndexOf("."));

        //If the class and it's parent are in the same package
        if (superclassPackageName.equals(entityPackageName)) {

            List<Method> superMethods = Arrays.asList(superclass.getDeclaredMethods());
            entityDeclaredMethods = ListUtils.union(entityDeclaredMethods, superMethods);
        }
    }

    public List<DTO> convertAllToDTO(List<T> entities, Class<? extends DTO> dtoClass) {
        List<DTO> dtos = new ArrayList<>();

        for (T entity : entities) {
            try {
                dtos.add(convertToDTO(entity, dtoClass.newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return dtos;
    }
}
