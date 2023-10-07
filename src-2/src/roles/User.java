package roles;

/**
 * @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */


public class User {
    //instance variables
    private String name;

    private String id;

    private String passWord;

    private String userName;


    public	User(String name,String id,String passWord,String userName) {
        this.name = name;
        this.id = id;
        this.passWord = passWord;
        this.userName = userName;
    }
    //getters
    public String getName() {
        return this.name;
    }
    public String getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.passWord;
    }

}