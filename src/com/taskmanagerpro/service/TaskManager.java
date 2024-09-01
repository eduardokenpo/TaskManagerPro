package com.taskmanagerpro.service;

import com.taskmanagerpro.model.Project;
import com.taskmanagerpro.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Project> projects;

    public TaskManager() {
        this.projects = new ArrayList<>();
    }

    public boolean addProject(Project project) {
        // Normalizar el nombre del proyecto para evitar duplicados con diferentes mayúsculas/minúsculas
        String normalizedProjectName = java.text.Normalizer.normalize(project.getName(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        // Verificar si ya existe un proyecto con el mismo nombre
        for (Project existingProject : projects) {
            String normalizedExistingName = java.text.Normalizer.normalize(existingProject.getName(), java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toLowerCase();
            if (normalizedExistingName.equals(normalizedProjectName)) {
                return false; // El proyecto ya existe, retornar false
            }
        }

        // Si no existe, añadir el proyecto y retornar true
        projects.add(project);
        return true;
    }

    public List<Project> getProjects() {
        return projects;
    }
    /**
     * Busca un proyecto por su nombre.
     * Normaliza el nombre del proyecto ingresado y lo compara con los nombres normalizados de los proyectos almacenados.
     * @param name El nombre del proyecto a buscar.
     * @return El proyecto si se encuentra, o null si no existe.
     */

    public Project getProjectByName(String name) {
        String normalizedInput = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
        for (Project project : projects) {
            String normalizedProjectName = java.text.Normalizer.normalize(project.getName(), java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toLowerCase();
            if (normalizedProjectName.equals(normalizedInput)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Elimina un proyecto por su nombre.
     * @param projectName El nombre del proyecto a eliminar.
     * @return true si el proyecto fue eliminado, false si no se encontró el proyecto.
     */
    public boolean removeProject(String projectName) {
        Project project = getProjectByName(projectName);
        if (project != null) {
            projects.remove(project);
            return true;
        }
        return false;
    }

    /**
     * Elimina una tarea específica dentro de un proyecto.
     * @param projectName El nombre del proyecto que contiene la tarea.
     * @param taskTitle El título de la tarea a eliminar.
     * @return true si la tarea fue eliminada, false si no se encontró la tarea o el proyecto.
     */
    public boolean removeTaskFromProject(String projectName, String taskTitle) {
        Project project = getProjectByName(projectName);
        if (project != null) {
            String normalizedTaskTitle = java.text.Normalizer.normalize(taskTitle, java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "")
                    .toLowerCase();
            for (Task task : project.getTasks()) {
                if (java.text.Normalizer.normalize(task.getTitle(), java.text.Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", "")
                        .toLowerCase().equals(normalizedTaskTitle)) {
                    project.getTasks().remove(task);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Guarda la lista de proyectos en un archivo.
     * Utiliza la serialización para escribir la lista completa de proyectos en el archivo especificado.
     * @param filePath Ruta del archivo donde se guardarán los datos.
     * @throws IOException Si ocurre un error durante el guardado.
     */

    public void saveData(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(projects);
        }
    }

    /**
     * Carga la lista de proyectos desde un archivo.
     * Utiliza la deserialización para leer la lista de proyectos desde el archivo especificado.
     * @param filePath Ruta del archivo desde donde se cargarán los datos.
     * @throws IOException Si ocurre un error durante la carga.
     * @throws ClassNotFoundException Si la clase Project no puede ser encontrada.
     */
    public void loadData(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            projects = (List<Project>) ois.readObject();
        }
    }
}

