import socket
from socket import gethostname


HOST = gethostname() # The server's hostname or IP address
GAME_PORT = 9090        # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, GAME_PORT))
    # s.sendall(b'Hello, cunny')
    s.sendall(b"REQUEST_PLAYER_ID@Dylan")
    # data = s.recv(1024)

# print('Received', repr(data))

       # writer = new BufferedWriter(new OutputStreamWriter(fromServerSocket.getOutputStream()));
       #          String playerName = readPlayerName();
       #          System.out.println("REQUEST_PLAYER_ID");
       #          writer.write("REQUEST_PLAYER_ID" + "@" + playerName + "\n");
       #          writer.flush();

       #          // exponential backoff yay
       #          long delay = 25;
       #          while (!playerID.isPresent()) {
       #              String msg = fromServer.readLine();
       #              System.out.println(msg);
       #              if (msg.equals("REQUEST_PLAYER_ID")) {
       #                  System.out.println("TRYING TO GET PLAYER ID");
       #                  writer.write(msg + "\n");
       #                  writer.flush();
       #                  Thread.sleep(delay);
       #                  delay *= 2;
       #              } else playerID = Optional.of(Integer.valueOf(msg));
       #          }
       #      } catch (Exception e) {
       #          if (writer != null) writer.close();
       #      }