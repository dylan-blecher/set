# Play Set
# Dylan Blecher
# blecher.dylan@gmail.com
# April-June 2020
# Run player client

from queue import Queue
from socket import gethostname
from enum import Enum
from client.Structures import (
    ActionType,
    PlayerAction,
    PlayerActionSelectSet,
)

from proto.action_pb2 import (
    ClientRequest as ClientRequestProto,
    ServerResponse as ServerResponseProto,
    JoinGameRequest as JoinGameRequestProto,
    ConfirmPlayerID as ConfirmPlayerIDProto
)

import socket
import time
import threading
import json

from client import server_communicator

from google.protobuf.internal.encoder import _VarintEncoder
from google.protobuf.json_format import MessageToJson

from flask import render_template


SET_SIZE = 3
GAME_PORT = 9090        # The port used by the server

# Run the player 
# (setup socket connection and then all interaction with Java server and Javascript front end)
class PlayerRunner:
    def __init__(self, sock):
        self._sock = sock
        self._actions = Queue() # python queues are synchronous if you add a few flags when using :)
        self.playerID = None
    
    def run_player(self):    
        # setup socke connection with backend Java
        # HOST = 'Dylans-MacBook-Pro-2.local'
        HOST = socket.gethostbyname('localhost')
        # HOST = gethostname()    # The server's hostname or IP address

        print("HOST IS: " + HOST)
        fromServer = None
        toServer = None
        
        try:
            fromServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            fromServer.connect((HOST, GAME_PORT))

            playerIDRequestProto = ClientRequestProto(
                joinGame=JoinGameRequestProto(
                    name= "Dyl" # readPlayerName()
                )
            )

            server_communicator.send_message(fromServer, playerIDRequestProto)


            delay = 0.025 # 25 milliseconds
            while self.playerID is None:
                time.sleep(delay)
                delay *= 2
                
                try: 
                    serverResponseProto = server_communicator.receive_message(fromServer, ServerResponseProto)
                    # print(serverResponseProto)
                    if serverResponseProto.HasField("playerID"):
                        self.playerID = serverResponseProto.playerID
                        
                        # can't write to fromServer anymore - it's one way!
                        fromServer.shutdown(socket.SHUT_WR) 

                except Exception as e:
                    print(str(e))
                    server_communicator.send_message(fromServer, playerIDRequestProto)
            
            toServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            toServer.connect((HOST, GAME_PORT))  
            
            # can't read from toServer anymore - it's one way!
            toServer.shutdown(socket.SHUT_RD) 

            confirmPlayerIDProto = ClientRequestProto(
                confirmPlayerID=ConfirmPlayerIDProto(
                    playerID=self.playerID
                )
            )
            
            server_communicator.send_message(toServer, confirmPlayerIDProto)
        
        except Exception as e:
            print(str(e))
            if fromServer is None:
                closeSocket(toServer)
            if toServer is None:
                closeSocket(fromServer)
            raise e

        actionQueueThread = self._sock.start_background_task(ActionQueueDrainer(self._actions, toServer).run)
        feedbackThread = self._sock.start_background_task(FeedbackGetter(fromServer, self._sock).run)

        self._sock.sleep(5)
        actionQueueThread.join()
        feedbackThread.join()

    def addActionToQueue(self, action):
        self._actions.put(action)

    def getPlayerID(self):
        return self.playerID

def closeSocket(sock):
    if sock != None:
        sock.shutdown(socket.SHUT_RDWR)
        sock.close()

# Get an action and send it to the server
class ActionQueueDrainer:
    def __init__(self, actions, toServer):
        self.actions = actions
        self.toServer = toServer

    def run(self):
        while True:
            action = self.actions.get(block=True)
            assert(action != None)
            self.sendAction(action)

    def sendAction(self, action: PlayerAction) -> None:
        server_communicator.send_message(self.toServer, action.proto)

# Handle messages from the server
class FeedbackGetter:
    def __init__(self, fromServer, sock):
        self.fromServer = fromServer
        self._sock = sock

    def run(self):
        while True:
            try:
                # CHANEG FROM _PRIVATE_METHOD
                serverResponseProto = server_communicator.receive_message(self.fromServer, ServerResponseProto)
                # print("got message" + str(serverResponseProto))
                if serverResponseProto.HasField("errorMessage"):
                    self._sock.emit("error_message", str(serverResponseProto.errorMessage))
                elif serverResponseProto.HasField("state"):
                    print("got state")
                    # convert state to json
                    stateJson = MessageToJson(serverResponseProto.state, indent=None)
                    self._sock.emit("board_change", stateJson)
                elif serverResponseProto.HasField("revealedSet"):
                    revealSetJson = MessageToJson(serverResponseProto.revealedSet, indent=None)
                    self._sock.emit("reveal_set", revealSetJson)
                    time.sleep(2.5) # TODO: This sleep is way too hacky. It should all be front end.
                    # Once I have the program running based on sending actions, instead of state
                    # every loop of the game-runner, this won't be an issue.
                    # The current issue that this sleep fixes is that it's overwriting the set 
                    # cards too quickly since state is sent straight after the revealed set is sent.
                elif serverResponseProto.HasField("result"):
                    break
            except Exception as e:
                print(str(e))