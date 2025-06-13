package com.atbm.models.wrapper.response;

import com.atbm.models.enums.Role;

public record AccountResponse(long accountId, String username, String email, String publicKey, Role role) {
}
