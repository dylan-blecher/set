# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: action.proto

from google.protobuf.internal import enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='action.proto',
  package='action',
  syntax='proto3',
  serialized_options=b'\n\tsrc.protoB\tAllProtos',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x0c\x61\x63tion.proto\x12\x06\x61\x63tion\"S\n\x06\x41\x63tion\x12 \n\x04type\x18\x01 \x01(\x0e\x32\x12.action.ActionType\x12\x10\n\x08playerID\x18\x02 \x01(\r\x12\x15\n\rcardPositions\x18\x03 \x03(\x05\"\x1f\n\x0fJoinGameRequest\x12\x0c\n\x04name\x18\x01 \x01(\t\"#\n\x0f\x43onfirmPlayerID\x12\x10\n\x08playerID\x18\x01 \x01(\r\"\x9d\x01\n\rClientRequest\x12+\n\x08joinGame\x18\x01 \x01(\x0b\x32\x17.action.JoinGameRequestH\x00\x12\x32\n\x0f\x63onfirmPlayerID\x18\x02 \x01(\x0b\x32\x17.action.ConfirmPlayerIDH\x00\x12 \n\x06\x61\x63tion\x18\x03 \x01(\x0b\x32\x0e.action.ActionH\x00\x42\t\n\x07request\"\x1c\n\x08PlayerID\x12\x10\n\x08playerID\x18\x01 \x01(\r\"\x90\x01\n\x04\x43\x61rd\x12\"\n\x06\x63olour\x18\x01 \x01(\x0e\x32\x12.action.CardColour\x12\"\n\x06number\x18\x02 \x01(\x0e\x32\x12.action.CardNumber\x12 \n\x05shape\x18\x03 \x01(\x0e\x32\x11.action.CardShape\x12\x1e\n\x04\x66ill\x18\x04 \x01(\x0e\x32\x10.action.CardFill\"m\n\x0b\x43onsensuses\x12\x16\n\x0enActivePlayers\x18\x01 \x01(\r\x12#\n\x1bnPlayersRequestingDrawThree\x18\x02 \x01(\r\x12!\n\x19nPlayersRequestingShowSet\x18\x03 \x01(\r\"-\n\x0e\x43\x61rdCollection\x12\x1b\n\x05\x63\x61rds\x18\x01 \x03(\x0b\x32\x0c.action.Card\"Q\n\x06Player\x12\n\n\x02ID\x18\x01 \x01(\r\x12\x0c\n\x04name\x18\x02 \x01(\t\x12-\n\rsetsCollected\x18\x03 \x03(\x0b\x32\x16.action.CardCollection\"\x97\x01\n\x05State\x12\x1b\n\x05\x62oard\x18\x01 \x03(\x0b\x32\x0c.action.Card\x12(\n\x0b\x63onsensuses\x18\x02 \x01(\x0b\x32\x13.action.Consensuses\x12%\n\ractivePlayers\x18\x03 \x03(\x0b\x32\x0e.action.Player\x12 \n\x18\x63\x61rdIndicesOfRevealedSet\x18\x04 \x03(\r\"$\n\x0bRevealedSet\x12\x15\n\rcardPositions\x18\x01 \x03(\r\"/\n\x06Result\x12%\n\rrankedPlayers\x18\x01 \x03(\x0b\x32\x0e.action.Player\"\xb6\x01\n\x0eServerResponse\x12\x12\n\x08playerID\x18\x01 \x01(\rH\x00\x12\x1e\n\x05state\x18\x02 \x01(\x0b\x32\r.action.StateH\x00\x12\x16\n\x0c\x65rrorMessage\x18\x03 \x01(\tH\x00\x12*\n\x0brevealedSet\x18\x04 \x01(\x0b\x32\x13.action.RevealedSetH\x00\x12 \n\x06result\x18\x05 \x01(\x0b\x32\x0e.action.ResultH\x00\x42\n\n\x08response*\x93\x01\n\nActionType\x12\x17\n\x13UNKNOWN_ACTION_TYPE\x10\x00\x12\x0e\n\nSELECT_SET\x10\x01\x12\x14\n\x10REQUEST_SHOW_SET\x10\x02\x12\x0e\n\nLEAVE_GAME\x10\x03\x12\x16\n\x12REQUEST_DRAW_THREE\x10\x04\x12\r\n\x08SHOW_SET\x10\xe8\x07\x12\x0f\n\nDRAW_THREE\x10\xe9\x07*@\n\nCardColour\x12\x12\n\x0eUNKNOWN_COLOUR\x10\x00\x12\x07\n\x03RED\x10\x01\x12\t\n\x05GREEN\x10\x02\x12\n\n\x06PURPLE\x10\x03*>\n\x08\x43\x61rdFill\x12\x10\n\x0cUNKNOWN_FILL\x10\x00\x12\x08\n\x04OPEN\x10\x01\x12\x0b\n\x07STRIPED\x10\x02\x12\t\n\x05SOLID\x10\x03*=\n\nCardNumber\x12\x12\n\x0eUNKNOWN_NUMBER\x10\x00\x12\x07\n\x03ONE\x10\x01\x12\x07\n\x03TWO\x10\x02\x12\t\n\x05THREE\x10\x03*C\n\tCardShape\x12\x11\n\rUNKNOWN_SHAPE\x10\x00\x12\x0b\n\x07\x44IAMOND\x10\x01\x12\x0c\n\x08SQUIGGLE\x10\x02\x12\x08\n\x04OVAL\x10\x03\x42\x16\n\tsrc.protoB\tAllProtosb\x06proto3'
)

