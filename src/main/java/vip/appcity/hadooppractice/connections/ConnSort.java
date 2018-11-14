package vip.appcity.hadooppractice.connections;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ConnSort implements WritableComparable<ConnSort> {
    private String friend1;
    private String friend2;
    private int hot;

    public ConnSort() {
        super();
    }

    public ConnSort(String friend1, String friedn2, int hot) {
        this.friend1 = friend1;
        this.friend2 = friedn2;
        this.hot = hot;
    }

    public String getFriend1() {
        return friend1;
    }

    public void setFriend1(String friend1) {
        this.friend1 = friend1;
    }

    public String getFriend2() {
        return friend2;
    }

    public void setFriend2(String friend2) {
        this.friend2 = friend2;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }


    @Override
    public int compareTo(ConnSort o) {
        int c = friend1.compareTo(o.getFriend1());
        if (c==0){
            return - Integer.compare(hot,o.getHot());
        }
        return c;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(friend1);
        out.writeUTF(friend2);
        out.writeInt(hot);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.friend1=in.readUTF();
        this.friend2=in.readUTF();
        this.hot=in.readInt();
    }
}
