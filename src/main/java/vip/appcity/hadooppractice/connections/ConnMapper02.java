package vip.appcity.hadooppractice.connections;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

public class ConnMapper02 extends Mapper<LongWritable, Text,ConnSort, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String s1 = StringUtils.split(s, ' ')[0];
        String s2 = StringUtils.split(s, ' ')[1];
        int s3 = Integer.parseInt(StringUtils.split(s, ' ')[2]);

        context.write(new ConnSort(s1,s2,s3),new IntWritable(s3));
    }
}
