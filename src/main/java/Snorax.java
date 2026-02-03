import java.util.ArrayList;
import java.util.Scanner;

public class Snorax {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String greetings = "Hello! I'm Snorax";
        String question = "What can I do for you?";
        String bye = "Goodnight. Time for a nap!";

        Storage storage = new Storage("./data/snorax.txt");
        ArrayList<Task> tasks = new ArrayList<>();
        try { tasks = storage.load(); } catch (SnoraxException e) { }

        // Print initial greeting
        System.out.println(line + "\n"
                            + greetings + "\n"
                            + question + "\n"
                            + line + "\n");

        Scanner sc = new Scanner(System.in);


        // Loop continuously to accept input
        while (true) {
            try {
                String input = sc.nextLine(); // Read user input

                System.out.println(line); // print divider before response

                Command command = Command.parse(input);

                switch (command) {
                    case BYE:
                        System.out.println(bye);
                        System.out.println(line);
                        sc.close();
                        return;

                    case LIST:
                        // output the list
                        for (int i = 1; i <= tasks.size(); i++) {
                            System.out.println(i +  ". " + tasks.get(i - 1).toString());
                        }
                        System.out.println(line);
                        break;

                    case DELETE:
                        String[] splitted = input.split(" ");
                        int index = Integer.parseInt(splitted[1]);
                        Task removedTask = tasks.get(index - 1);
                        tasks.remove(index - 1);
                        storage.save(tasks);
                        System.out.println("done bro removed task:");
                        System.out.println("  " + removedTask.toString());
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case UNMARK:
                        String[] unmarkSplit = input.split(" ");
                        int unmarkIndex = Integer.parseInt(unmarkSplit[1]);
                        tasks.get(unmarkIndex - 1).markAsNotDone();
                        storage.save(tasks);
                        System.out.println("ok marked as undone u lazy:\n" 
                                        + tasks.get(unmarkIndex - 1).toString());
                        System.out.println(line);
                        break;

                    case MARK:
                        String[] markSplit = input.split(" ");
                        int markIndex = Integer.parseInt(markSplit[1]);
                        tasks.get(markIndex - 1).markAsDone();
                        storage.save(tasks);
                        System.out.println("good job, marked as done:\n" 
                                        + tasks.get(markIndex - 1).toString());
                        System.out.println(line);
                        break;

                    case TODO:
                        String description = input.substring(5);
                        if (description.isEmpty()) {
                            throw new SnoraxException("cannot be empty bro");
                        }
                        Task task = new Todo(description);
                        tasks.add(task);
                        storage.save(tasks);
                        System.out.println("ok added this task liao:");
                        System.out.println(" " + task);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case DEADLINE:
                        String[] parts = input.substring(9).split(" /by ");
                        if (parts.length < 2 || parts[0].trim().isEmpty()) {
                            throw new SnoraxException("cannot be empty bro");
                        }
                        Task deadlineTask = new Deadline(parts[0], parts[1]);
                        tasks.add(deadlineTask);
                        storage.save(tasks);
                        System.out.println("ok added this task liao:");
                        System.out.println("  " + deadlineTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case EVENT:
                        String remaining = input.substring(6);
                        String[] eventParts = remaining.split(" /from | /to ");
                        if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) {
                            throw new SnoraxException("cannot be empty bro");
                        }
                        Task eventTask = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        tasks.add(eventTask);
                        storage.save(tasks);
                        System.out.println("ok added this task liao:");
                        System.out.println("  " + eventTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case UNKNOWN:
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
    }
}
