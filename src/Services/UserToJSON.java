package Services;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserToJSON {
    private String _email;
    private String _name;
    private String _passwd;

    public UserToJSON(String ...params){
        this._email=params[0];
        this._passwd=params[1];
    }
    public String ConvertToJSON(){
        JSONArray jsonUser= new JSONArray();
        JSONObject myJObject = new JSONObject();
        myJObject.put("email",this._email);
        myJObject.put("passwd",this._passwd);
        jsonUser.put(myJObject);
        return jsonUser.toString();
    }
}
