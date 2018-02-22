﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Server
{
    class server
    {
        static bool quit = false;
        static LinkedList<String> incommingMessages = new LinkedList<string>();

        class ReceiveThreadLaunchInfo
        {
            public ReceiveThreadLaunchInfo(int ID, Socket socket)
            {
                this.ID = ID;
                this.socket = socket;
            }

            public int ID;
            public Socket socket;
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

                ID++;
            }
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

                        lock (incommingMessages)
                        {
                            incommingMessages.AddLast(receiveInfo.ID + ":" + encoder.GetString(buffer, 0, result));
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

            var dungeon = new Dungeon(); 

            dungeon.Init();

            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 8221);

            s.Bind(ipLocal);
            s.Listen(4);

            Console.WriteLine("Waiting for client ...");

            Socket newConnection = s.Accept();
            var dungeonResult = (dungeon.RoomInfo());

            byte[] sendBuffer = encoder.GetBytes(dungeonResult); 
            int bytesSent = newConnection.Send(sendBuffer); //Sending Back Info


            if (newConnection != null)
            {
                while (true)
                {
                    byte[] buffer = new byte[4096];

                    try
                    {
                        int result = newConnection.Receive(buffer);

                        if (result > 0)
                        {
                            String recdMsg = encoder.GetString(buffer, 0, result);
                            Console.WriteLine("Received: " + recdMsg);

                            dungeonResult = dungeon.Process(recdMsg);


                            sendBuffer = encoder.GetBytes(dungeonResult); // this is sending back to client

                            bytesSent = newConnection.Send(sendBuffer);

                        }
                    }
                    catch (System.Exception ex)
                    {
                        Console.WriteLine(ex);
                    }

                }
            }
        }
    }
}