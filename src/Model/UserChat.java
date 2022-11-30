package Model;

public class UserChat {
    private String _email;
    private String _name;

    public String get_email() {
        return _email;
    }

    public String get_name() {
        return _name;
    }

    public String get_passwd() {
        return _passwd;
    }
    private String _passwd;

    public UserChat(){
    }
    public UserChat(String email, String name, String pwd){
        this._email = email;
        this._name = name;
        this._passwd = pwd;
    }
}
