from typing import List, Type, TypeVar

from google.protobuf.message import Message
from google.protobuf.internal.encoder import _VarintEncoder  # type: ignore
from google.protobuf.internal.decoder import _DecodeVarint  # type: ignore

from socket import socket

T = TypeVar("T", bound=Message)


def encode_varint(value: int) -> bytes:
    """ Encode an int as a protobuf varint """
    data: List[bytes] = []
    _VarintEncoder()(data.append, value, False)
    return b''.join(data)


def decode_varint(data: bytes) -> int:
    """ Decode a protobuf varint to an int """
    return _DecodeVarint(data, 0)[0]


def send_message(sock: socket, msg: Message) -> None:
    """ Send a message, prefixed with its size, to a TPC/IP socket """
    data = msg.SerializeToString()
    size = encode_varint(len(data))
    sock.sendall(size + data)


def recv_message(sock: socket, msg_type: Type[T]) -> T:
    """ Receive a message, prefixed with its size, from a TCP/IP socket """
    # Receive the size of the message data
    data = b''
    while True:
        try:
            data += sock.recv(1)
            size = decode_varint(data)
            break
        except IndexError:
            pass
    # Receive the message data
    data = sock.recv(size)
    # Decode the message
    msg = msg_type()
    msg.ParseFromString(data)
    return msg