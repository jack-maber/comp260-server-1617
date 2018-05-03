using System;
using System.Net.Sockets;
namespace SUD
{

    public class Player
    {
        public string PlayerName { set; get; }

        public Room currentRoom;

        public Socket socket { set; get; }

        public Room GetRoom()
        {
            return currentRoom;
        }




















    }
}