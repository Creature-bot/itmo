package net;

import java.io.Serializable;

public class Response implements Serializable {
    private final String message;
    private final boolean success;
    private final boolean exit;

    public Response(String message, boolean success) {
        this(message, success, false);
    }

    public Response(String message, boolean success, boolean exit) {
        this.message = message;
        this.success = success;
        this.exit = exit;
    }

    public String message() {
        return message;
    }

    public boolean success() {
        return success;
    }

    public boolean isExit() {
        return exit;
    }
}