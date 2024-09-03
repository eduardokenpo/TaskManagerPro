package com.taskmanagerpro.ui;
import com.taskmanagerpro.model.Project;
import com.taskmanagerpro.model.Task;
import com.taskmanagerpro.service.TaskManager;

import java.io.IOException;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class TaskManagerUI {
    private static final String INVALID_CHOICE_MESSAGE = "Invalid choice, please try again.";

    private final TaskManager taskManager;
    private final Scanner scanner;

    public TaskManagerUI(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }

    private String getNormalizedUserInput(String prompt) {
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        return java.text.Normalizer.normalize(userInput, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            int choice = getUserChoice();
            exit = handleUserChoice(choice);
        }
    }

    private int getUserChoice() {
        showMainMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline para evitar problemas con scanner.nextLine()
        return choice;
    }

    private boolean handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> createProject();
            case 2 -> addTaskToProject();
            case 3 -> listProjects();
            case 4 -> listTasks();
            case 5 -> markTaskAsCompleted();
            case 6 -> removeProject();
            case 7 -> removeTaskFromProject();
            case 8 -> {
                exitApplication();
                return true;
            }
            default -> System.out.println(INVALID_CHOICE_MESSAGE);
        }
        return false;
    }

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

    private String promptForProjectName() {
        return getNormalizedUserInput("Enter project name: ");
    }

    private void createProject() {
        String projectName = promptForProjectName();
        Project project = new Project(projectName);

        if (taskManager.addProject(project)) {
            System.out.println("Project '" + projectName + "' created.");
        } else {
            System.out.println(ErrorMessage.PROJECT_EXISTS.getMessage());
        }
    }

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
            System.out.println(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }
    }

    private void listProjects() {
        System.out.println("Projects:");
        for (Project project : taskManager.getProjects()) {
            System.out.println(project);
        }
    }

    private void listTasks() {
        String projectName = promptForProjectName();
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            System.out.println("Tasks in project '" + projectName + "':");
            for (Task task : project.getTasks()) {
                System.out.println(task);
            }
        } else {
            System.out.println(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }
    }

    private void markTaskAsCompleted() {
        String projectName = getNormalizedUserInput("Enter project name: ");
        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            String taskTitle = getNormalizedUserInput("Enter task title to mark as completed: ");

            boolean taskFound = false;
            for (Task task : project.getTasks()) {
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
                System.out.println(ErrorMessage.TASK_NOT_FOUND.getMessage());
            }
        } else {
            System.out.println(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }
    }

    private void removeProject() {
        String projectName = promptForProjectName();
        boolean removed = taskManager.removeProject(projectName);
        if (removed) {
            System.out.println("Project '" + projectName + "' removed.");
        } else {
            System.out.println(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }
    }

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
                System.out.println(ErrorMessage.TASK_NOT_FOUND.getMessage());
            }
        } else {
            System.out.println(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }
    }

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



