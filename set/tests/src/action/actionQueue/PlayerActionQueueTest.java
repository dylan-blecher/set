package src.action.actionQueue;

import org.junit.jupiter.api.Test;
import src.action.ActionSelectSet;

import static org.junit.Assert.assertSame;
import static src.action.ActionType.*;


class ActionQueueTest {
    @Test
    public void testActionQueueLowLow() {
        ActionQueue.addAction(new src.action.Action(LEAVE_GAME, 0));
        ActionQueue.addAction(new ActionSelectSet(SELECT_SET, 1, getExampleCardPositions()));
        assertSame(ActionQueue.getNext().getType(), LEAVE_GAME);
        assertSame(ActionQueue.getNext().getType(), SELECT_SET);
    }

    @Test
    public void testActionQueueHighHigh() {
        ActionQueue.addAction(new src.action.Action(DRAW_THREE, 0));
        ActionQueue.addAction(new src.action.Action(SHOW_SET, 1));
        assertSame(ActionQueue.getNext().getType(), DRAW_THREE);
        assertSame(ActionQueue.getNext().getType(), SHOW_SET);
    }

    @Test
    public void testActionQueueLowHigh() {
        ActionQueue.addAction(new src.action.Action(REQUEST_SHOW_SET, 0));
        ActionQueue.addAction(new src.action.Action(SHOW_SET, 1));
        assertSame(ActionQueue.getNext().getType(), SHOW_SET);
        assertSame(ActionQueue.getNext().getType(), REQUEST_SHOW_SET);
    }

    @Test
    public void testActionQueueHighLow() {
        ActionQueue.addAction(new src.action.Action(SHOW_SET, 0));
        ActionQueue.addAction(new src.action.Action(LEAVE_GAME, 1));
        assertSame(ActionQueue.getNext().getType(), SHOW_SET);
        assertSame(ActionQueue.getNext().getType(), LEAVE_GAME);
    }

    private int[] getExampleCardPositions() {
        return new int[]{1, 2, 3};
    }
}
