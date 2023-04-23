package groupwork_hub;

import java.util.HashSet;

public class Group {
    private final HashSet<User> members = new HashSet<>();
    private final HashSet<Task> proposedTasks = new HashSet<>();
    private final HashSet<Task> ongoingTasks = new HashSet<>();
    private double approveThreshold = 0.5;


    // concerning new tasks
    public void addTask(Task task) {
        proposedTasks.add(task);
    }

    public boolean approveTask(Task task, User user) {
        if (!proposedTasks.contains(task)) { return false; }

        if (!task.approvedBy(user)) { return false; }
        if (task.getApproveCount() > members.size() * approveThreshold) {
            proposedTasks.remove(task);
            ongoingTasks.add(task);
        }
        return true;
    }

    // concerning members
    public boolean addUser(User user) {
        return members.add(user);
    }
    public boolean deleteUser(User user) {
        return members.remove(user);
    }
}
