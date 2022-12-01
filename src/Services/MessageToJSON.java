package Services;

import Handler.Messages;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageToJSON {

    public String ConvertToJSON(String ...params){
        JSONArray jsonUserMessage= new JSONArray();
        JSONObject myJObject = new JSONObject();
        myJObject.put("fromusr",params[0]);
        myJObject.put("tousr",params[1]);
        myJObject.put("msgdate",params[2]);
        myJObject.put("msgtime",params[3]);
        myJObject.put("msg",params[4]);
        jsonUserMessage.put(myJObject);
        return jsonUserMessage.toString();
    }
}
