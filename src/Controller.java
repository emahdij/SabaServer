import java.io.*;

/**
 * Created by mehdi on 4/16/17.
 */
public class Controller {
    public static final String Source = "/home/mehdi/SabaAplicationSource/Server/";
    public static final String clientAddres = Source + "ClientAddress";
    public static final String CashAplication = Source + "Cash";
    public static final String PersonAddressTxt = Source + "Persons/Persons.txt";
    public static final String PersonAddressDir = Source + "Persons/";


//    public static void main(String[] args) throws IOException {
//        System.out.println(ClientOrder("192.168.65.56 1010 getChats 10 "));
//    }

    public static String ClientOrder(String target) {
        try {

            String[] arr = target.split("\\s+");
            FileReader Address = new FileReader(clientAddres);
            int a;
            String str = "";
            while (-1 != (a = Address.read())) {
                str += String.valueOf((char) a);
            }
            Address.close();
            int swich = 1;
            String[] arr2 = str.split("\\s+");
            for (int i = 0; i < arr2.length; i += 3) {
                if (arr2[i].equals(arr[2]))
                    swich = 0;
            }
            if (swich == 1) {
                FileWriter fileWriter = new FileWriter(clientAddres);
                for (int i = 0; i < arr2.length; i++) {
                    if (arr2[i].equals(arr[2])) {
                        i += 2;
                        continue;
                    }
                    fileWriter.write(arr2[i] + " ");
                }
                fileWriter.write(arr[2] + " " + arr[1] + " " + arr[0] + " \n");
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(clientAddres, true);
                fileWriter.write(arr[2] + " " + arr[1] + " " + arr[0] + " ");
                fileWriter.close();
            }


            if (arr[2].equals("SignUp"))
                return SignUp(target);
            else if (arr[2].equals("Signin"))
                return Signin(target);
            else if (arr[2].equals("FriendRequest"))
                return FriendRequest(target);
            else if (arr[2].equals("getChats"))
                return getChats(target);
            else if (arr[2].equals("getHistoryContacts"))
                return getHistoryContacts(target);
            else if (arr[2].equals("getNameNumber")) {
                return getNameNumber(target);
            } else
                return "NotFound!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public static String SignUp(String target) throws IOException {
        String[] arr = target.split("\\s+");
        File build = new File(PersonAddressTxt);
        String cach = "";
        int a;
        FileReader sign = new FileReader(PersonAddressTxt);
        while (-1 != (a = sign.read())) {
            cach += String.valueOf(((char) a));
        }
        sign.close();
        System.out.println("Client want sign up (numberClient: " + arr[3] + "  ,  " + "password : " + arr[4] + ")");
        String[] str = cach.split("\\s+");
        for (int i = 0; i < str.length; i += 4) {
            if (str[i].equals(arr[3])) {
                System.out.println("sign up failed the number is found!");
                return "false";
            }
        }
        new File(PersonAddressDir + arr[3]).mkdir();
        new File(PersonAddressDir + arr[3] + "/" + "Groups").mkdir();
        new File(PersonAddressDir + arr[3] + "/" + "Channels").mkdir();
        new File(PersonAddressDir + arr[3] + "/" + "ContactsMessage").mkdir();
        File build2 = new File(PersonAddressDir + arr[3] + "/Contacts");
        build2.createNewFile();
        File Groups = new File(PersonAddressDir + arr[3] + "/Groups/groupsInfo");
        File Channel = new File(PersonAddressDir + arr[3] + "/Channels/ChannelsInfo");
        File Chats = new File(PersonAddressDir + arr[3] + "/Chats");
        Groups.createNewFile();
        Channel.createNewFile();
        Chats.createNewFile();
        FileWriter signup = new FileWriter(PersonAddressTxt, true);
        signup.write(arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6] + " ");
        signup.close();
        System.out.println("SignUp " + arr[3] + " Succesfuuly!");
        return "True";
    }

    public static String Signin(String target) throws IOException {
        String[] arr = target.split("\\s+");
        String cach = "";
        int a;
        FileReader sign = new FileReader(PersonAddressTxt);
        while (-1 != (a = sign.read())) {
            cach += String.valueOf(((char) a));
        }
        sign.close();
        String[] str = cach.split("\\s+");
        System.out.println("Client want sign in (username: " + arr[3] + "  ,  password : " + arr[4] + ")");
        for (int i = 0; i < str.length; i += 4) {
            if (arr[3].equals(str[i]))
                if (arr[4].equals(str[i + 1])) {
                    System.out.println("sign in  Succesfully");
                    return "true";
                }
        }
        System.out.println("sign in  Failed  The username or password is wrong !");
        return "false";
    }

