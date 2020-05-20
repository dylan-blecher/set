package src.action.actionQueue;

import org.junit.jupiter.api.Test;
import src.action.ActionSelectSet;

import static org.junit.jupiter.api.Assertions.assertSame;
import static src.action.ActionType.*;

class ActionQueueTest {
    @Test
    public void testActionQueueLowLow() {
        ActionQueue actions = new ActionQueue();
        actions.addAction(new src.action.Action(LEAVE_GAME, 0));
        actions.addAction(new ActionSelectSet(SELECT_SET, 1, getExampleCardPositions()));
        assertSame(actions.getNext().getType(), LEAVE_GAME);
        assertSame(actions.getNext().getType(), SELECT_SET);
    }

    @Test
    public void testActionQueueHighHigh() {
        ActionQueue actions = new ActionQueue();
        actions.addAction(new src.action.Action(DRAW_THREE, 0));
        actions.addAction(new src.action.Action(SHOW_SET, 1));
        assertSame(actions.getNext().getType(), DRAW_THREE);
        assertSame(actions.getNext().getType(), SHOW_SET);
    }

    @Test
    public void testActionQueueLowHigh() {
        ActionQueue actions = new ActionQueue();
        actions.addAction(new src.action.Action(REQUEST_SHOW_SET, 0));
        actions.addAction(new src.action.Action(SHOW_SET, 1));
        assertSame(actions.getNext().getType(), SHOW_SET);
        assertSame(actions.getNext().getType(), REQUEST_SHOW_SET);
    }

    @Test
    public void testActionQueueHighLow() {
        ActionQueue actions = new ActionQueue();
        actions.addAction(new src.action.Action(SHOW_SET, 0));
        actions.addAction(new src.action.Action(LEAVE_GAME, 1));
        assertSame(actions.getNext().getType(), SHOW_SET);
        assertSame(actions.getNext().getType(), LEAVE_GAME);
    }

    private int[] getExampleCardPositions() {
        return new int[]{1, 2, 3};
    }
}
