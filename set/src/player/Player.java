package src.player;

import src.cardCollection.set.Set;
import src.proto.AllProtos;

/**
 * @author dylanblecher
 * Player of the game.
 * Proto-backed according to Google's protobuf.
 */
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
//        proto.toBuilder().
//        proto.getSetsCollectedList().
        // TODO: work out how to dynamically add to proto lists so I can keep a score in the front end!
        proto.toBuilder().addSetsCollected(set.getProto());
//        proto.getSetsCollectedList().add(set.getProto());
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
