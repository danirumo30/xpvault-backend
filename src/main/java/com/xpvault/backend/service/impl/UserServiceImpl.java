package com.xpvault.backend.service.impl;

import com.xpvault.backend.model.AppUserModel;
import com.xpvault.backend.dao.UserDAO;
import com.xpvault.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public List<AppUserModel> allUsers() {
        return userDAO.findAll();
    }

}