_ACTIONTYPE = _descriptor.EnumDescriptor(
  name='ActionType',
  full_name='action.ActionType',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN_ACTION_TYPE', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SELECT_SET', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='REQUEST_SHOW_SET', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='LEAVE_GAME', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='REQUEST_DRAW_THREE', index=4, number=4,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SHOW_SET', index=5, number=1000,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='DRAW_THREE', index=6, number=1001,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1184,
  serialized_end=1331,
)
_sym_db.RegisterEnumDescriptor(_ACTIONTYPE)

ActionType = enum_type_wrapper.EnumTypeWrapper(_ACTIONTYPE)
_CARDCOLOUR = _descriptor.EnumDescriptor(
  name='CardColour',
  full_name='action.CardColour',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN_COLOUR', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='RED', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='GREEN', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='PURPLE', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1333,
  serialized_end=1397,
)
_sym_db.RegisterEnumDescriptor(_CARDCOLOUR)

CardColour = enum_type_wrapper.EnumTypeWrapper(_CARDCOLOUR)
_CARDFILL = _descriptor.EnumDescriptor(
  name='CardFill',
  full_name='action.CardFill',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN_FILL', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='OPEN', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='STRIPED', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SOLID', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1399,
  serialized_end=1461,
)
_sym_db.RegisterEnumDescriptor(_CARDFILL)

CardFill = enum_type_wrapper.EnumTypeWrapper(_CARDFILL)
_CARDNUMBER = _descriptor.EnumDescriptor(
  name='CardNumber',
  full_name='action.CardNumber',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN_NUMBER', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='ONE', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='TWO', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='THREE', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1463,
  serialized_end=1524,
)
_sym_db.RegisterEnumDescriptor(_CARDNUMBER)

CardNumber = enum_type_wrapper.EnumTypeWrapper(_CARDNUMBER)
_CARDSHAPE = _descriptor.EnumDescriptor(
  name='CardShape',
  full_name='action.CardShape',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNKNOWN_SHAPE', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='DIAMOND', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SQUIGGLE', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='OVAL', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1526,
  serialized_end=1593,
)
_sym_db.RegisterEnumDescriptor(_CARDSHAPE)

CardShape = enum_type_wrapper.EnumTypeWrapper(_CARDSHAPE)
UNKNOWN_ACTION_TYPE = 0
SELECT_SET = 1
REQUEST_SHOW_SET = 2
LEAVE_GAME = 3
REQUEST_DRAW_THREE = 4
SHOW_SET = 1000
DRAW_THREE = 1001
UNKNOWN_COLOUR = 0
RED = 1
GREEN = 2
PURPLE = 3
UNKNOWN_FILL = 0
OPEN = 1
STRIPED = 2
SOLID = 3
UNKNOWN_NUMBER = 0
ONE = 1
TWO = 2
THREE = 3
UNKNOWN_SHAPE = 0
DIAMOND = 1
SQUIGGLE = 2
OVAL = 3



