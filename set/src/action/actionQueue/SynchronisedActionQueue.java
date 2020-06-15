package src.action.actionQueue;

import src.action.Action;
import src.action.ActionTypePriority;

import java.time.Instant;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author dylanblecher
 *
 * To avoid the producer-consumer problem, we can only allow one action to take place at a time.
 * So, let's keep a to-do-list of actions as a priority queue.
 * Actions are prioritised first by if they are requestType and then by the order (time) in which the action was added
 * Why do we prioritise requestType? completed requests get first priority because they've been on the queue for a while
 * already (ever since someone first requested them)... As soon as all players agree on a move, it should be
 * implemented immediately. Other moves would invalidate it.
 */
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

/**
 * compares 'actionQueueEntry's by their type and then (secondarily) time entered.
 * See ActionTypePriority.java
 * RequestType is highest priority. All others are equal.
 */
class ActionComparator implements Comparator<ActionQueueEntry> {
    @Override
    public int compare(ActionQueueEntry m1, ActionQueueEntry m2) {
        // compare type
        int typeComparison = m2.getPriority().compareTo(m1.getPriority());
        if (typeComparison != 0) return typeComparison;

        // compare time
        long timeComparison = m1.getEnqueueTime() - m2.getEnqueueTime();
        if (timeComparison > 0) return -1;
        if (timeComparison < 0) return 1;
        return 0;
    }

}

/**
 * Entries for the ActionQueue, storing time entered and action priority (within action)
 */
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

    public ActionTypePriority getPriority() {
        return action.getType().getPriority();
    }
}