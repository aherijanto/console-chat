package Handler;
import Network.ConnectURI;
import Services.FileProfile;
import Services.UserToJSON;
import java.io.IOException;
import java.net.URL;

public class UserLogin {
    private final String _email;
    private final String _passwd;
    public UserLogin(String _email, String _passwd) throws IOException {
        this._email = _email;
        this._passwd = _passwd;
        UserCheck userProfile = new UserCheck(this._email);
        Boolean checkUser = userProfile.isEmailExist();
        if(checkUser){
            System.out.println("User exist...");
            Boolean userIsLogin = Login();
            if(userIsLogin){
                System.out.println("Login Successful..\n");
                FileProfile profile = new FileProfile();
                boolean filePrefetch = profile.ProfileExist();
                if(filePrefetch) {
//
                    String emailprofile = profile.ReadProfile();
                    if(emailprofile.equals(_email+"\n")){
                        System.out.println("You have already Logged In...");
                    }
                }else{
                    profile.Profiling(_email);
                }
            }else{
                System.out.println("Please Check Username or Password...\n");
            }
        }else{
            System.out.println("User Not Found, Please Register...\n");
        }
    }

    public Boolean Login() throws IOException {
        UserToJSON userProfile = new UserToJSON(this._email,this._passwd);
        String _profile = userProfile.ConvertToJSON();

        ConnectURI uriBuilder = new ConnectURI();
        URL inetAddress = uriBuilder.buildURL("https://mimoapps.xyz/senang/login/");
        uriBuilder.postJSON(inetAddress, _profile);
        return uriBuilder.response.toString().equals("found");
    }
}