_ACTION = _descriptor.Descriptor(
  name='Action',
  full_name='action.Action',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='type', full_name='action.Action.type', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='playerID', full_name='action.Action.playerID', index=1,
      number=2, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='cardPositions', full_name='action.Action.cardPositions', index=2,
      number=3, type=5, cpp_type=1, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=24,
  serialized_end=107,
)


_JOINGAMEREQUEST = _descriptor.Descriptor(
  name='JoinGameRequest',
  full_name='action.JoinGameRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='name', full_name='action.JoinGameRequest.name', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=109,
  serialized_end=140,
)


_CONFIRMPLAYERID = _descriptor.Descriptor(
  name='ConfirmPlayerID',
  full_name='action.ConfirmPlayerID',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='playerID', full_name='action.ConfirmPlayerID.playerID', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=142,
  serialized_end=177,
)


_CLIENTREQUEST = _descriptor.Descriptor(
  name='ClientRequest',
  full_name='action.ClientRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='joinGame', full_name='action.ClientRequest.joinGame', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='confirmPlayerID', full_name='action.ClientRequest.confirmPlayerID', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='action', full_name='action.ClientRequest.action', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
    _descriptor.OneofDescriptor(
      name='request', full_name='action.ClientRequest.request',
      index=0, containing_type=None,
      create_key=_descriptor._internal_create_key,
    fields=[]),
  ],
  serialized_start=180,
  serialized_end=337,
)


_PLAYERID = _descriptor.Descriptor(
  name='PlayerID',
  full_name='action.PlayerID',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='playerID', full_name='action.PlayerID.playerID', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=339,
  serialized_end=367,
)


_CARD = _descriptor.Descriptor(
  name='Card',
  full_name='action.Card',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='colour', full_name='action.Card.colour', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='number', full_name='action.Card.number', index=1,
      number=2, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='shape', full_name='action.Card.shape', index=2,
      number=3, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='fill', full_name='action.Card.fill', index=3,
      number=4, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=370,
  serialized_end=514,
)


_CONSENSUSES = _descriptor.Descriptor(
  name='Consensuses',
  full_name='action.Consensuses',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='nActivePlayers', full_name='action.Consensuses.nActivePlayers', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='nPlayersRequestingDrawThree', full_name='action.Consensuses.nPlayersRequestingDrawThree', index=1,
      number=2, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='nPlayersRequestingShowSet', full_name='action.Consensuses.nPlayersRequestingShowSet', index=2,
      number=3, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=516,
  serialized_end=625,
)


_CARDCOLLECTION = _descriptor.Descriptor(
  name='CardCollection',
  full_name='action.CardCollection',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='cards', full_name='action.CardCollection.cards', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=627,
  serialized_end=672,
)


_PLAYER = _descriptor.Descriptor(
  name='Player',
  full_name='action.Player',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='ID', full_name='action.Player.ID', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='name', full_name='action.Player.name', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='setsCollected', full_name='action.Player.setsCollected', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=674,
  serialized_end=755,
)


_STATE = _descriptor.Descriptor(
  name='State',
  full_name='action.State',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='board', full_name='action.State.board', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='consensuses', full_name='action.State.consensuses', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='activePlayers', full_name='action.State.activePlayers', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='cardIndicesOfRevealedSet', full_name='action.State.cardIndicesOfRevealedSet', index=3,
      number=4, type=13, cpp_type=3, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=758,
  serialized_end=909,
)


_REVEALEDSET = _descriptor.Descriptor(
  name='RevealedSet',
  full_name='action.RevealedSet',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='cardPositions', full_name='action.RevealedSet.cardPositions', index=0,
      number=1, type=13, cpp_type=3, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=911,
  serialized_end=947,
)


_RESULT = _descriptor.Descriptor(
  name='Result',
  full_name='action.Result',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='rankedPlayers', full_name='action.Result.rankedPlayers', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=949,
  serialized_end=996,
)


_SERVERRESPONSE = _descriptor.Descriptor(
  name='ServerResponse',
  full_name='action.ServerResponse',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='playerID', full_name='action.ServerResponse.playerID', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='state', full_name='action.ServerResponse.state', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='errorMessage', full_name='action.ServerResponse.errorMessage', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='revealedSet', full_name='action.ServerResponse.revealedSet', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='result', full_name='action.ServerResponse.result', index=4,
      number=5, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
    _descriptor.OneofDescriptor(
      name='response', full_name='action.ServerResponse.response',
      index=0, containing_type=None,
      create_key=_descriptor._internal_create_key,
    fields=[]),
  ],
  serialized_start=999,
  serialized_end=1181,
)

