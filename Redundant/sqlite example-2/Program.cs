using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Concurrent;

#if TARGET_LINUX
using Mono.Data.Sqlite;
using sqliteConnection = Mono.Data.Sqlite.SqliteConnection;
using sqliteCommand = Mono.Data.Sqlite.SqliteCommand;
using sqliteDataReader = Mono.Data.Sqlite.SqliteDataReader;
#endif

#if TARGET_WINDOWS
using System.Data.SQLite;
using sqliteConnection = System.Data.SQLite.SQLiteConnection;
using sqliteCommand = System.Data.SQLite.SQLiteCommand;
using sqliteDataReader = System.Data.SQLite.SQLiteDataReader;
#endif

namespace SUD
{
    class Program
    {
        static bool quit = false;
        static ConcurrentQueue<ClientMessageBase> clientCommand = new ConcurrentQueue<ClientMessageBase>();
        static Dungeon dungeon;
        public bool DungeonCreated = false;


        //Gives the conneceted player an ID and a socket
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
        //Opens the accept thread and inserts the player into the dungeon
        static void acceptClientThread(Object obj)
        {
            Socket s = obj as Socket;

            int ID = 0;
            //While the server is still live, this will continue to run
            while (quit == false)
            {
                var command = new sqliteCommand("select * from " + "table_rooms" + " order by name asc", dungeon.conn);
                var newClientSocket = s.Accept();

                var myThread = new Thread(clientReceiveThread);
                myThread.Start(new ReceiveThreadLaunchInfo(ID, newClientSocket));

                ID++; //Iterates on the ID so that no two players have the same one
                dungeon.socket2player.Add(newClientSocket,"player"+ID);
                dungeon.player2socket.Add("player" + ID, newClientSocket);

                clientCommand.Enqueue(new ClientJoined(newClientSocket));

                {
                    try //Inserts the player into room zero, I.E. starting room
                    {
                        var sql = "insert into " + "table_players" + " (name, room) values ";
                        sql += "('" + "player" + ID + "'";
                        sql += ",";
                        sql += "'" + "Room 0" + "'";
                        sql += ")";

                        command = new sqliteCommand(sql, dungeon.conn);
                        command.ExecuteNonQuery();
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine("Failed to add player" + ex);
                    }
                }



                Console.WriteLine("Client added"); //Prints to the server that a client has been added
            }
        }
        //Gets the player socket and then receives the info they are sending back for processing 
        static void clientReceiveThread(Object obj)
        {
            ReceiveThreadLaunchInfo receiveInfo = obj as ReceiveThreadLaunchInfo;

            ASCIIEncoding encoder = new ASCIIEncoding();
            bool socketLost = false;

            while ((quit == false) && (socketLost == false)) //Only runs while the player is connected and the server is still live
            {
                byte[] buffer = new byte[4096];

                try
                {
                    int result = receiveInfo.socket.Receive(buffer);

                    if (result > 0)
                    {
                        clientCommand.Enqueue(new ClientMessage(receiveInfo.socket, encoder.GetString(buffer, 0, result)));
                    }
                }
                catch (System.Exception)
                {
                    clientCommand.Enqueue(new ClientLost(receiveInfo.socket));
                    socketLost = true;
                }
            }
        }

