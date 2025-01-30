package com.openclassrooms.mddapi.mappers;

import java.util.List;

public interface ResponseMapper<E, R> {

    R toResponse(E entity);

    List<R> toResponse(List<E> entityList);
}
