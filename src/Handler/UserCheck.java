package Handler;

import Network.ConnectURI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class UserCheck {
    private String emailUser;
    public UserCheck(String _emailuser) {
        this.emailUser = _emailuser;
    }
    public Boolean isEmailExist() throws IOException {
        JSONArray jsonUser= new JSONArray();
        JSONObject myJObject = new JSONObject();
        myJObject.put("email",this.emailUser);
        jsonUser.put(myJObject);
        ConnectURI uriBuilder = new ConnectURI();
        URL inetAddress = uriBuilder.buildURL("https://mimoapps.xyz/senang/checkuser/");
        uriBuilder.postJSON(inetAddress,jsonUser.toString());
        if(uriBuilder.response.toString().equals("match")){
            return true;
        }else{
            return false;
        }
    }
}
