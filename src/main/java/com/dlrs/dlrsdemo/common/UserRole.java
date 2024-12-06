package com.dlrs.dlrsdemo.common;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum UserRole {
    SUPER_ADMIN("super_admin"),
    SUPERVISOR("supervisor"),
    SURVEYOR("surveyor");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public static Set<String> getUserRoleType() {
        Set<String> userRoles = new HashSet<>();

        for (UserRole role : UserRole.values()) {
            if (!role.equals(SUPER_ADMIN)) { // Exclude SUPER_ADMIN
                userRoles.add(role.role);
            }
        }
        return userRoles;
    }

    public static UserRole getUserRoleType(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getRole().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No matching UserRole found for role: " + role);
    }
}


