from enum import Enum
from typing import List

from src.proto.action_pb2 import (
	Action as ActionProto,
	ActionType as ActionTypeProto
)


class ActionType(Enum):
    SELECT_SET = ActionTypeProto.SELECT_SET
    REQUEST_SHOW_SET = ActionTypeProto.REQUEST_SHOW_SET
    LEAVE_GAME = ActionTypeProto.LEAVE_GAME
    REQUEST_DRAW_THREE = ActionTypeProto.REQUEST_DRAW_THREE


ACTION_TYPE_TO_PROTO = {
	ActionType.SELECT_SET: ActionTypeProto.SELECT_SET,
	ActionType.REQUEST_SHOW_SET: ActionTypeProto.REQUEST_SHOW_SET,
	ActionType.LEAVE_GAME: ActionTypeProto.LEAVE_GAME,
	ActionType.REQUEST_DRAW_THREE: ActionTypeProto.REQUEST_DRAW_THREE
}


def _action_type_to_proto(action_type: ActionType) -> ActionTypeProto:
	return ACTION_TYPE_TO_PROTO[action_type]


class PlayerAction:
	def __init__(self, proto: ActionProto) -> None:
		self.proto = proto

	@classmethod
	def build(
		cls,
		actionType: ActionType,
		playerID: int
	) -> "PlayerAction":
		return cls(
			ActionProto(
				type=_action_type_to_proto(actionType),
				playerID=playerID
			)
		)

	def getActionType(self) -> ActionType:
		return ActionType(self.proto.actionType)

	def getPlayerID(self) -> int:
		return self.proto.playerID


class PlayerActionSelectSet(PlayerAction):
	@classmethod
	def build(
		cls,
		actionType: ActionType,
		playerID: int,
		cardPositions: List[int]
	):
		return cls(
			ActionProto(
				type=_action_type_to_proto(actionType),
				playerID=playerID,
				cardPositions=cardPositions
			)
		)

	def getCardPositions(self) -> List[int]:
		return list(self.proto.cardPositions)

class SocketMessage:
	def __init__(self, messageType, message):
		self.messageType = messageType
		self.message = message

	def getmessageType(self):
		return self.messageType

	def getMessage(self):
		return self.message