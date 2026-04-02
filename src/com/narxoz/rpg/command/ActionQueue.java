package com.narxoz.rpg.command;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue {
    private final List<ActionCommand> queue = new ArrayList<>();

    public void enqueue(ActionCommand cmd) {
        // TODO: Add the command to the end of the queue.
        queue.add(cmd);
    }

    public void undoLast() {
        // TODO: Remove the last queued command without executing it.
        // Design question: should cmd.undo() be called, or is it simply removed?
        // For this assignment: just remove the last entry from the queue.
        // Hint: List has a remove(int index) method — think about which index is "last".
        queue.removeLast();
    }

    public void executeAll() {
        // TODO: Call execute() on every command in the queue, in order.
        // TODO: Clear the queue after all commands have run.
        // TODO: What should happen if the queue is empty?
        for (ActionCommand cmd : queue){
            cmd.execute();
        }
        queue.clear();
        System.out.println("All queued commands were executed");
    }

    public List<String> getCommandDescriptions() {
        // TODO: Return a snapshot list of descriptions for all queued commands.
        // TODO: Use cmd.getDescription() for each command.
        // Note: the returned list must be independent — modifying it must not affect the queue.
        return new ArrayList<>(queue.stream().map(ActionCommand::getDescription).toList());
    }
}
