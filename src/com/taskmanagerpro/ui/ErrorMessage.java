package com.taskmanagerpro.ui;

public enum ErrorMessage {
    PROJECT_NOT_FOUND("Project not found."),
    TASK_NOT_FOUND("Task not found."),
    PROJECT_EXISTS("A project with the name already exists.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

