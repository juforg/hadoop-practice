package vip.appcity.hadooppractice.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author songjie
 */
public class WcMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String strline = value.toString();
//        System.out.println("strline:"+strline);
        String[] words = StringUtils.split(strline,' ');
		for (String ff : words) {
			context.write(new Text(ff), new IntWritable(1));
		}
//        StringTokenizer words = new StringTokenizer(strline);
//        while (words.hasMoreTokens()){
//            context.write(new Text(words.nextToken()),new IntWritable(1));
//        }
    }
}
