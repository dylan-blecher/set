package src.action;


public class Action {
    private final ActionType actionType;

    public Action(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getType() { return actionType; }
}


//package src.action;
//
//
//        import src.proto.ActionProtos;
//
//public class Action {
//    private final ActionProtos.Action proto;
//
//    public Action(ActionProtos.Action action) {
//        proto = action;
//    }
//    public Action(ActionType type) {
//        this.type = type;
//    }
//
//    public ActionType getType() { return type; }
//}
//
//
