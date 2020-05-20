package src.action;


public class Action {
    private final ActionType actionType;

    public Action(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getType() { return actionType; }
}


