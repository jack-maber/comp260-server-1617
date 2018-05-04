using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Net.Sockets;


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
    public class Dungeon
    {
        public Dictionary<Socket, string> socket2player;
        public Dictionary<string, Socket> player2socket;
        public sqliteConnection conn = null;
        string dungeon = "DungeonBase";


        //public String currentRoom = "";

        public void Init()
        {
            socket2player = new Dictionary<Socket, string>();
            player2socket = new Dictionary<string, Socket>();

            var roomMap = new Dictionary<string, Room>();
            {
                var room = new Room("Room 0", "You are standing in the entrance hall\nAll adventures start here");
                room.north = "Room 1";
                roomMap.Add(room.name, room);
            }

            {
                var room = new Room("Room 1", "You are in room 1");
                room.south = "Room 0";
                room.west = "Room 3";
                room.east = "Room 2";
                roomMap.Add(room.name, room);
            }

            {
                var room = new Room("Room 2", "You are in room 2");
                room.north = "Room 4";
                roomMap.Add(room.name, room);
            }

            {
                var room = new Room("Room 3", "You are in room 3");
                room.east = "Room 1";
                roomMap.Add(room.name, room);
            }

            {
                var room = new Room("Room 4", "You are in room 4");
                room.south = "Room 2";
                room.west = "Room 5";
                roomMap.Add(room.name, room);
            }

            {
                var room = new Room("Room 5", "You are in room 5");
                room.south = "Room 1";
                room.east = "Room 4";
                roomMap.Add(room.name, room);
            }



            try
            {
                sqliteConnection.CreateFile(dungeon);

                conn = new sqliteConnection("Data Source=" + dungeon + ";Version=3;FailIfMissing=True");

                sqliteCommand command;

                conn.Open();

                command = new sqliteCommand("create table table_rooms (name varchar(20), desc varchar(20), north varchar(20), south varchar(20), west varchar(20), east varchar(20))", conn);
                command.ExecuteNonQuery();

                command = new sqliteCommand("create table table_players (name varchar(20), room varcahr(6))", conn);
                command.ExecuteNonQuery();

                foreach (var kvp in roomMap)
                {
                    try
                    {
                        var sql = "insert into " + "table_rooms" + " (name, desc, north, south, west, east) values ";
                        sql += "('" + kvp.Key + "'";
                        sql += ",";
                        sql += "'" + kvp.Value.desc + "'";
                        sql += ",";
                        sql += "'" + kvp.Value.north + "'";
                        sql += ",";
                        sql += "'" + kvp.Value.south + "'";
                        sql += ",";
                        sql += "'" + kvp.Value.west + "'";
                        sql += ",";
                        sql += "'" + kvp.Value.east + "'";
                        sql += ")";

                        command = new sqliteCommand(sql, conn);
                        command.ExecuteNonQuery();
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine("Failed to add room" + ex);
                    }
                }


                try
                {
                    Console.WriteLine("");
                    command = new sqliteCommand("select * from " + "table_rooms" + " order by name asc", conn);
                    var reader = command.ExecuteReader();

                    while (reader.Read())
                    {
                        Console.WriteLine("Name: " + reader["name"] + "Exits: " + reader["north"] + reader["south"] + reader["west"] + reader["east"]);
                    }

                    reader.Close();
                    Console.WriteLine("");
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Failed to display DB");
                }

                //do player stuff



            }
            catch (Exception ex)
            {
                Console.WriteLine("Create DB failed: " + ex);
            }
        }

        public void SetClientInRoom(Socket client, String room)
        {
            if (socket2player.ContainsKey(client) == false)
            {
                socket2player[client] = "player";
            }

            try
            {
                var text = "update " + "table_players" + " set room= " + "'" + room + "'" + " where name = " + "'"+socket2player[client]+"'";
                            
                var command = new sqliteCommand(text, conn);
                command.ExecuteNonQuery();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex);
            }

#if false
            if (socketToRoomLookup.ContainsKey(client) == false)
            {
                socketToRoomLookup[client] = roomMap[room];
            }
#endif
        }

        public void RemoveClient(Socket client)
        {
#if false
            if (socketToRoomLookup.ContainsKey(client) == true)
            {
                socketToRoomLookup.Remove(client);
            }
#endif
        }

        public String RoomDescription(Socket client)
        {
            var currentRoom = GetRoom(client);

            var command = new sqliteCommand("select * from  table_rooms where name == '" + currentRoom + "'", conn);
            var reader = command.ExecuteReader();

            var result = "";

            reader.Read();
            {
                result = reader["desc"] as String;
                result += "\n";
                result += "Exits";



                if (reader["north"] as String != "")
                {
                    result += " north";
                }

                if (reader["west"] as String != "")
                {
                    result += " west";
                }

                if (reader["south"] as String != "")
                {
                    result += " south";
                }

                if (reader["east"] as String != "")
                {
                    result += " east";
                }
          
            }
            return result+"\n";
        }  


        public String GetRoom(Socket client)
        {
            var name = socket2player[client];

            var command = new sqliteCommand("select * from  table_players where name == '" + name + "'", conn);
            var reader = command.ExecuteReader();


            reader.Read();
            {
                return reader["room"] as string;
            }


            throw new Exception();

        }
    }
}
