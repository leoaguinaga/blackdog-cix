package pe.edu.utp.blackdog.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum State {
    ON_HOLD("Pedido en espera de confirmación"),
    ON_PROCESS("Pedido en proceso de preparación"),
    ON_THE_WAY("Pedido en camino"),
    WAITING_DRIVER("En espera de asiganción de delivery"),
    FINISHED("Pedido entregado"),
    CANCELED("Pedido cancelado");

    private final String displayName;

    State(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<State> getStates() {
        return new ArrayList<>(Arrays.asList(State.values()));
    }
}
