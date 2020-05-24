package src.action.actionQueue;

import org.junit.Test;
import src.action.Action;
import src.action.PlayerAction;
import src.action.PlayerActionSelectSet;

import static org.junit.Assert.*;
import static src.action.ActionType.*;

public class ActionQueueTest {
    @Test
    public void testActionQueueLowLow() {
        SynchronisedActionQueue actions = new SynchronisedActionQueue();
        actions.addAction(new PlayerAction(LEAVE_GAME, 0));
        actions.addAction(new PlayerActionSelectSet(SELECT_SET, 1, getExampleCardPositions()));
        assertSame(actions.getNext().getType(), LEAVE_GAME);
        assertSame(actions.getNext().getType(), SELECT_SET);
    }

    @Test
    public void testActionQueueHighHigh() {
        SynchronisedActionQueue actions = new SynchronisedActionQueue();
        actions.addAction(new Action(DRAW_THREE));
        actions.addAction(new Action(SHOW_SET));
        assertSame(actions.getNext().getType(), DRAW_THREE);
        assertSame(actions.getNext().getType(), SHOW_SET);
    }

    @Test
    public void testActionQueueLowHigh() {
        SynchronisedActionQueue actions = new SynchronisedActionQueue();
        actions.addAction(new PlayerAction(REQUEST_SHOW_SET, 0));
        actions.addAction(new Action(SHOW_SET));
        assertSame(actions.getNext().getType(), SHOW_SET);
        assertSame(actions.getNext().getType(), REQUEST_SHOW_SET);
    }

    @Test
    public void testActionQueueHighLow() {
        SynchronisedActionQueue actions = new SynchronisedActionQueue();
        actions.addAction(new Action(SHOW_SET));
        actions.addAction(new PlayerAction(LEAVE_GAME, 1));
        assertSame(actions.getNext().getType(), SHOW_SET);
        assertSame(actions.getNext().getType(), LEAVE_GAME);
    }

    private int[] getExampleCardPositions() {
        return new int[]{1, 2, 3};
    }
}
