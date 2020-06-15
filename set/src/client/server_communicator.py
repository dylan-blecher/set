# Helpers used to interract with Java backend by serialising and deserialising proto
# according to Google's Protobuf
# Play Set
# Dylan Blecher
# blecher.dylan@gmail.com
# April-June 2020

from typing import List, Type, TypeVar

from google.protobuf.message import Message
from google.protobuf.internal.encoder import _VarintBytes  # type: ignore
from google.protobuf.internal.decoder import _DecodeVarint  # type: ignore

from socket import socket

# serialise and send message to backend
def send_message(clientSocket, message):
    data = message.SerializeToString()
    size = encode_varint(len(data))
    clientSocket.sendall(size + data)

# receive and deserialise message to backend
def receive_message(clientSocket, protoClass):
    # Receive the size of the message data
    data = b""
    while True:
        try:
            data += clientSocket.recv(1)
            size = decode_varint(data)
            break
        except IndexError:
            pass

    # Receive the message data
    data = b""
    while len(data) < size:
        chunk = clientSocket.recv(size - len(data))
        if chunk == b"":
            raise RuntimeError("can't read enough to fill proto")
        data += chunk

    # Decode the message
    proto = protoClass()
    proto.ParseFromString(data)
    return proto
    # if you want to return the actual Python object instead of Proto version:
    # proto_cls = message_type.proto_cls
    # proto = proto_cls()
    # proto.ParseFromString(data)
    # return message_type(proto)


"""
proto helpers
from https://krpc.github.io/krpc/communication-protocols/tcpip.html
"""
def encode_varint(num_bytes: int) -> bytes:
    """
    Encode an int as protobuf varint
    """
    encoded: bytes = _VarintBytes(num_bytes)
    assert isinstance(encoded, bytes)
    return encoded


def decode_varint(data: bytes) -> int:
    """
    Decode a protobuf varint to an int
    """
    decoded: Tuple[int, int] = _DecodeVarint(data, 0)
    assert isinstance(decoded[0], int)
    return decoded[0]