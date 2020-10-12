package rob.sample.madapp;

public class registerClass {

    public String cfullName;
    public String cEmail;
    public static String ccEmail;
    public String cPhone;

    public static String getccEmail() {
        return ccEmail;
    }


    public  String getcEmail() {
        return cEmail;
    }


    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public registerClass(String cfullName, String cEmail, String cPhone) {
        this.cfullName = cfullName;
        this.cEmail = cEmail;
        this.cPhone = cPhone;

    }
}
