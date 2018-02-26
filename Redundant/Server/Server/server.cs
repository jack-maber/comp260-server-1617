using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.IO;

namespace Server
{
    class server
    {
        static bool quit = false;

        static LinkedList<String> incomingMessages = new LinkedList<string>();

        static LinkedList<String> outgoingMessages = new LinkedList<string>();

        static Dictionary<String, Socket> clientDictionary = new Dictionary<String, Socket>();

        static List<Player> PlayerList = new List<Player>();

        static Dungeon dungeon = new Dungeon();

        

        class ReceiveThreadLaunchInfo
        {
            public int ID;
            public Socket socket;

            public ReceiveThreadLaunchInfo(int ID, Socket socket)
            {
                this.ID = ID;
                this.socket = socket;
            }

        }

        static void acceptClientThread(Object obj)
        {
            Socket s = obj as Socket;

            int ID = 0;

            while (quit == false)
            {
                var newClientSocket = s.Accept();
                var myThread = new Thread(clientReceiveThread);

                myThread.Start(new ReceiveThreadLaunchInfo(ID, newClientSocket));

                lock (clientDictionary)
                {
                    String playerID = "client" + ID;
                    clientDictionary.Add(playerID, newClientSocket);
                    var player = new Player
                    {
                        dungeonRef = dungeon
                    };

                    player.Init();
                    PlayerList.Add(player);
                     

                    var dungeonResult = dungeon.RoomInfo(player);

                    lock (outgoingMessages)
                    {
                        outgoingMessages.AddLast(playerID + ":" + dungeonResult);
                    }

                    ID++;//Iterates on ID as to not leave players with the same ID


                }

            }

        }


        static Socket GetSocketFromName(String name)
        {
            lock (clientDictionary)
            {
                return clientDictionary[name];
            }
        }



        static String GetNameFromSocket(Socket s)
        {
            lock (clientDictionary)
            {
                foreach (KeyValuePair<String, Socket> o in clientDictionary)
                {
                    if (o.Value == s)
                    {
                        return o.Key;
                    }
                }
            }

            return null;
        }


        static void clientReceiveThread(Object obj)
        {
            ReceiveThreadLaunchInfo receiveInfo = obj as ReceiveThreadLaunchInfo;
            bool socketLost = false;

            while ((quit == false) && (socketLost == false))
            {
                byte[] buffer = new byte[4094];

                try
                {
                    int result = receiveInfo.socket.Receive(buffer);

                    if (result > 0)
                    {
                        ASCIIEncoding encoder = new ASCIIEncoding();

                        lock (incomingMessages)
                        {
                            incomingMessages.AddLast(receiveInfo.ID + ":" + encoder.GetString(buffer, 0, result));
                        }
                    }
                }
                catch (System.Exception ex)
                {
                    socketLost = true;
                }
            }
        }

       

        static void Main(string[] args)
        {
            ASCIIEncoding encoder = new ASCIIEncoding();
            dungeon.Init();

            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 8221);

            s.Bind(ipLocal);
            s.Listen(4);

            Console.WriteLine("Waiting for client ...");
           

            var myThread = new Thread(acceptClientThread);
            myThread.Start(s);


            byte[] buffer = new byte[4096];


            while (true)
            {
                
                String messageToSend = "";
                lock (outgoingMessages)
                {
                    if (outgoingMessages.First != null)
                    {
                        messageToSend = outgoingMessages.First.Value;

                        outgoingMessages.RemoveFirst();

                    }
                }

                if (messageToSend != "") // This is the kickback to the Client
                {
                    Console.WriteLine("sending message");
                    String[] substrings = messageToSend.Split(':');
                    string theClient = substrings[0];
                    string dungeonResult = substrings[1];

                    byte[] sendBuffer = encoder.GetBytes(dungeonResult);
                    int bytesSent = GetSocketFromName(theClient).Send(sendBuffer);
                    bytesSent = GetSocketFromName(theClient).Send(sendBuffer);

                }

                String ServerPrint = "";
                lock (incomingMessages)
                {
                    if (incomingMessages.First != null)
                    {
                        ServerPrint = incomingMessages.First.Value;

                        incomingMessages.RemoveFirst();

                    }
                }

                if (ServerPrint != "")
                {
                    Console.WriteLine(ServerPrint);


                    String[] substrings = ServerPrint.Split(':');

                    int PlayerID = Int32.Parse(substrings[0]); 
                    Console.WriteLine(substrings[0]);
                    var dungeonResult = dungeon.Process(substrings[1], PlayerList[PlayerID]);

                    String theClient = "client" + substrings[0];
                    Console.WriteLine(dungeonResult);

                    lock (outgoingMessages)
                    {
                        outgoingMessages.AddLast(theClient + ":" + dungeonResult);
                    }
                }

            }
            
        }
    }
}