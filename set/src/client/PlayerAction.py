class PlayerAction:
	def __init__(self, actionType, playerID):
		self.actionType = actionType
		self.playerID = playerID

	def getActionType(self):
		return self.actionType

	def getPlayerID(self):
		return self.playerID

class PlayerActionSelectSet(PlayerAction):
	def __init__(self, actionType, playerID, cardPositions):
		super().__init__(actionType, playerID)
		self.cardPositions = cardPositions

	def getCardPositions(self):
		return self.cardPositions