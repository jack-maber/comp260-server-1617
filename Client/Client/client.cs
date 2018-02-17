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
        static void Main(string[] args)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 8221);

			bool connected = false;

			while (connected == false) 
			{
				try 
				{
					s.Connect (ipLocal);
					connected = true;
				} 
				catch (Exception) 
				{
					Thread.Sleep (1000);
				}
			}

            

            while (true)
            {
                Console.WriteLine("Type Your Messsage");
                Console.Write("\n> ");
                var Msg = Console.ReadLine();

                try
                {
                    ASCIIEncoding encoder = new ASCIIEncoding();
                    byte[] buffer = encoder.GetBytes(Msg);
                    Console.WriteLine("Writing to server: " + Msg);
                    int bytesSent = s.Send(buffer);

                }
                catch (System.Exception ex)
                {
                    Console.WriteLine(ex);	
                }

                
                try
                {
                    byte[] buffer = new byte[4096];

                    int result = s.Receive(buffer);

                    if (result > 0)
                    {
                        ASCIIEncoding encoder = new ASCIIEncoding();
                        String recdMsg = encoder.GetString(buffer, 0, result);

                        Console.WriteLine("Received: " + recdMsg);

                        Thread.Sleep(5000);
                    }
                }
                catch (System.Exception ex)
                {
                    Console.WriteLine(ex);
                }

                Thread.Sleep(1000);
            }
        }
    }
}
