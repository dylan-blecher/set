package src.player;

import src.cardCollection.set.Set;
import src.proto.AllProtos;

public class Player {
    public final AllProtos.Player proto;

    public Player(AllProtos.Player playerProto) {
        proto = playerProto;
    }

    public Player(int ID, String name) {
        this(AllProtos.Player
                .newBuilder()
                .setID(ID)
                .setName(name)
                .build()
        );
    }

    public void collectSet(Set set) {
        proto.getSetsCollectedList().add(set.getProto());
    }

    // TODO: Maybe this should be elsewhere... player shouldn't be responsible for reliably saying their score? idk...
    public void seeScore() {
        // TODO: lol what a gross temporary solution below for plurals...
        System.out.printf("%s (player %d) found %d set%s!\n", getName(), getID() + 1, getNSetsCollected(), getNSetsCollected() == 1 ? "" : "s");
    }

    public int getNSetsCollected() {
        return proto.getSetsCollectedList().size();
    }

    public int getID() {
        return proto.getID();
    }

    public String getName() {
        return proto.getName();
    }
}
