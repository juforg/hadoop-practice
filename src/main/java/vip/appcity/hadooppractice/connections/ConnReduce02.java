package vip.appcity.hadooppractice.connections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ConnReduce02 extends Reducer<ConnSort, IntWritable, Text, NullWritable> {
    @Override
    protected void reduce(ConnSort key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum =0;
        for (IntWritable val : values) {
            sum =+ val.get();
        }
        String msg = key.getFriend1() + " "+key.getFriend2()+" "+sum;
        context.write(new Text(msg),NullWritable.get());

    }
}