_ACTION.fields_by_name['type'].enum_type = _ACTIONTYPE
_CLIENTREQUEST.fields_by_name['joinGame'].message_type = _JOINGAMEREQUEST
_CLIENTREQUEST.fields_by_name['confirmPlayerID'].message_type = _CONFIRMPLAYERID
_CLIENTREQUEST.fields_by_name['action'].message_type = _ACTION
_CLIENTREQUEST.oneofs_by_name['request'].fields.append(
  _CLIENTREQUEST.fields_by_name['joinGame'])
_CLIENTREQUEST.fields_by_name['joinGame'].containing_oneof = _CLIENTREQUEST.oneofs_by_name['request']
_CLIENTREQUEST.oneofs_by_name['request'].fields.append(
  _CLIENTREQUEST.fields_by_name['confirmPlayerID'])
_CLIENTREQUEST.fields_by_name['confirmPlayerID'].containing_oneof = _CLIENTREQUEST.oneofs_by_name['request']
_CLIENTREQUEST.oneofs_by_name['request'].fields.append(
  _CLIENTREQUEST.fields_by_name['action'])
_CLIENTREQUEST.fields_by_name['action'].containing_oneof = _CLIENTREQUEST.oneofs_by_name['request']
_CARD.fields_by_name['colour'].enum_type = _CARDCOLOUR
_CARD.fields_by_name['number'].enum_type = _CARDNUMBER
_CARD.fields_by_name['shape'].enum_type = _CARDSHAPE
_CARD.fields_by_name['fill'].enum_type = _CARDFILL
_CARDCOLLECTION.fields_by_name['cards'].message_type = _CARD
_PLAYER.fields_by_name['setsCollected'].message_type = _CARDCOLLECTION
_STATE.fields_by_name['board'].message_type = _CARD
_STATE.fields_by_name['consensuses'].message_type = _CONSENSUSES
_STATE.fields_by_name['activePlayers'].message_type = _PLAYER
_RESULT.fields_by_name['rankedPlayers'].message_type = _PLAYER
_SERVERRESPONSE.fields_by_name['state'].message_type = _STATE
_SERVERRESPONSE.fields_by_name['revealedSet'].message_type = _REVEALEDSET
_SERVERRESPONSE.fields_by_name['result'].message_type = _RESULT
_SERVERRESPONSE.oneofs_by_name['response'].fields.append(
  _SERVERRESPONSE.fields_by_name['playerID'])
_SERVERRESPONSE.fields_by_name['playerID'].containing_oneof = _SERVERRESPONSE.oneofs_by_name['response']
_SERVERRESPONSE.oneofs_by_name['response'].fields.append(
  _SERVERRESPONSE.fields_by_name['state'])
_SERVERRESPONSE.fields_by_name['state'].containing_oneof = _SERVERRESPONSE.oneofs_by_name['response']
_SERVERRESPONSE.oneofs_by_name['response'].fields.append(
  _SERVERRESPONSE.fields_by_name['errorMessage'])
_SERVERRESPONSE.fields_by_name['errorMessage'].containing_oneof = _SERVERRESPONSE.oneofs_by_name['response']
_SERVERRESPONSE.oneofs_by_name['response'].fields.append(
  _SERVERRESPONSE.fields_by_name['revealedSet'])
_SERVERRESPONSE.fields_by_name['revealedSet'].containing_oneof = _SERVERRESPONSE.oneofs_by_name['response']
_SERVERRESPONSE.oneofs_by_name['response'].fields.append(
  _SERVERRESPONSE.fields_by_name['result'])
