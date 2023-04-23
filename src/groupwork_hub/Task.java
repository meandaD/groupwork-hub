package groupwork_hub;

import java.util.HashMap;
import java.util.HashSet;

public class Task {
    private String title;
    private String description;
    private int maxParticipators;
    private final HashSet<User> approves = new HashSet<>();
    private final HashMap<User, Boolean> participators = new HashMap<>();

    public Task(String title, String description, int maxParticipators) {
        this.title = title;
        this.description = description;
        this.maxParticipators = maxParticipators;
    }

    // concerning approvals
    public int getApproveCount() {
        return approves.size();
    }

    public boolean approvedBy(User user) {
        if (approves.contains(user)) { return false; }

        approves.add(user);
        return true;
    }

    public boolean cancelApproveBy(User user) {
        return approves.remove(user);
    }

    // concerning participators
    public boolean addParticipator(User participator) {
        if (participators.size() < maxParticipators) {
            participators.put(participator, false);
            return true;
        }
        return false;
    }

    public boolean removeParticipator(User participator) {
        return participators.remove(participator);
    }

    // concerning completeness
    public boolean getCompleteness() {
        if (participators.size() < maxParticipators) { return false; }
        for (User participator: participators.keySet()) {
            if (!participators.get(participator)) { return false; }
        }

        return true;
    }

    public boolean finishTaskBy(User user) {
        return participators.replace(user, false, true);
    }
}
