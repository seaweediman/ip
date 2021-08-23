package duke;

import duke.commands.*;

import java.util.ArrayList;

/**
 * Represents the Parser class which makes sense of what the user typed.
 */
public class Parser {

    /**
     * Method to parse and make sense what the user typed in.
     *
     * @param commandLine the user's input.
     * @param tasks       the TaskList that contains all the user's tasks.
     * @return Command command deciphered from the user input.
     * @throws DukeException If the user gives a bad input.
     */
    public Command parse(String commandLine, TaskList tasks) throws DukeException {
        String[] fullCommand = commandLine.split(" ");
        String command = fullCommand[0];
        String desc;

        switch (command) {
        case "bye":
            return new ExitCommand("exit");
        case "list":
            return new ListCommand("list");
        case "done":
            checkIndex(fullCommand.length);
            int doneIndex = Integer.parseInt(fullCommand[1]);
            checkIndex(doneIndex, tasks.size());
            return new DoneCommand("done", doneIndex);
        case "delete":
            checkIndex(fullCommand.length);
            int deleteIndex = Integer.parseInt(fullCommand[1]);
            checkIndex(deleteIndex, tasks.size());
            return new DeleteCommand("delete", deleteIndex);
        case "todo":
            StringBuilder todoBuilder = new StringBuilder();
            for (int i = 1; i < fullCommand.length; i++) {
                if (i != 1) {
                    todoBuilder.append(" ");
                }
                todoBuilder.append(fullCommand[i]);
            }
            desc = todoBuilder.toString();
            checkDesc(desc);
            return new AddTodoCommand(desc);
        case "deadline":
            StringBuilder deadlineBuilder = new StringBuilder();
            StringBuilder byBuilder = new StringBuilder();
            String by;
            boolean byFound = false;

            for (int i = 1; i < fullCommand.length; i++) {
                if (byFound) {
                    if (!byBuilder.toString().equals("")) {
                        byBuilder.append(" ");
                    }
                    byBuilder.append(fullCommand[i]);
                } else {
                    if (i == 1) {
                        deadlineBuilder.append(fullCommand[i]);
                    } else if (fullCommand[i].equals("/by")) {
                        byFound = true;
                    } else {
                        deadlineBuilder.append(" ");
                        deadlineBuilder.append(fullCommand[i]);
                    }
                }
            }
            desc = deadlineBuilder.toString();
            by = byBuilder.toString();

            checkDesc(desc);
            return new AddDeadlineCommand(desc, by);
        case "event":
            StringBuilder eventBuilder = new StringBuilder();
            StringBuilder atBuilder = new StringBuilder();
            String at;
            boolean atFound = false;

            for (int i = 1; i < fullCommand.length; i++) {
                if (atFound) {
                    if (!atBuilder.toString().equals("")) {
                        atBuilder.append(" ");
                    }
                    atBuilder.append(fullCommand[i]);
                } else {
                    if (i == 1) {
                        eventBuilder.append(fullCommand[i]);
                    } else if (fullCommand[i].equals("/at")) {
                        atFound = true;
                    } else {
                        eventBuilder.append(" ");
                        eventBuilder.append(fullCommand[i]);
                    }
                }
            }
            desc = eventBuilder.toString();
            at = atBuilder.toString();
            checkDesc(desc);
            return new AddEventCommand(desc, at);
        case "find":
            StringBuilder keywordBuilder = new StringBuilder();
            for (int i = 1; i < fullCommand.length; i++) {
                if (i != 1) {
                    keywordBuilder.append(" ");
                }
                keywordBuilder.append(fullCommand[i]);
            }
            return new FindCommand("find", keywordBuilder.toString());
        }
        return new UnknownCommand("unknown");
    }

    /**
     * Checks whether there is a description in the command line. Throws DukeException if description is missing.
     *
     * @param test the description the user input.
     * @throws DukeException if there is no description in the command line.
     */
    public static void checkDesc(String test) throws DukeException {
        if (test.equals("")) {
            throw new DukeException("The description is empty");
        }
    }

    /**
     * Checks if the user input an index number when using the commands: done, delete.
     *
     * @param length the number of words in the command line that the user input.
     * @throws DukeException If the user did not enter an index number.
     */
    public static void checkIndex(int length) throws DukeException {
        if (length == 1) {
            throw new DukeException("Please give an index number");
        }
    }

    /**
     * Checks if the index number that the user input is valid.
     *
     * @param i            the index number that the user input.
     * @param lengthOfList the length of the user's TaskList.
     * @throws DukeException If the index number is negative or more than length of the TaskList.
     */
    public static void checkIndex(int i, int lengthOfList) throws DukeException {
        if (i <= 0) {
            throw new DukeException("Please give an index number > 0");
        } else if (i > lengthOfList) {
            throw new DukeException("Maximum index number is " + lengthOfList);
        }
    }
}


//            ArrayList<String> keywords = new ArrayList<>();
//            for (int i = 1; i < fullCommand.length; i++) {
//                keywords.add(fullCommand[i]);
//            }