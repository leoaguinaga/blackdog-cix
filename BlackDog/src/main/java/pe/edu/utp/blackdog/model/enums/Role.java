package pe.edu.utp.blackdog.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN("Administrador"),
    DELIVERY("Delivery"),
    CHEF("Cocina");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<Role> getRoles() {
        return new ArrayList<>(Arrays.asList(Role.values()));
    }
}
