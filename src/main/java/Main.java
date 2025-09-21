import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<TaskTracker> taskList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        int nextId = 1;

        File file = new File("tasks.json");
        if(file.exists()){
            try {
                taskList = mapper.readValue(file, new TypeReference<ArrayList<TaskTracker>>(){});
                for(TaskTracker t : taskList){
                    if(t.getId() >= nextId) nextId = t.getId() + 1;
                }
            } catch (IOException e) {
                System.out.println("Error reading tasks: " + e.getMessage());
            }
        }

        if(args.length == 0){
            System.out.println("Usage: java Main <command> [parameters]");
            return;
        }

        String command = args[0].toLowerCase();

        switch (command){
            case "add":
                if(args.length < 3){
                    System.out.println("Usage: java Main add <description> <status>");
                    return;
                }
                String description = args[1];
                String status = args[2].toLowerCase();
                if(!status.equals("todo") && !status.equals("in-progress") && !status.equals("done")){
                    System.out.println("Invalid status. Use: todo, in-progress, done");
                    return;
                }

                TaskTracker newTask = new TaskTracker(description, status);
                newTask.setId(nextId++);
                newTask.setCreatedAt(LocalDateTime.now());
                newTask.setUpdatedAt(LocalDateTime.now());
                taskList.add(newTask);
                System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
                break;

            case "update":
                if(args.length < 3){
                    System.out.println("Usage: java Main update <id> <new description>");
                    return;
                }
                int updateId = Integer.parseInt(args[1]);
                String newDesc = args[2];
                TaskTracker tUpdate = findTaskById(taskList, updateId);
                if(tUpdate == null){
                    System.out.println("Task not found.");
                    return;
                }
                tUpdate.setName(newDesc);
                tUpdate.setUpdatedAt(LocalDateTime.now());
                System.out.println("Task updated successfully.");
                break;

            case "delete":
                if(args.length < 2){
                    System.out.println("Usage: java Main delete <id>");
                    return;
                }
                int deleteId = Integer.parseInt(args[1]);
                TaskTracker tDelete = findTaskById(taskList, deleteId);
                if(tDelete == null){
                    System.out.println("Task not found.");
                    return;
                }
                taskList.remove(tDelete);
                System.out.println("Task deleted successfully.");
                break;

            case "mark-done":
            case "mark-in-progress":
                if(args.length < 2){
                    System.out.println("Usage: java Main " + command + " <id>");
                    return;
                }
                int markId = Integer.parseInt(args[1]);
                TaskTracker tMark = findTaskById(taskList, markId);
                if(tMark == null){
                    System.out.println("Task not found.");
                    return;
                }
                if(command.equals("mark-done")) tMark.setStatus("done");
                else tMark.setStatus("in-progress");
                tMark.setUpdatedAt(LocalDateTime.now());
                System.out.println("Task status updated successfully.");
                break;

            case "list":
                if(args.length == 1){
                    listTasks(taskList, null); // list all
                } else {
                    listTasks(taskList, args[1].toLowerCase()); // filter by status
                }
                break;

            default:
                System.out.println("Unknown command.");
                break;
        }

        // Save JSON
        try{
            mapper.writeValue(new File("tasks.json"), taskList);
        } catch (IOException e){
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static TaskTracker findTaskById(ArrayList<TaskTracker> tasks, int id){
        for(TaskTracker t : tasks){
            if(t.getId() == id) return t;
        }
        return null;
    }

    private static void listTasks(ArrayList<TaskTracker> tasks, String status){
        for(TaskTracker t : tasks){
            if(status == null || t.getStatus().equals(status)){
                System.out.println("ID: " + t.getId() + " | " + t.getName() + " | Status: " + t.getStatus());
            }
        }
    }
}
