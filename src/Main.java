import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    static String filePath = "src/Tasks.json";
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Load existing tasks from JSON
        List<Task> tasks = loadTasks();

        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter task description: ");
        String taskDescription = scanner.nextLine();

        int newId = tasks.size() + 1;
        Task task = new Task(newId, taskName, taskDescription);

        tasks.add(task);

        // Save back to JSON file
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(tasks, writer);
        }

        System.out.println("Task saved to JSON!");
    }

    private static List<Task> loadTasks() {
        try (Reader reader = new FileReader(filePath)) {
            Task[] taskArray = gson.fromJson(reader, Task[].class);
            if (taskArray != null) {
                return new ArrayList<>(Arrays.asList(taskArray));
            }
        } catch (IOException e) {
            // file might not exist on first run → return empty list
        }
        return new ArrayList<>();
    }
}
