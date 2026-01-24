import java.util.ArrayList;
import java.util.Scanner;

public class Snorax {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String greetings = "Hello! I'm Snorax";
        String question = "What can I do for you?";
        String bye = "Goodnight. Time for a nap!";

        // Print initial greeting
        System.out.println(line + "\n"
                            + greetings + "\n"
                            + question + "\n"
                            + line + "\n");

        Scanner sc = new Scanner(System.in);

        // List to store echos
        ArrayList<Task> tasks = new ArrayList<>();

        // Loop continuously to accept input
        while (true) {
            try {
                String input = sc.nextLine(); // Read user input

                System.out.println(line); // print divider before response

                if (input.equals("bye")) {
                    System.out.println(bye);
                    System.out.println(line);
                    break; // Exit loop
                } else if (input.equals("list")) {
                    // output the list
                    for (int i = 1; i <= tasks.size(); i++) {
                        System.out.println(i +  ". " + tasks.get(i - 1).toString());
                    }
                    System.out.println(line);
                } else if (input.startsWith("unmark ")) {
                    String[] splitted = input.split(" ");
                    int index = Integer.parseInt(splitted[1]);
                    tasks.get(index - 1).markAsNotDone();
                    System.out.println("ok marked as undone u lazy:\n" 
                                    + tasks.get(index - 1).toString());
                    System.out.println(line);
                } else if (input.startsWith("mark ")) {
                    String[] splitted = input.split(" ");
                    int index = Integer.parseInt(splitted[1]);
                    tasks.get(index - 1).markAsDone();
                    System.out.println("good job, marked as done:\n" 
                                    + tasks.get(index - 1).toString());
                    System.out.println(line);
                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5);
                    if (description.isEmpty()) {
                        throw new SnoraxException("cannot be empty bro");
                    }
                    Task task = new Todo(description);
                    tasks.add(task);
                    System.out.println("ok added this task liao:");
                    System.out.println(" " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new SnoraxException("cannot be empty bro");
                    }
                    Task task = new Deadline(parts[0], parts[1]);
                    tasks.add(task);
                    System.out.println("ok added this task liao:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event ")) {
                    String remaining = input.substring(6);
                    String[] parts = remaining.split(" /from | /to ");
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new SnoraxException("cannot be empty bro");
                    }
                    Task task = new Event(parts[0], parts[1], parts[2]);
                    tasks.add(task);
                    System.out.println("ok added this task liao:");
                    System.out.println("  " + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else {
                    throw new SnoraxException("idk what that means, goodnight");
                }
            } catch (SnoraxException e) {
                System.out.println(e.getMessage());
                System.out.println(line);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("give valid index");
                System.out.println(line);
            } catch (NumberFormatException e) {
                System.out.println("give valid index");
                System.out.println(line);
            } catch (Exception e) {
                System.out.println("OOPS!!! Something went wrong!");
                System.out.println(line);
            }
        }
        sc.close(); //close the scanner
    }
}
