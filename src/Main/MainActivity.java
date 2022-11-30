package Main;
import Handler.UserCheck;
import Handler.UserRegistration;
import Network.ConnectURI;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity {
    private static void ShowMenu(){
        System.out.println("Please Select Option");
        System.out.println("======================");
        System.out.println("[0] Login");
        System.out.println("[1] Register ");
        System.out.println("[2] Check User If Exist");
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
    public static void main(String[] args) {
        while(true){
            try{
                Scanner option = new Scanner(System.in);
                ShowMenu();
                char _yourChoice = option.next().charAt(0);

                switch (_yourChoice){
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
