package groupwork_hub;

import java.util.HashSet;

public class User {
    private String name;
    private final HashSet<Group> groups = new HashSet<>();
    private final HashSet<Task> proposedTasks = new HashSet<>();
    private final HashSet<Task> approvedTasks = new HashSet<>();
    private final HashSet<Task> ongoingTasks = new HashSet<>();
    private final HashSet<Task> participatedTasks = new HashSet<>();

    // concerning the user
    public User(String name) {
        this.name = name;
    }

    public void changeName(String newName) {
        name = newName;
    }

    // concerning new task
    public void proposeTask(Group group, String title, String description, int maxParticipators) {
        Task task = new Task(title, description, maxParticipators);
        proposedTasks.add(task);
        group.addTask(task);
    }

    public boolean approveTask(Group group, Task task) {
        if (!group.approveTask(task, this)) {
            System.out.println("You have already approved this task, or the task does not exist.");
            return false;
        }
        approvedTasks.add(task);
        return true;
    }

    // concerning groups
    public boolean joinGroup(Group group) {
        if (!group.addUser(this)) {
            System.out.println("You are already member of this group.");
            return false;
        }
        groups.add(group);
        return true;
    }

    public void leaveGroup(Group group) {
        for (Task task: approvedTasks) {
            task.cancelApproveBy(this);
        }

        for (Task task: ongoingTasks) {
            task.removeParticipator(this);
        }

        group.deleteUser(this);
    }

    // concerning participation in tasks
    public void participateIn(Task task) {
        if (task.addParticipator(this)) {
            participatedTasks.add(task);
        } else {
            System.out.println("This task is full.");
        }
    }

    public void leaveTask(Task task) {
        if (ongoingTasks.contains(task)) {
            task.removeParticipator(this);
            ongoingTasks.remove(task);
            System.out.println("User " + this + " left task " + task);
        } else if (participatedTasks.contains(task)) {
            System.out.println("You cannot leave a task in which you have finished your part!");
        } else {
            System.out.println("You are not in this task, cannot leave it!");
        }
    }

    // concerning completeness of task
    public boolean completeTask(Task task) {
        return task.finishTaskBy(this);
    }
}