_SERVERRESPONSE.fields_by_name['result'].containing_oneof = _SERVERRESPONSE.oneofs_by_name['response']
DESCRIPTOR.message_types_by_name['Action'] = _ACTION
DESCRIPTOR.message_types_by_name['JoinGameRequest'] = _JOINGAMEREQUEST
DESCRIPTOR.message_types_by_name['ConfirmPlayerID'] = _CONFIRMPLAYERID
DESCRIPTOR.message_types_by_name['ClientRequest'] = _CLIENTREQUEST
DESCRIPTOR.message_types_by_name['PlayerID'] = _PLAYERID
DESCRIPTOR.message_types_by_name['Card'] = _CARD
DESCRIPTOR.message_types_by_name['Consensuses'] = _CONSENSUSES
DESCRIPTOR.message_types_by_name['CardCollection'] = _CARDCOLLECTION
DESCRIPTOR.message_types_by_name['Player'] = _PLAYER
DESCRIPTOR.message_types_by_name['State'] = _STATE
DESCRIPTOR.message_types_by_name['RevealedSet'] = _REVEALEDSET
DESCRIPTOR.message_types_by_name['Result'] = _RESULT
DESCRIPTOR.message_types_by_name['ServerResponse'] = _SERVERRESPONSE
DESCRIPTOR.enum_types_by_name['ActionType'] = _ACTIONTYPE
DESCRIPTOR.enum_types_by_name['CardColour'] = _CARDCOLOUR
DESCRIPTOR.enum_types_by_name['CardFill'] = _CARDFILL
DESCRIPTOR.enum_types_by_name['CardNumber'] = _CARDNUMBER
DESCRIPTOR.enum_types_by_name['CardShape'] = _CARDSHAPE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

Action = _reflection.GeneratedProtocolMessageType('Action', (_message.Message,), {
  'DESCRIPTOR' : _ACTION,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.Action)
  })
_sym_db.RegisterMessage(Action)

JoinGameRequest = _reflection.GeneratedProtocolMessageType('JoinGameRequest', (_message.Message,), {
  'DESCRIPTOR' : _JOINGAMEREQUEST,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.JoinGameRequest)
  })
_sym_db.RegisterMessage(JoinGameRequest)

ConfirmPlayerID = _reflection.GeneratedProtocolMessageType('ConfirmPlayerID', (_message.Message,), {
  'DESCRIPTOR' : _CONFIRMPLAYERID,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.ConfirmPlayerID)
  })
_sym_db.RegisterMessage(ConfirmPlayerID)

ClientRequest = _reflection.GeneratedProtocolMessageType('ClientRequest', (_message.Message,), {
  'DESCRIPTOR' : _CLIENTREQUEST,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.ClientRequest)
  })
_sym_db.RegisterMessage(ClientRequest)

PlayerID = _reflection.GeneratedProtocolMessageType('PlayerID', (_message.Message,), {
  'DESCRIPTOR' : _PLAYERID,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.PlayerID)
  })
_sym_db.RegisterMessage(PlayerID)

Card = _reflection.GeneratedProtocolMessageType('Card', (_message.Message,), {
  'DESCRIPTOR' : _CARD,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.Card)
  })
_sym_db.RegisterMessage(Card)

Consensuses = _reflection.GeneratedProtocolMessageType('Consensuses', (_message.Message,), {
  'DESCRIPTOR' : _CONSENSUSES,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.Consensuses)
  })
_sym_db.RegisterMessage(Consensuses)

CardCollection = _reflection.GeneratedProtocolMessageType('CardCollection', (_message.Message,), {
  'DESCRIPTOR' : _CARDCOLLECTION,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.CardCollection)
  })
_sym_db.RegisterMessage(CardCollection)

Player = _reflection.GeneratedProtocolMessageType('Player', (_message.Message,), {
  'DESCRIPTOR' : _PLAYER,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.Player)
  })
_sym_db.RegisterMessage(Player)

State = _reflection.GeneratedProtocolMessageType('State', (_message.Message,), {
  'DESCRIPTOR' : _STATE,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.State)
  })
_sym_db.RegisterMessage(State)

RevealedSet = _reflection.GeneratedProtocolMessageType('RevealedSet', (_message.Message,), {
  'DESCRIPTOR' : _REVEALEDSET,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.RevealedSet)
  })
_sym_db.RegisterMessage(RevealedSet)

Result = _reflection.GeneratedProtocolMessageType('Result', (_message.Message,), {
  'DESCRIPTOR' : _RESULT,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.Result)
  })
_sym_db.RegisterMessage(Result)

ServerResponse = _reflection.GeneratedProtocolMessageType('ServerResponse', (_message.Message,), {
  'DESCRIPTOR' : _SERVERRESPONSE,
  '__module__' : 'action_pb2'
  # @@protoc_insertion_point(class_scope:action.ServerResponse)
  })
_sym_db.RegisterMessage(ServerResponse)


DESCRIPTOR._options = None
# @@protoc_insertion_point(module_scope)