        //Main gameplay loop
        static void Main(string[] args)
        {
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            Console.WriteLine("Running from: " + "'" + "165.227.238.191" + "'");
            IPEndPoint ipLocal = new IPEndPoint(IPAddress.Parse("165.227.238.191"), 8222); //Uses the IP of the server so that players can connect

            s.Bind(ipLocal);
            s.Listen(4);

            dungeon = new Dungeon(); //Creates a new dungeon the initates it
            dungeon.Init();

            Console.WriteLine("Waiting for clients ...");

            var myThread = new Thread(acceptClientThread); //This allows it to take in player commands
            myThread.Start(s);

            ASCIIEncoding encoder = new ASCIIEncoding();
            byte[] outputBuffer;

            while (true)
            {
                ClientMessageBase command;

                if (clientCommand.TryDequeue(out command) == true)
                {
                    if (command is ClientJoined)
                    {
                        try
                        {
                            dungeon.SetClientInRoom(command.client, "Room 0"); //Set the player in room 0
                            String dungeonOutput = dungeon.RoomDescription(command.client);

                            dungeonOutput += "type 'help' for help\n";

                            try
                            {
                                command.client.Send(encoder.GetBytes(dungeonOutput)); //Sends the player the dungeon output
                            }
                            catch (Exception) { }

                            Console.WriteLine("Send client welcome: " + dungeonOutput);

                        }
                        catch (System.Exception)
                        {
                            clientCommand.Enqueue(new ClientLost(command.client));
                        }
                    }

                    if (command is ClientMessage) //Runs through the client reply and executes the command they want
                    {
                        var clientMessage = command as ClientMessage;

                        String outputToUser = dungeon.RoomDescription(clientMessage.client);

                        String[] input = clientMessage.message.Split(' '); //splits the input up

                        switch (input[0].ToLower()) //changes it to lower case so that it is easier to parse
                        {
                            case "help": //GIves user help such as other commands
                                outputToUser += "\nCommands are ....\n";
                                outputToUser += "help - for this screen\n";
                                outputToUser += "look - to look around\n";
                                outputToUser += "go [north | south | east | west]  - to travel between locations\n";
                                outputToUser += "say - talk to all the dungeoneers in the room\n";
                                outputToUser += "\n";

                                break;

                            case "look": //Gives the user a description of there current room 
                                outputToUser = dungeon.RoomDescription(clientMessage.client);
                                break;

                            case "say": //Activates global chat
                                outputToUser += "\nYou say: ";
                                for (var i = 1; i < input.Length; i++)
                                {
                                    outputToUser += input[i] + " ";
                                }

                                outputToUser += "\n";

                                {
                                    String messageToSend = "Someone says: ";

                                    for (var i = 1; i < input.Length; i++)
                                    {
                                        messageToSend += input[i] + " ";
                                    }

                                    outputBuffer = encoder.GetBytes(messageToSend);


                                    foreach (var kvp in dungeon.player2socket)
                                    {
                                            try
                                            {
                                                kvp.Value.Send(outputBuffer);
                                            }
                                            catch (Exception)
                                            { }
                                    }

                                }

                                break;

                            case "go": //Allows the players to move around the dungeon
                                var currentRoom = dungeon.GetRoom(clientMessage.client);

                                var SQLcommand = new sqliteCommand("select * from  table_rooms where name == '" + currentRoom + "'", dungeon.conn);
                                var reader = SQLcommand.ExecuteReader();

                                var isInNewRoom = false;
                                var newRoom = "";
                                while (reader.Read())
                                {

                                    String[] temp = { "north", "south", "east", "west" }; //Compares the player input of a room to the string of existing rooms

                                    for (var i = 0; i < temp.Length; i++)
                                    {
                                        if (reader[temp[i]] != null)
                                        {
                                            Console.Write(reader[temp[i]] + " ");
                                        }
                                    }

                                    if ((input[1].ToLower() == "north") && (reader["north"] != null)) //Checks if the move is valid against the current position of the room the player is in
                                    {
                                        newRoom = reader["north"].ToString();
                                        isInNewRoom = true;
                                    }
                                    else
                                    {
                                        if ((input[1].ToLower() == "south") && (reader["south"] != null))
                                        {
                                            newRoom = reader["south"].ToString();
                                            isInNewRoom = true;
                                        }
                                        else
                                        {
                                            if ((input[1].ToLower() == "east") && (reader["east"] != null))
                                            {
                                                newRoom = reader["east"].ToString();
                                                isInNewRoom = true;
                                            }
                                            else
                                            {
                                                if ((input[1].ToLower() == "west") && (reader["west"] != null))
                                                {
                                                    newRoom = reader["west"].ToString();
                                                    isInNewRoom = true;
                                                }
                                            }
                                        }
                                    }
                                }

                                if(isInNewRoom ==false) //If the player inputs an invalid room, inform them of that
                                {
                                    //handle error
                                    outputToUser = "\nERROR";
                                    outputToUser+="\nCan not go " + input[1] + " from here";
                                    outputToUser+="\nPress any key to continue";
                            
                                }
                                else //otherwise set them in said room, then send them the new room description
                                {
                                    dungeon.SetClientInRoom(clientMessage.client, newRoom);
                                    outputToUser = dungeon.RoomDescription(clientMessage.client);
                                }
                                break;
                        


                            default: //More general error throw
                                //handle error
                                outputToUser += "\nERROR";
                                outputToUser += "\nCan not " + clientMessage.message;
                                outputToUser += "\n";
                                break;
                        }

                        try //Sends message back to client
                        {
                            clientMessage.client.Send(encoder.GetBytes(outputToUser));
                            Console.WriteLine("Send client message: " + outputToUser);
                        }
                        catch (Exception) { }
                    }

                    if (command is ClientLost) //If the client disconnect, state that, and then remove them from the dungeon
                    {
                        var clientMessage = command as ClientLost;

                        Console.WriteLine("Client Lost");

                        dungeon.RemoveClient(clientMessage.client);
                    }
                }                       
            }
        }
    }
}