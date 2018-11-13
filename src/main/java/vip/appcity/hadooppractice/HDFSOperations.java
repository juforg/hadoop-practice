package vip.appcity.hadooppractice;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * @author songjie
 */
public class HDFSOperations {
    static Configuration config;
    static FileSystem fileSystem;

    public void configure() throws Exception {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.199.101:9000");
        fileSystem = FileSystem.get(URI.create("hdfs://192.168.199.101:9000"), configuration, "root");
    }

    public static void listFiles() throws IOException {
        Path path = new Path("/");
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(path, true);

        while (iterator.hasNext()) {
            LocatedFileStatus status = iterator.next();
            System.out.println(status.getPath().getName());
        }
    }

    public void rm() throws IOException {
        Path path = new Path("/");
        fileSystem.delete(path, true);
    }

    public static void mkdir() throws IOException {
        Path path = new Path("/test");
        fileSystem.mkdirs(path);
    }

    public static void uploadfiles() throws IOException {
        File file = new File("/Users/songjie/Documents/gitspace/hdfs-practice/src/main/resources/wc.txt");
        Path path = new Path("/wc/input/wc.txt");
        FSDataOutputStream fsDataOutputStream= fileSystem.create(path);
        IOUtils.copyBytes(new FileInputStream(file),fsDataOutputStream,config);
        System.out.println("上传结束");

    }

    public static void downloadfile() throws IOException {
        File file = new File("/Users/songjie/Documents/gitspace/hdfs-practice/src/main/resources/t.md");
        Path path = new Path("/wc/output/part-r-00000");
        FSDataInputStream fsDataInputStream = fileSystem.open(path);
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copyBytes(fsDataInputStream,fos,config);
        System.out.println("下载成功");
    }
    public static void listDic() throws IOException {
        Path path = new Path("/");
        FileStatus[] fileStatus =fileSystem.listStatus(path);
        for (int i = 0; i < fileStatus.length; i++) {
            FileStatus status = fileStatus[i];
            System.out.println(status.getPath()+"\t"
                    +status.getAccessTime()+"\t"
                    +status.getBlockSize()+"\t"
                    +status.getModificationTime()+"\t"
                    +status.getOwner()+"\t"
            );
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        config = new Configuration();
        config.set("fs.defaultFS","hdfs://sj-node2:8020");
        fileSystem = FileSystem.get(URI.create("hdfs://sj-node2:8020"),config,"root");
//        mkdir();
//        listDic();
//        uploadfiles();
        downloadfile();
        listFiles();

    }
}
