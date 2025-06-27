package com.atbm.models.wrapper.request;

import com.atbm.models.enums.Role;

public record EditAccountRequest(
        long accountId,
        String username,
        String email,
        Role role,
        Boolean isDeleted
) {}
