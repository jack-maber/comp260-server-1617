using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

#if TARGET_LINUX
using Mono.Data.Sqlite;
using sqliteConnection 	=Mono.Data.Sqlite.SqliteConnection;
using sqliteCommand 	=Mono.Data.Sqlite.SqliteCommand;
using sqliteDataReader	=Mono.Data.Sqlite.SqliteDataReader;
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
        sqliteConnection conn = null;
        string databaseName = "data.database";


        String currentRoom = "";

        public void Init()
        {
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

            //currentRoom = roomMap["Room 0"];

            try
            {
                sqliteConnection.CreateFile(databaseName);

                conn = new sqliteConnection("Data Source=" + databaseName + ";Version=3;FailIfMissing=True");

                sqliteCommand command;

                conn.Open();

                command = new sqliteCommand("create table table_rooms (name varchar(20), desc varchar(20), north varchar(20), south varchar(20), west varchar(20), east varchar(20))", conn);
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

                //command = new SQLiteCommand("drop table table_phonenumbers", conn);
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

            }
            catch (Exception ex)
            {
                Console.WriteLine("Create DB failed: " + ex);
            }

            currentRoom = "Room 0";
        }

        public void SetClientInRoom(Socket client, String room)
        {
            if (socketToRoomLookup.ContainsKey(client) == false)
            {
                socketToRoomLookup[client] = roomMap[room];
            }
        }

        public void RemoveClient(Socket client)
        {
            if (socketToRoomLookup.ContainsKey(client) == true)
            {
                socketToRoomLookup.Remove(client);
            }
        }

        public String RoomDescription(Socket client)
        {
            String desc = "";

            desc += socketToRoomLookup[client].desc + "\n";
            desc += "Exits" + "\n";
            for (var i = 0; i < socketToRoomLookup[client].exits.Length; i++)
            {
                if (socketToRoomLookup[client].exits[i] != null)
                {
                    desc += Room.exitNames[i] + " ";
                }
            }

            var players = 0;

            foreach(var kvp in socketToRoomLookup)
            {
                if( (kvp.Key != client)
                  &&(kvp.Value == socketToRoomLookup[client])
                  )
                {
                    players++;
                }
            }

            if(players > 0)
            {
                desc += "\n";
                desc += "There are " + players + " other dungeoneers in this room";
            }

            desc += "\n";

            return desc;
        }        
    }
}
