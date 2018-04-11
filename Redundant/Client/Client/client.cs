using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Client
{
    class client
    {
        static LinkedList<String> incomingMessages = new LinkedList<string>();

        

        static void ReceiveMessages(Object obj)
        {
            ASCIIEncoding encoder = new ASCIIEncoding();
            byte[] receiveBuffer = new byte[8192];

            Socket s = obj as Socket;

            while (true)
            {
                try
                {
                    int reciever = s.Receive(receiveBuffer);
                    s.Receive(receiveBuffer);
                    if (reciever > 0)
                    {
                        String userCmd = encoder.GetString(receiveBuffer, 0, reciever);
                        Console.WriteLine(userCmd);
                    }
                }
                catch (System.Exception ex)
                {
                    Console.WriteLine(ex);
                }
            }
        }

        static void Main(string[] args)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("165.227.238.191"), 8221);

            bool connected = false;

            while (connected == false)
            {
                try
                {
                    s.Connect(ipLocal);
                    connected = true;
                }
                catch (Exception)
                {
                    Thread.Sleep(1000);
                }
            }


            int ID = 0;

            var myThread = new Thread(ReceiveMessages);
            myThread.Start(s);
            

            ASCIIEncoding encoder = new ASCIIEncoding();
            byte[] buffer = new byte[4096];

            while (true)
            {

                
                String Msg = Console.ReadLine();
                ID++;

                buffer = encoder.GetBytes(Msg);

                try
                {
                    
                    int bytesSent = s.Send(buffer);
                }
                catch (System.Exception ex)
                {
                    Console.WriteLine(ex);
                }

                
            }
        }
    }
}