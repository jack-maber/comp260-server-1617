using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Security.Cryptography;

namespace passwords
{
    class Program
    {
        static byte[] GenerateHash(byte[] plainText)
        {
            HashAlgorithm algorithm = new SHA256Managed();

            return algorithm.ComputeHash(plainText);
        }

        static byte[] GenerateSaltedHash(byte[] plainText, byte[] salt)
        {
            HashAlgorithm algorithm = new SHA256Managed();

            byte[] plainTextWithSaltBytes =
              new byte[plainText.Length + salt.Length];

            for (int i = 0; i < plainText.Length; i++)
            {
                plainTextWithSaltBytes[i] = plainText[i];
            }
            for (int i = 0; i < salt.Length; i++)
            {
                plainTextWithSaltBytes[plainText.Length + i] = salt[i];
            }

            return algorithm.ComputeHash(plainTextWithSaltBytes);
        }

        public static bool CompareByteArrays(byte[] array1, byte[] array2)
        {
            if (array1.Length != array2.Length)
            {
                return false;
            }

            for (int i = 0; i < array1.Length; i++)
            {
                if (array1[i] != array2[i])
                {
                    return false;
                }
            }

            return true;
        }

        static void Main(string[] args)
        {
            var rng = new RNGCryptoServiceProvider();
            byte[] salt = new byte[16];


            bool quit = false;

            while (!quit)
            {
                Console.WriteLine("");
                Console.WriteLine("1-generate hash password");
                Console.WriteLine("2-generate hash password with salt");
                Console.WriteLine("X-quit\n");

                var entry = Console.ReadLine();

                switch (entry[0])
                {
                    case '1':
                        {
                            Console.Write("enter password:");
                            var password = Console.ReadLine();
                            var hash = GenerateHash(Encoding.UTF8.GetBytes(password));

                            Console.WriteLine("Hash:" + Convert.ToBase64String(hash));
                        }
                        break;

                    case '2':
                        {
                            Console.Write("enter password:");
                            var password = Console.ReadLine();

                            rng.GetBytes(salt);
                            var hash = GenerateSaltedHash(Encoding.UTF8.GetBytes(password), salt);

                            Console.WriteLine("Hash:" + Convert.ToBase64String(hash) + " salt:" + Convert.ToBase64String(salt) );
                        }
                        break;

                    case 'x':
                    case 'X':
                        quit = true;
                        break;
                }
            }  
        }
    }
}
