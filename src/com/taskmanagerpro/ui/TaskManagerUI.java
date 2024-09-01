package com.taskmanagerpro.ui;

import com.taskmanagerpro.model.Project;
import com.taskmanagerpro.model.Task;
import com.taskmanagerpro.service.TaskManager;

import java.io.IOException;
import java.util.Scanner;

/**
 * La clase TaskManagerUI se encarga de manejar la interfaz de usuario de la aplicación TaskManagerPro.
 * Permite la interacción con el usuario para crear proyectos, agregar tareas, listar proyectos y tareas,
 * marcar tareas como completadas y guardar los datos antes de salir.
 */

public class TaskManagerUI {
    private static final String INVALID_CHOICE_MESSAGE = "Invalid choice, please try again.";

    private TaskManager taskManager;
    private Scanner scanner;

    /**
     * Constructor de TaskManagerUI.
     * @param taskManager El gestor de tareas que maneja la lógica de los proyectos y tareas.
     */

    public TaskManagerUI(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Captura y normaliza la entrada del usuario.
     * Normaliza los caracteres eliminando acentos y convirtiendo el texto a minúsculas.
     * @param prompt Mensaje que se mostrará al usuario.
     * @return Entrada del usuario normalizada.
     */

    private String getNormalizedUserInput(String prompt) {
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        return java.text.Normalizer.normalize(userInput, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
    /**
     * Método principal que inicia el ciclo de la interfaz de usuario.
     * Presenta un menú principal y maneja las opciones seleccionadas por el usuario.
     */

    public void start() {
        boolean exit = false;
        while (!exit) {
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
    }

    private int getUserChoice() {
        showMainMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline para evitar problemas con scanner.nextLine()
        return choice;
    }
    /**
     * Maneja la opción seleccionada por el usuario y ejecuta la acción correspondiente.
     * @param choice Opción seleccionada por el usuario en el menú principal.
     */

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> createProject();
            case 2 -> addTaskToProject();
            case 3 -> listProjects();
            case 4 -> listTasks();
            case 5 -> markTaskAsCompleted();
            case 6 -> removeProject();
            case 7 -> removeTaskFromProject();
            case 8 -> exitApplication();
            default -> System.out.println(INVALID_CHOICE_MESSAGE);
        }
    }
    /**
     * Muestra el menú principal de la aplicación.
     */

    private void showMainMenu() {
        System.out.println("\nTaskManagerPro - Main Menu");
        System.out.println("1. Create Project");
        System.out.println("2. Add Task to Project");
        System.out.println("3. List Projects");
        System.out.println("4. List Tasks by Project");
        System.out.println("5. Mark Task as Completed");
        System.out.println("6. Remove Project");
        System.out.println("7. Remove Task from Project");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }
    /**
     * Solicita al usuario el nombre del proyecto y lo normaliza.
     * @return El nombre del proyecto normalizado.
     */

    private String promptForProjectName() {
        return getNormalizedUserInput("Enter project name: ");
    }
    /**
     * Crea un nuevo proyecto basado en el nombre ingresado por el usuario.
     * Añade el proyecto al gestor de tareas y confirma su creación.
     */

    private void createProject() {
        String projectName = promptForProjectName();
        Project project = new Project(projectName);

        if (taskManager.addProject(project)) {
            System.out.println("Project '" + projectName + "' created.");
        } else {
            System.out.println("Error: A project with the name '" + projectName + "' already exists. Please choose a different name.");
        }
    }

    /**
     * Agrega una nueva tarea a un proyecto existente.
     * Solicita al usuario el título y la descripción de la tarea, y la añade al proyecto seleccionado.
     */

    private void addTaskToProject() {
        String projectName = promptForProjectName();
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            System.out.print("Enter task title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Enter task description: ");
            String description = scanner.nextLine().trim();
            Task task = new Task(title, description);
            project.addTask(task);
            System.out.println("Task added to project '" + projectName + "'.");
        } else {
            System.out.println("Project not found.");
        }
    }
    /**
     * Lista todos los proyectos existentes.
     * Muestra el nombre y el número de tareas de cada proyecto.
     */

    private void listProjects() {
        System.out.println("Projects:");
        for (Project project : taskManager.getProjects()) {
            System.out.println(project);
        }
    }
    /**
     * Lista las tareas de un proyecto específico.
     * Solicita el nombre del proyecto, luego muestra todas las tareas asociadas a ese proyecto.
     */

    private void listTasks() {
        String projectName = promptForProjectName();
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            System.out.println("Tasks in project '" + projectName + "':");
            for (Task task : project.getTasks()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Project not found.");
        }
    }
    /**
     * Marca una tarea específica como completada.
     * Solicita el nombre del proyecto y el título de la tarea, luego actualiza el estado de la tarea.
     */

    private void markTaskAsCompleted() {
        String projectName = getNormalizedUserInput("Enter project name: ");
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            String taskTitle = getNormalizedUserInput("Enter task title to mark as completed: ");

            boolean taskFound = false;
            for (Task task : project.getTasks()) {
                // Normalizar tanto el título de la tarea almacenada como el título ingresado
                String normalizedTaskTitle = java.text.Normalizer.normalize(task.getTitle(), java.text.Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", "")
                        .toLowerCase();
                if (normalizedTaskTitle.equals(taskTitle)) {
                    task.setCompleted(true);
                    System.out.println("Task '" + taskTitle + "' marked as completed.");
                    taskFound = true;
                    break;
                }
            }
            if (!taskFound) {
                System.out.println("Task not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    }

    /**
     * Elimina un proyecto.
     * Solicita al usuario el nombre del proyecto y lo elimina si existe.
     */

    private void removeProject() {
        String projectName = promptForProjectName();
        boolean removed = taskManager.removeProject(projectName);
        if (removed) {
            System.out.println("Project '" + projectName + "' removed.");
        } else {
            System.out.println("Project not found.");
        }
    }

    /**
     * Elimina una tarea dentro de un proyecto.
     * Solicita el nombre del proyecto y el título de la tarea, luego elimina la tarea si existe.
     */

    private void removeTaskFromProject() {
        String projectName = promptForProjectName();
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            System.out.print("Enter task title to remove: ");
            String taskTitle = scanner.nextLine().trim();
            boolean removed = taskManager.removeTaskFromProject(projectName, taskTitle);
            if (removed) {
                System.out.println("Task '" + taskTitle + "' removed from project '" + projectName + "'.");
            } else {
                System.out.println("Task not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    }
    /**
     * Guarda los datos y cierra la aplicación.
     * Asegura que todos los proyectos y tareas se guarden antes de salir.
     */

    private void exitApplication() {
        try {
            taskManager.saveData("taskmanager.dat");  // Guarda los datos en el archivo antes de salir
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
        System.out.println("Exiting the application...");
        System.exit(0);
    }
}


