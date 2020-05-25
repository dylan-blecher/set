package src.action.actionQueue;

import src.action.Action;

import java.time.Instant;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// TODO: if i make a replay function, how does this get re-created?

// To avoid the producer-consumer problem, we can only allow one action to take place at a time.
// So, let's keep a to do list of actions.
// Actions are prioritised first by if they are requestType and then by the order (time) in which the action was added
// Why do we prioritise requestType? completed requests get first priority because they've been on the queue for ages
// (ever since someone first requested them)
public class SynchronisedActionQueue {
    private final Queue<ActionQueueEntry> pq = new PriorityQueue<>(new ActionComparator());
    public Action getNext() {
        synchronized (pq) {
            try {
                if (pq.size() == 0) pq.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                return null;
            }

            return pq.remove().getAction();
        }
    }
    public void addAction(Action action) {
        synchronized (pq) {
            pq.add(new ActionQueueEntry(action));
            if (pq.size() == 1) pq.notify();
        }
    }
}

// put RequestType first, if it's the same, time first.
class ActionComparator implements Comparator<ActionQueueEntry> {
    @Override
    public int compare(ActionQueueEntry m1, ActionQueueEntry m2) {
        // compare type
        // .. TODO: make function that gets action priority!
        int typeComparison = m2.getAction().getType().getPriority().compareTo(m1.getAction().getType().getPriority());
        if (typeComparison != 0) return typeComparison;

        // compare time
        long timeComparison = m1.getEnqueueTime() - m2.getEnqueueTime();
        if (timeComparison > 0) return -1;
        if (timeComparison < 0) return 1;
        return 0;
    }

}

class ActionQueueEntry {
    private final Action action;
    private final long enqueueTime;

    ActionQueueEntry(Action action) {
        this.action = action;
        this.enqueueTime = Instant.now().toEpochMilli();
    }

    public long getEnqueueTime() {
        return enqueueTime;
    }

    public Action getAction() {
        return action;
    }
}