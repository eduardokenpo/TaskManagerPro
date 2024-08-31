package com.taskmanagerpro;

import com.taskmanagerpro.service.TaskManager;
import com.taskmanagerpro.ui.TaskManagerUI;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación TaskManagerPro.
 * Se encarga de cargar los datos, iniciar la interfaz de usuario y guardar los datos antes de salir.
 */
public class Main {
    private static final String DATA_FILE = "taskmanager.dat";

    /**
     * Método principal de la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Intentar cargar los datos guardados desde el archivo
        try {
            taskManager.loadData(DATA_FILE);
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }

        // Iniciar la interfaz de usuario
        TaskManagerUI ui = new TaskManagerUI(taskManager);
        ui.start();

        // Guardar los datos antes de salir
        try {
            taskManager.saveData(DATA_FILE);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}
