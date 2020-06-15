package student.example.signstudent;

public class Course {
    private String name,time,start,end,isOngoing,isSigned;

    public Course(String name,String time,String start,String end,String isOngning,String isSigned){
        this.name = name;
        this.time = time;
        this.start = start;
        this.end = end;
       this.isOngoing = isOngning;
       this.isSigned = isSigned;
    }

    public String getIsOngoing() {
        return isOngoing;
    }

    public void setIsOngoing(String isOngoing) {
        this.isOngoing = isOngoing;
    }

    public String getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(String isSigned) {
        this.isSigned = isSigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
