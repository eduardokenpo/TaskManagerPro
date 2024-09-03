package com.taskmanagerpro.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Project representa un proyecto que contiene múltiples tareas.
 * Implementa Serializable para permitir que los objetos de esta clase se puedan guardar y cargar desde un archivo.
 */
@SuppressWarnings("ALL")
public class Project implements Serializable {
    private final String name;
    private final List<Task> tasks;

    /**
     * Constructor de Project.
     * Inicializa el proyecto con un nombre y una lista vacía de tareas.
     * @param name El nombre del proyecto.
     */
    public Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    /**
     * Devuelve el nombre del proyecto.
     * @return El nombre del proyecto.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la lista de tareas del proyecto.
     * @return Una lista de tareas.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Agrega una tarea a la lista de tareas del proyecto.
     * @param task La tarea a agregar.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Devuelve la lista de tareas pendientes.
     * Filtra y devuelve solo las tareas que no han sido completadas.
     * @return Una lista de tareas pendientes.
     */
    @SuppressWarnings("unused")
    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    /**
     * Devuelve la lista de tareas completadas.
     * Filtra y devuelve solo las tareas que han sido completadas.
     * @return Una lista de tareas completadas.
     */
    @SuppressWarnings("unused")
    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    /**
     * Devuelve una representación en cadena del proyecto.
     * Incluye el nombre del proyecto y el número de tareas asociadas.
     * @return Una cadena que representa el proyecto.
     */
    @Override
    public String toString() {
        return name + " - " + tasks.size() + " tasks";
    }
}

