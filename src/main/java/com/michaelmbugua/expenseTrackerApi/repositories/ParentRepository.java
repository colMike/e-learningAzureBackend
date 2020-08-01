package com.michaelmbugua.expenseTrackerApi.repositories;

import com.michaelmbugua.expenseTrackerApi.domain.Parent;
import com.michaelmbugua.expenseTrackerApi.exceptions.EtAuthException;

public interface ParentRepository {

    Number create(String firstName, String lastName, String email, String password) throws EtAuthException;

    Parent findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    Parent findById(Number userId);

    Boolean checkEmail(String email);
}
