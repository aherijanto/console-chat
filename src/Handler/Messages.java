package Handler;

import Main.MainActivity;
import Network.ConnectURI;
import Services.FileProfile;
import Services.MessageToJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Messages {
    private String fromUser;
    private String toUser;
    private String msgDate;
    private String msgTime;


    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public Messages(){
    }

    public Boolean SendMessage() throws IOException {
        MessageToJSON message = new MessageToJSON();
        String jsonMessage = message.ConvertToJSON(this.fromUser,this.toUser,this.msgDate,this.msgTime,this.msg);
        ConnectURI uriBuilder = new ConnectURI();
        URL inetAddress = uriBuilder.buildURL("https://mimoapps.xyz/senang/send/");
        uriBuilder.postJSON(inetAddress,jsonMessage.toString());
        if(uriBuilder.response.toString().equals("send")){
            return true;
        }else{
            return false;
        }
    }

    public Boolean GetMessage() throws IOException {
        MessageToJSON userMessage = new MessageToJSON();
        String sendCriteria = userMessage.ConvertToJSON(this.fromUser,this.toUser,this.msgDate,this.msgTime,this.msg);
        System.out.println(sendCriteria);
        ConnectURI uriBuilder = new ConnectURI();
        URL inetAddress = uriBuilder.buildURL("https://mimoapps.xyz/senang/get/");
        uriBuilder.postJSON(inetAddress,sendCriteria.toString());
        String res = uriBuilder.response.toString();

        JSONArray messageArray = new JSONArray(res);
        JSONObject myJSONObject;
        int index = messageArray.length();
        Messages[] messageDisplay = new Messages[index];
        for (int i = 0; i < index; i++) {
            Messages s = new Messages();
            myJSONObject = messageArray.getJSONObject(i);
            s.setFromUser(myJSONObject.getString("from_usr"));
            s.setToUser(myJSONObject.getString("to_usr"));
            s.setMsgDate(myJSONObject.getString("msgdate"));
            s.setMsgTime(myJSONObject.getString("msgtime"));
            s.setMsg(myJSONObject.getString("msg"));
            messageDisplay[i]=s;
        }
        System.out.println("Messages are : ");
        System.out.println("-----------------------------------------------------------------------------------------");
        FileProfile userProfile = new FileProfile();
        String newProfile = userProfile.ReadProfile();
        String userFromTo;
        for(Messages msg : messageDisplay){
            if(msg.getFromUser().equals(newProfile)) {
                userFromTo = "You wrote :";
                System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+msg.getMsgDate() + " " + msg.getMsgTime() + " " + userFromTo + "\n");
                System.out.format("%80s", msg.getMsg()+"\n\n");
                //System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+msg.getMsg() + "\n\n");
            }else{
                userFromTo = msg.getFromUser()+" wrote :";
                System.out.print(msg.getMsgDate() + " " + msg.getMsgTime() + " " + userFromTo + "\n");
                System.out.print(msg.getMsg() + "\n\n");
            }
        }
        System.out.println("----------------------------------------------end message--------------------------------");
        System.out.println("\n");
        return true;

    }
}
