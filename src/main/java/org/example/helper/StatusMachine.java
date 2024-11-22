package org.example.helper;

public enum StatusMachine {
    AVAILABLE("Available"),
    RENTED("Rented");

    private final String status;

    StatusMachine(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusMachine fromString(String status) {
        for (StatusMachine sm : StatusMachine.values()) {
            if (sm.getStatus().equalsIgnoreCase(status)) {
                return sm;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
