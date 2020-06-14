import eventlet
# overrides sleep from standard library (puts eventlet - thread - to sleep instead of all threads (i.e. process))
eventlet.monkey_patch() 
# this whole directory should probably be put somewhere else...

from client.PlayerClient import PlayerRunner
from client.Structures import (
    ActionType,
    PlayerAction,
    PlayerActionSelectSet,
)

from flask import Flask, render_template, request, json
from flask_socketio import SocketIO, send
import threading
import time

app = Flask(__name__)
app.config['SECRET_KEY'] = 'mysecret'
socketio = SocketIO(app)
thread = None
thread_lock = threading.Lock()

playerRunner = PlayerRunner(socketio)

@app.route('/', methods=['GET', 'POST'])
def setup():
    return render_template('index.html')

@socketio.on('message') # connect
def handleMessage(msg):
    print("Here is an unnamed message:")
    print(msg)

@socketio.on('connect')
def connect():
    print("trying")
    global thread
    with thread_lock:
        if thread is None:
            thread = socketio.start_background_task(playerRunner.run_player)
    print("done")

@socketio.on('select_set')
def handle_select_set(cardPositions):
    #     elif actionType == ActionType.REQUEST_SHOW_SET or actionType == ActionType.LEAVE_GAME or actionType == ActionType.REQUEST_DRAW_THREE:
    # showSetAction = PlayerAction.build(ActionType.REQUEST_SHOW_SET, 0)
    select_set_action = PlayerActionSelectSet.build(ActionType.SELECT_SET, playerRunner.getPlayerID(), cardPositions)
    playerRunner.addActionToQueue(select_set_action)
    # playerRunner.addActionToQueue(showSetAction)

    # emit('update value', message, broadcast=True)

@socketio.on('draw_cards')
def handle_draw_cards():
    print(playerRunner.getPlayerID())
    draw_cards_request_action = PlayerAction.build(ActionType.REQUEST_DRAW_THREE, playerRunner.getPlayerID())
    playerRunner.addActionToQueue(draw_cards_request_action)

@socketio.on('reveal_set')
def handle_reveal_set():
    reveal_set_request_action = PlayerAction.build(ActionType.REQUEST_SHOW_SET, playerRunner.getPlayerID())
    playerRunner.addActionToQueue(reveal_set_request_action)

if __name__ == '__main__':
    socketio.run(app, port=int(input("Enter port (e.g. 8003): ")))
