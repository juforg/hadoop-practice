package vip.appcity.hadooppractice.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

/**
 * @author songjie
 */
public class JobWc {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration config = new Configuration();
        // 在代码中设置JVM系统参数，用于给job对象来获取访问HDFS的用户身份

        config.set("fs.defaultFS","hdfs://sj-node2:8020");
        config.set("yarn.resourcemanager.hostname","sj-node3:8088");
        Job job= Job.getInstance(config);
        job.setJarByClass(JobWc.class);
        job.setJobName("wc");

        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPaths(job,"/wc/input/wc.txt");
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://sj-node2:8020"),config,"root");
        Path path = new Path("/wc/output");
        if(fileSystem.exists(path)){
            fileSystem.delete(path,true);
        }

        FileOutputFormat.setOutputPath(job,path);
        boolean b = job.waitForCompletion(true);
        if (b){
            System.out.println("job 执行完成");
        }else {
            System.out.println("job 执行失败");
        }


    }
}
