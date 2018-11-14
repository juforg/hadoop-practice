package vip.appcity.hadooppractice.connections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

public class ConnReduce01 extends Reducer<FoF, IntWritable, Text, NullWritable> {
    @Override
    protected void reduce(FoF key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        boolean f = true;
        for(IntWritable val :values){
            if(val.get()==0){
                f=false;
                break;
            }
            sum += val.get();
        }
        if (f){
            String[] strings = StringUtils.split(key.toString(), '\t');
            String msg = strings[0]+ " " +strings[1] +" "+ sum;
            context.write(new Text(msg),NullWritable.get());
        }
    }
}
