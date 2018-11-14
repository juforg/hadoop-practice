package vip.appcity.hadooppractice.connections;


import org.apache.hadoop.io.Text;

public class FoF extends Text {
    public FoF() {
        super();
    }
    public FoF(String friends1,String friends2){
        set(getof(friends1,friends2));
    }

    public String getof(String friends1, String friends2){
        int c = friends1.compareTo(friends2);
        if(c > 0){
            return friends2+"\t" +friends1;
        }
        return friends1 + "\t" +friends2;
    }
}
