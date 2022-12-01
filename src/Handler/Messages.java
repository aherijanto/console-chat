package Handler;

import Network.ConnectURI;
import Services.MessageToJSON;

import java.io.IOException;
import java.net.URL;

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
        System.out.println(uriBuilder.response.toString());
        return true;
//        if(uriBuilder.response.toString().equals("send")){
//            return true;
//        }else{
//            return false;
//        }
    }
}
