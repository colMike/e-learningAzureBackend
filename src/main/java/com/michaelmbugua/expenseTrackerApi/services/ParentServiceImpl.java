package com.michaelmbugua.expenseTrackerApi.services;

import com.michaelmbugua.expenseTrackerApi.domain.Parent;
import com.michaelmbugua.expenseTrackerApi.exceptions.EtAuthException;
import com.michaelmbugua.expenseTrackerApi.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;


@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    ParentRepository parentRepository;


    @Override
    public Parent validateParent(String email, String password) throws EtAuthException {
        if (email != null) email = email.toLowerCase();
        return parentRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Parent registerParent(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) email = email.toLowerCase();
        if (!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid Email Format");
        Integer count = parentRepository.getCountByEmail(email);

        if (count > 0)
            throw new EtAuthException("Email already in use");
        Number parentId = parentRepository.create(firstName, lastName, email, password);

        return parentRepository.findById(parentId);
    }

    @Override
    public Boolean checkEmail(String email) {
        return parentRepository.checkEmail(email);
    }
}
