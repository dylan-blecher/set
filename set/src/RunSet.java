package src;

import src.server.Server;

/**
 * @author dylanblecher
 * blecher.dylan@gmail.com
 * April-June 2020
 *
 * Play Set
 * Set is a multi-player real-time card game designed by Marsha Falco in 1974.
 * Checkout the rules here:
 * https://en.wikipedia.org/wiki/Set_(card_game)
 */
public class RunSet {
    public static void main(String[] args) {
        // Run the game-server
        new Server().start();
    }
}
