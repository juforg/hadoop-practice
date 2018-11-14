package vip.appcity.hadooppractice.connections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

public class ConnMapper01 extends Mapper {
    @Override
    protected void map(Object key, Object value, Context context) throws IOException, InterruptedException {
        String linestr = value.toString();
        String[] firends = StringUtils.split(linestr,'\t');
        for (int i = 1; i < firends.length; i++) {
            String firend = firends[i];
            context.write(new FoF(firends[0],firend),new IntWritable(0));

            for (int j = i+1; j < firends.length; j++) {
                String s = firends[j];
                context.write(new FoF(firend,s),new IntWritable(1));
            }
        }        
    }
}
