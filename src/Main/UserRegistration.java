package Main;

import Model.UserChat;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserRegistration {
    UserChat t_user;

    public void Register(String t_email,String t_fullname, String t_pwd){
        t_user = new UserChat(t_email,t_fullname,t_pwd);
        System.out.println(t_user.get_email());
        System.out.println(t_user.get_name());
        System.out.println(t_user.get_passwd());
    }

    public String ConvertToJSON(){
        JSONArray jsonUser= new JSONArray();

        //for(int u=0;u<_studentTI.size();u++){
        JSONObject myJObject = new JSONObject();
        myJObject.put("email",t_user.get_email());
        myJObject.put("name",t_user.get_name());
        myJObject.put("passwd",t_user.get_passwd());
        jsonUser.put(myJObject);
        return jsonUser.toString();
    }

}
