package com.atbm.models.wrapper.request;

import com.atbm.models.enums.Role;

public record AddAccountRequest(
        String username,
        String password,
        String email,
        Role role,
        Boolean isDeleted
) {}
