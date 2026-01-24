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
            } else if (input.contains("unmark")) {
                String[] splitted = input.split(" ");
                int index = Integer.parseInt(splitted[1]);
                tasks.get(index - 1).markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:\n" 
                                + tasks.get(index - 1).toString());
                System.out.println(line);
            } else if (input.contains("mark")) {
                String[] splitted = input.split(" ");
                int index = Integer.parseInt(splitted[1]);
                tasks.get(index - 1).markAsDone();
                System.out.println("Nice! I've marked this task as done:\n" 
                                + tasks.get(index - 1).toString());
                System.out.println(line);
            } else {
                // Add and Echo the command if its not bye
                Task task = new Task(input);
                tasks.add(task);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }

        sc.close(); //close the scanner
    }
}
