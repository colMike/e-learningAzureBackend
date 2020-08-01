package com.michaelmbugua.expenseTrackerApi.services;

import com.michaelmbugua.expenseTrackerApi.domain.Parent;
import com.michaelmbugua.expenseTrackerApi.exceptions.EtAuthException;

public interface ParentService {

    Parent validateParent(String email, String password) throws EtAuthException;
    Parent registerParent(String firstName, String lastName, String email, String password) throws EtAuthException;

    Boolean checkEmail(String email);
}
