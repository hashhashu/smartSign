package student.example.signstudent;

import android.app.Application;

public class NumberData extends Application {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void onCreate(){
        number = "null";
        super.onCreate();
    }
}