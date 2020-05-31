from queue import Queue
from socket import gethostname
from enum import Enum
from Structures import (
    ActionType,
    PlayerAction,
    PlayerActionSelectSet,
)

from src.proto.action_pb2 import (
    ClientRequest as ClientRequestProto,
    ServerResponse as ServerResponseProto,
    JoinGameRequest as JoinGameRequestProto,
    ConfirmPlayerID as ConfirmPlayerIDProto
)

import socket
import time
import threading
import json

from src.client import server_communicator

from google.protobuf.internal.encoder import _VarintEncoder


SET_SIZE = 3
GAME_PORT = 9090        # The port used by the server

def runPlayer():
    actions = Queue()       # python queues are synchronous :)
    HOST = gethostname()    # The server's hostname or IP address
    playerID = None
    fromServer = None
    toServer = None
    
    try:
        fromServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        fromServer.connect((HOST, GAME_PORT))

        # maybe i needa create this as a "ClientRequest type"
        # action = PlayerActionSelectSet.build(
        #     ActionType.SELECT_SET,
        #     playerID=2403,
        #     cardPositions=[94, 98, 5]
        # )

        # ActionProto(
        #     type=_action_type_to_proto(actionType),
        #     playerID=playerID
        # )
          
        # playerIDRequestProto = ClientRequestProto()
        # playerIDRequestProto.joinGame.name = readPlayerName()

        playerIDRequestProto = ClientRequestProto(
            joinGame=JoinGameRequestProto(
                name=readPlayerName()
            )
        )

        server_communicator._send_message(fromServer, playerIDRequestProto)

        
        delay = 0.025 # 25 milliseconds
        while playerID is None:
            time.sleep(delay)
            delay *= 2
            
            try: 
                print("ahh waiting")
                serverResponseProto = server_communicator._receive_message(fromServer, ServerResponseProto)
                print(serverResponseProto)
                print("stuck here")
                if serverResponseProto.HasField("playerID"):
                    print("got player id")
                    playerID = serverResponseProto.playerID
                    fromServer.shutdown(socket.SHUT_WR) # can't write to fromServer anymore - it's one way!

            except Exception as e:
                print(str(e))
                print("if we failed to _receive_message, we probably got the request for ID that we just put on!")
                server_communicator._send_message(fromServer, playerIDRequestProto)
                print("sent message again")
        

        
        toServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        toServer.connect((HOST, GAME_PORT))  
        toServer.shutdown(socket.SHUT_RD)           # can't read from toServer anymore - it's one way!

        confirmPlayerIDProto = ClientRequestProto(
            confirmPlayerID=ConfirmPlayerIDProto(
                playerID=playerID
            )
        )
        
        server_communicator._send_message(toServer, confirmPlayerIDProto)
        print("sent confirmation of id")
    
    except Exception as e:
        print(str(e))
        if fromServer is None:
            closeSocket(toServer)
        if toServer is None:
            closeSocket(fromServer)
        raise e


    actionQueueThread = ActionQueueDrainer(actions, toServer)
    feedbackThread = FeedbackGetter(fromServer)

    actionQueueThread.start()
    # while True:
    #     continue
    feedbackThread.start()

    playerRequestsToLeave = False
    print("start get action loop")
    while not playerRequestsToLeave:
        action = getAction(playerID)
        if action != None:
            print("added action to queue!")
            actions.put(action)

            if action.getActionType() == ActionType.LEAVE_GAME:
                playerRequestsToLeave = True
                
    farewellPlayer()

    actionQueueThread.join()
    feedbackThread.join()

class ActionQueueDrainer(threading.Thread):
    def __init__(self, actions, toServer):
        threading.Thread.__init__(self)
        self.actions = actions
        self.toServer = toServer

    def run(self):
        while True:
            action = self.actions.get(block=True)
            assert(action != None)
            self.sendAction(action)
            print("sent action!")

    def sendAction(self, action: PlayerAction) -> None:
        server_communicator._send_message(self.toServer, action.proto)

class FeedbackGetter(threading.Thread):
    def __init__(self, fromServer):
        threading.Thread.__init__(self)
        self.fromServer = fromServer

    def run(self):
        while True:
            try:
                serverResponseProto = server_communicator._receive_message(self.fromServer, ServerResponseProto)
        
                if serverResponseProto.HasField("errorMessage"):
                    print(serverResponseProto.errorMessage)
            except Exception as e:
                print(str(e))

def closeSocket(sock):
    if sock != None:
        sock.shutdown(socket.SHUT_RDWR)
        sock.close()

def farewellPlayer():
    print("Adios muchacho :)")

def readPlayerName():
    return input("What is your name? ")

def promptForAction():
    print("Enter 0 if you would like to select a set.")
    print("Enter 1 to request to see a set.")
    print("Enter 2 to leave game now.")
    print("Enter 3 to request 3 more cards.")

def getAction(playerID):
    promptForAction()
    actionType = ActionType(int(input()) + 1) # TODO: not sure if this works...
    action = None

    if actionType == ActionType.SELECT_SET:
        action = getSelectSet(playerID, actionType)
    elif actionType == ActionType.REQUEST_SHOW_SET or actionType == ActionType.LEAVE_GAME or actionType == ActionType.REQUEST_DRAW_THREE:
        action = PlayerAction.build(actionType, playerID)
    else:
        print("DIDN'T GET A VALID ACTION")

    return action

def getSelectSet(playerID, actionType):
    print("Enter the 3 cards in your set. ")

    cardPositions = [int(x) for x in input().split()]
    print(cardPositions)

    return PlayerActionSelectSet.build(actionType, playerID, cardPositions)


class StructureType(Enum):
    ERROR_MESSAGE = 0

runPlayer()