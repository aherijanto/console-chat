package Main;
import Handler.Messages;
import Handler.UserCheck;
import Handler.UserLogin;
import Handler.UserRegistration;
import Network.ConnectURI;
import Services.FileProfile;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainActivity {
    static String emailprofile;
    private static void ShowMenu(){
        System.out.println("Please Select Option");
        System.out.println("======================");
        System.out.println("[0] Login");
        System.out.println("[1] Register ");
        System.out.println("[2] Check User If Exist");
        System.out.println("[3] Send Messages");
        System.out.println("[4] Get Messages From User");
        System.out.println("[5] Logout");
        System.out.println("[X] Exit");
        System.out.print("Your Choice : ");
    }
    private static void ShowMessage(String _msg){
        System.out.println(_msg);
    }

    private static void RegisterUser(String x_email, String x_fullname, String x_password) throws IOException {
        UserCheck _isExist = new UserCheck(x_email);
        Boolean userIsExist = _isExist.isEmailExist();
        if(userIsExist){
            ShowMessage("User Already Exist...");
            ShowMessage("User Registration Canceled...\n");
        }else {
            UserRegistration _myUser = new UserRegistration();
            ShowMessage("Sending to server...");

            _myUser.Register(x_email, x_fullname, x_password);
            String _userData = _myUser.ConvertToJSON();
            ConnectURI uriBuilder = new ConnectURI();
            URL inetAddress = uriBuilder.buildURL("https://mimoapps.xyz/senang/register/");
            uriBuilder.postJSON(inetAddress, _userData);
            if(uriBuilder.response.toString().equals("failed")){
                ShowMessage("Please input a valid email address..\n");
            }else{
                ShowMessage("You have been register successfully...\n");
            }
        }
    }

    private static void CheckIfUserExist(String x_email) throws IOException {
        UserCheck _isExist = new UserCheck(x_email);
        Boolean userIsExist = _isExist.isEmailExist();
        if(userIsExist) {
            ShowMessage("User Already Exist...\n");
        }else{
            ShowMessage("User's Email Not Found...\n");
        }
    }

    private static void LoginToWorld(String email,String passwd) throws IOException {
        UserLogin userLogin = new UserLogin(email,passwd);
    }

    private static void Logout(){
        FileProfile logout = new FileProfile();
        logout.DestroyProfile();
    }

    private static void CheckLog(){
        FileProfile profile = new FileProfile();
        boolean filePrefetch = profile.ProfileExist();
        if(filePrefetch) {

            emailprofile = profile.ReadProfile();
            emailprofile = emailprofile.trim();
            ShowMessage("Status : You've logged as " + emailprofile);
        }else{
            ShowMessage("Status : Please Login...");
        }
    }

    private static void SendMessage(String to, String msg) throws IOException {
        Date msgDate = new Date( );
        SimpleDateFormat formatDate =
                new SimpleDateFormat("yyyy-MM-dd");

        Date msgTime = new Date( );
        SimpleDateFormat formatTime =
                new SimpleDateFormat("hh:mm:ss");
        Messages newMessage = new Messages();
        if(emailprofile==null){
            ShowMessage("Please login...\n");
        }else {
            newMessage.setToUser(to);
            newMessage.setFromUser(emailprofile);
            newMessage.setMsgDate(formatDate.format(msgDate));
            newMessage.setMsgTime(formatTime.format(msgTime));
            newMessage.setMsg(msg);
            Boolean statusSend = newMessage.SendMessage();
            if(statusSend){
                ShowMessage("Message sent...\n");
            }else{
                ShowMessage("Failed to send...\n");
            }
        }
    }
    private static void GetMessages(String username) throws IOException {
        Date msgDate = new Date( );
        SimpleDateFormat formatDate =
                new SimpleDateFormat("yyyy-MM-dd");

        Date msgTime = new Date( );
        SimpleDateFormat formatTime =
                new SimpleDateFormat("hh:mm:ss");
        Messages getUserMessage = new Messages();
        getUserMessage.setFromUser(emailprofile);
        getUserMessage.setToUser(username);
        getUserMessage.setMsgDate(formatDate.format(msgDate));
        getUserMessage.setMsgTime(formatTime.format(msgTime));
        getUserMessage.setMsg("get");
        getUserMessage.GetMessage();
    }
    public static void main(String[] args) {
        while(true){
            CheckLog();
            try{
                Scanner option = new Scanner(System.in);
                ShowMenu();
                char _yourChoice = option.next().charAt(0);

                switch (_yourChoice){
                    case '0':
                        Scanner userLogin = new Scanner(System.in);
                        ShowMessage("\nLogin to Server");
                        System.out.print("Username (email) : ");
                        String x_email = userLogin.nextLine();
                        System.out.print("Password : ");
                        String x_passwd = userLogin.nextLine();
                        ShowMessage("\nProcessing Login...");
                        LoginToWorld(x_email,x_passwd);
                        break;

                    case '1':
                        ShowMessage("\nRegister User");
                        Scanner register = new Scanner(System.in);
                        System.out.print("Email : ");
                        String _email = register.nextLine();
                        System.out.print("Fullname : ");
                        String _fullname = register.nextLine();
                        System.out.print("Password (max. 30 Character) : ");
                        String _pwd = register.nextLine();
                        System.out.print("Retype Password : ");
                        String _pwdRetype = register.nextLine();
                        if(!_pwd.equals(_pwdRetype)){
                            ShowMessage("Password is not the same...\n");
                        }else{
                            ShowMessage("Registering user...\n");
                            RegisterUser(_email,_fullname,_pwd);
                        }
                        break;
                    case '2':
                        ShowMessage("\nCheck Existing User");
                        try{
                            Scanner check = new Scanner(System.in);
                            System.out.print("Input Username (email) : ");
                            String emailuser = check.nextLine();
                            CheckIfUserExist(emailuser);
                        }catch(Exception e){
                        }
                        break;
                    case '3':
                        ShowMessage("\nSend Message");
                        Scanner sendMessage = new Scanner(System.in);
                        System.out.print("To : ");
                        String toUser = sendMessage.nextLine();
                        System.out.print("Message : ");
                        String msg = sendMessage.nextLine();
                        SendMessage(toUser,msg);
                        break;
                    case '4':
                        ShowMessage("\nGet Messages From Spesific User");
                        Scanner getMessage = new Scanner(System.in);
                        System.out.print("Type username : ");
                        String spesificUser = getMessage.nextLine();
                        GetMessages(spesificUser);
                        break;
                    case '5':
                        ShowMessage("Logging Out...\n");
                        Logout();
                        break;
                    case 'X':
                        System.out.println("Exit");
                        System.exit(0);
                        break;
                }
            }catch (Exception e){
            }
        }
    }
}