    public static String FriendRequest(String target) throws IOException {
        String[] arr = target.split("\\s+");
        System.out.println("Client want Friend (" + arr[3] + " want to friend by " + arr[4]);
        FileReader friendfound = new FileReader(PersonAddressTxt);
        int a;
        String string = "";
        while (-1 != (a = friendfound.read())) {
            string += String.valueOf((char) a);
        }
        String[] str2 = string.split("\\s+");
        int swich = 0;
        for (int i = 0; i < str2.length; i += 4) {
            if (str2[i].equals(arr[4])) {
                swich = 1;
                string = "";
                string += str2[i + 2];
            }
        }
        friendfound.close();
        if (swich == 0) {
            System.out.println("User not found!");
            return "UserNotFound";
        }
        FileReader friend = new FileReader(PersonAddressDir + arr[3] + "/Contacts");
        String str = "";
        while (-1 != (a = friend.read())) {
            str += String.valueOf((char) a);
        }
        friend.close();
        String[] arr2 = str.split("\\s+");
        for (int i = 0; i < arr2.length; i += 2) {
            if (arr2[i].equals(arr[4])) {
                System.out.println("you was friend!");
                return "YouFriended";
            }
        }
        File file = new File(PersonAddressDir + arr[3] + "/ContactsMessage/" + arr[4]);
        file.createNewFile();
        File file1 = new File(PersonAddressDir + arr[4] + "/ContactsMessage/" + arr[3]);
        file1.createNewFile();
        FileWriter friend2 = new FileWriter(PersonAddressDir + arr[3] + "/Contacts", true);
        friend2.write(arr[4] + " " + "0 ");
        friend2.close();
        FileWriter friend21 = new FileWriter(PersonAddressDir + arr[3] + "/Chats", true);
        friend21.write(arr[4] + " " + "1 ");
        friend21.close();
        FileWriter friend3 = new FileWriter(PersonAddressDir + arr[4] + "/Contacts", true);
        friend3.write(arr[3] + " " + "0 ");
        friend3.close();
        FileWriter friend31 = new FileWriter(PersonAddressDir + arr[4] + "/Chats", true);
        friend31.write(arr[3] + " " + "1 ");
        friend31.close();
        System.out.println("Rrquest succesfull");
        return string;
    }

    public static String getChats(String target) throws IOException {
        String[] arr = target.split("\\s+");
        System.out.println("Client : " + arr[2] + "  wants to get his previous messages ");
        FileReader friend = new FileReader(PersonAddressDir + arr[3] + "/Chats");
        int a;
        String cach = "";
        while (-1 != (a = friend.read())) {
            cach += String.valueOf(((char) a));
        }
        friend.close();
        System.out.println("The Chatnames was sent");
        return cach;
    }

    public static String getHistoryContacts(String target) throws IOException {
        String[] arr = target.split("\\s+");
        System.out.println("Client: " + arr[3] + " wants to get history message by " + arr[4]);
        String cach = " ";
        int a;
        FileReader sign = new FileReader(PersonAddressDir + arr[3] + "/ContactsMessage/" + arr[4]);
        while (-1 != (a = sign.read())) {
            cach += String.valueOf(((char) a));
        }
        sign.close();
        System.out.println("server : history message was sent");
        return cach;

    }

    public static String getNameNumber(String target) throws IOException {
        String[] arr = target.split("\\s+");
        System.out.println("Client : " + arr[3] + " want to get  users name");
        int a = 0;
        String cach = "";
        FileReader fileReader = new FileReader(PersonAddressTxt);
        while (-1 != (a = fileReader.read())) {
            cach += String.valueOf(((char) a));
        }
        fileReader.close();
        String finall = "";
        String[] str = cach.split("\\s+");
        for (int i = 0; i < arr.length - 4; i++) {
            for (int j = 0; j < str.length; j += 4) {
                if (arr[i + 4].equals(str[j])) {
                    finall += arr[i + 4] + " " + str[j + 2] + " ";
                    break;
                }
            }
        }
        System.out.println("The names was sent");
        return finall;
    }


}
