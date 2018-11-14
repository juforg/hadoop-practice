package vip.appcity.hadooppractice.connections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class ConnectionsJob {
    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");
        Boolean flag = jobOne();
        if (flag) {
            jobTwo();
        }
    }

    static Boolean jobOne() {
        Boolean flag = true;
        Configuration config = new Configuration();
        config.set("fs.defaultFS", "hdfs://sj-node1:8020");
        config.set("yarn.resourcemanager.hostname", "sj-node03:8088");

        try {
            Job job = Job.getInstance(config);
            job.setJarByClass(ConnectionsJob.class);
            job.setJobName("2degree connections");

            job.setMapperClass(ConnMapper01.class);
            job.setReducerClass(ConnReduce01.class);

            job.setOutputKeyClass(FoF.class);
            job.setOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job, new Path("/input/qq.txt"));

            Path outpath = new Path("/tuijian/conn01");

            FileSystem fileSystem = FileSystem.get(config);

            if (fileSystem.exists(outpath)) {
                fileSystem.delete(outpath, true);
            }

            FileOutputFormat.setOutputPath(job, outpath);

            flag = job.waitForCompletion(true);
            if (flag) {
                System.out.println("job 执行成功");
            } else {
                System.out.println("job 执行失败");
            }

        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }

    static Boolean jobTwo() {
        Boolean flag = true;
        Configuration config = new Configuration();
        config.set("fs.defaultFS","hdfs://sj-node1:8020");
        config.set("yarn.resourcemanager.hostname","sj-node3:8088");
        try {
            Job job = Job.getInstance(config);
            job.setJarByClass(ConnectionsJob.class);
            job.setJobName("2degree conn job2");

            job.setMapperClass(ConnMapper02.class);
            job.setReducerClass(ConnReduce02.class);

            job.setMapOutputKeyClass(ConnSort.class);
            job.setMapOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job,new Path("/tuijian/conn01/"));

            Path path = new Path("/tuijian/conn02/");

            FileSystem fileSystem = FileSystem.get(config);
            if(fileSystem.exists(path)){
                fileSystem.delete(path,true);
            }

            FileOutputFormat.setOutputPath(job,path);
            flag = job.waitForCompletion(true);
            if (flag){
                System.out.println("job2 执行完成");
            }else{
                System.out.println("job2 执行失败");
            }


        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
