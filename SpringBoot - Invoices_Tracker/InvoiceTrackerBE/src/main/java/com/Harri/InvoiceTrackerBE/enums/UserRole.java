package com.Harri.InvoiceTrackerBE.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {      //USER ROLES ENUM
    SUPERUSER, AUDITOR , USER;

    @Override
    public String getAuthority() {
        return name();
    }
}