package com.taskmanagerpro.model;
import java.io.Serializable;

/**
 * La clase Task representa una tarea individual dentro de un proyecto.
 * Implementa Serializable para permitir que los objetos de esta clase se puedan guardar y cargar desde un archivo.
 */
@SuppressWarnings("ALL")
public class Task implements Serializable {

    private final String title;
    private final String description;
    private boolean isCompleted;

    /**
     * Constructor de Task.
     * Inicializa una tarea con un título, descripción
     * Por defecto, la tarea no está completada.
     * @param title El título de la tarea.
     * @param description La descripción de la tarea.
     */

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Devuelve el título de la tarea.
     * @return El título de la tarea.
     */

    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el estado de la tarea.
     * @return true si la tarea está completada, de lo contrario false.
     */

    public boolean isCompleted(){
        return isCompleted;
    }

    /**
     * Establece el estado de la tarea como completada o no.
     * @param completed true para marcar la tarea como completada, false para marcarla como pendiente.
     */

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Devuelve una representación en cadena de la tarea.
     * Incluye el estado (COMPLETED o PENDING), el título y la descripción.
     * @return Una cadena que representa la tarea.
     */

    @Override
    public String toString() {
        return (isCompleted ? "[COMPLETED] " : "[PENDING] ") + title + " - " + description;
    }
}

