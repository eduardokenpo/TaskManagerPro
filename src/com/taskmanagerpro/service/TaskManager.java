package com.taskmanagerpro.service;
import com.taskmanagerpro.model.Project;
import com.taskmanagerpro.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class TaskManager {
    private List<Project> projects;

    public TaskManager() {
        this.projects = new ArrayList<>();
    }

    //  método privado para normalizar cadenas
    private String normalize(String input) {
        return java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public boolean addProject(Project project) {
        String normalizedProjectName = normalize(project.getName());

        for (Project existingProject : projects) {
            String normalizedExistingName = normalize(existingProject.getName());
            if (normalizedExistingName.equals(normalizedProjectName)) {
                return false;
            }
        }

        projects.add(project);
        return true;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getProjectByName(String name) {
        String normalizedInput = normalize(name);
        for (Project project : projects) {
            String normalizedProjectName = normalize(project.getName());
            if (normalizedProjectName.equals(normalizedInput)) {
                return project;
            }
        }
        return null;
    }

    public boolean removeProject(String projectName) {
        Project project = getProjectByName(projectName);
        if (project != null) {
            projects.remove(project);
            return true;
        }
        return false;
    }

    public boolean removeTaskFromProject(String projectName, String taskTitle) {
        Project project = getProjectByName(projectName);
        if (project != null) {
            String normalizedTaskTitle = normalize(taskTitle);
            for (Task task : project.getTasks()) {
                if (normalize(task.getTitle()).equals(normalizedTaskTitle)) {
                    project.getTasks().remove(task);
                    return true;
                }
            }
        }
        return false;
    }

    public void saveData(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(projects);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            projects = (List<Project>) ois.readObject();
        }
    }
}


