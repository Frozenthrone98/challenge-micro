package com.mamani.msaccountmanagement.service;

import java.util.List;

public interface ICRUD<T, S, ID> {
    S save(T t) throws Exception;
    S update(T t, ID id) throws Exception;
    List<S> readAll() throws Exception;
    S readById(ID t) throws Exception;
    void delete(ID id) throws Exception;
}
