from queue import Queue
from socket import gethostname
from enum import Enum
from Structures import PlayerAction, PlayerActionSelectSet, SocketMessage

import socket
import time
import threading
import json

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

        joinGameRequest = SocketMessage("REQUEST_PLAYER_ID", readPlayerName())
        # not sure if encode is necessary...
        fromServer.sendall(serialise(joinGameRequest).encode())
        
        delay = 0.025 # 25 milliseconds
        while playerID is None:
            jsonString = fromServer.recv(256).decode()
            msg = json.loads(jsonString, object_hook=deserialise)
            msgType = msg.getMessageType()
            
            if msgType == "REQUEST_PLAYER_ID":
                fromServer.sendall(serialise(joinGameRequest).encode())
                time.sleep(delay)
                delay *= 2
            elif msgType == "GIVE_PLAYER_ID":
                playerID = int(msg.getMessage())
                fromServer.shutdown(socket.SHUT_WR) # can't write to fromServer anymore - it's one way!

        toServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        toServer.connect((HOST, GAME_PORT))  
        toServer.shutdown(socket.SHUT_RD)           # can't read from toServer anymore - it's one way!
        confirmationOfID = joinGameRequest = SocketMessage("CONFIRM_PLAYER_ID", str(playerID))
        toServer.sendall(serialise(confirmationOfID).encode())
    
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
    feedbackThread.start()

    playerRequestsToLeave = False
    while not playerRequestsToLeave:
        action = getAction(playerID)
        if action != None:
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

    def sendAction(self, action):
        self.toServer.sendall((self.serializeAction(action) + "\n").encode())


    def serializeAction(self, action):
        actionType = action.getActionType()
        serializedAction = "@".join([ActionType(actionType).name, str(action.getPlayerID())])
        
        if actionType == ActionType.SELECT_SET:
            serializedAction = "@".join([serializedAction, "@".join([str(num) for num in action.getCardPositions()])])
        
        print(serializedAction)
        return serializedAction

class FeedbackGetter(threading.Thread):
    def __init__(self, fromServer):
        threading.Thread.__init__(self)
        self.fromServer = fromServer

    def run(self):
        while True:
            jsonString = self.fromServer.recv(256).decode()
            jsonObj = json.loads(jsonString)
            print(jsonObj["message"])
            
            # json.loads(s) # loads json data from a string
            # json.dumps(j) # prints JSON object as a string

def closeSocket(socket):
    if socket != None:
        socket.shutdown(socket.SHUT_RDWR)
        socket.close()

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
    actionType = ActionType(int(input())) # TODO: not sure if this works...
    action = None

    if actionType == ActionType.SELECT_SET:
        action = getSelectSet(playerID, actionType)
    elif actionType == ActionType.REQUEST_SHOW_SET or actionType == ActionType.LEAVE_GAME or actionType == ActionType.REQUEST_DRAW_THREE:
        action = PlayerAction(actionType, playerID)
    else:
        print("DIDN'T GET A VALID ACTION")

    return action

def getSelectSet(playerID, actionType):
    print("Enter the 3 cards in your set. ")

    cardPositions = [int(x) for x in input().split()]
    print(cardPositions)

    return PlayerActionSelectSet(actionType, playerID, cardPositions)

def deserialise(jsonDictionary):
    if '__type__' not in jsonDictionary:
        return None

    requiredType = jsonDictionary['__type__']
    if requiredType == 'MESSAGE':
        return SocketMessage(jsonDictionary['messageType'], jsonDictionary['message'])

    # if requiredType == 'BOARD':
    #     return Board(jsonDictionary['Cards'])

    return None

# returns the JSON string for a given object by first converting it to a dictionary
def serialise(obj):
    return json.dumps(obj.__dict__)


class ActionType(Enum):
    SELECT_SET = 0
    REQUEST_SHOW_SET = 1
    LEAVE_GAME = 2
    REQUEST_DRAW_THREE = 3

class StructureType(Enum):
    ERROR_MESSAGE = 0

runPlayer()