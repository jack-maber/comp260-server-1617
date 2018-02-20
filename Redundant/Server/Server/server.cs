using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using System.Net;
using System.Net.Sockets;

namespace Server
{
    class server
    {
        static void Main(string[] args)
        {
            Dungeon dungeon = new Dungeon();

            dungeon.Init();
            
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 8221);
			
            s.Bind(ipLocal);
            s.Listen(4);

            Console.WriteLine("Waiting for client ...");
            
            Socket newConnection = s.Accept();
            if (newConnection != null)
            {
                ASCIIEncoding encoder = new ASCIIEncoding();
                Console.WriteLine("Connected");
                while (true)
                {
                    byte[] buffer = new byte[4096];

                    try
                    {
                        int result = newConnection.Receive(buffer);
                        Console.WriteLine("Sending");
                        if (result > 0)
                        {
                            String userCmd = encoder.GetString(buffer, 0, result);

                            var dungeonResult = dungeon.Process(userCmd);

                            byte[] sendBuffer = encoder.GetBytes(dungeonResult);
                            int bytesSent = newConnection.Send(sendBuffer);
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
