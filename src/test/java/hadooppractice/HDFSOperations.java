package hadooppractice;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

public class HDFSOperations {
    FileSystem fileSystem;

    @Before
    public void configure() throws Exception {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.199.101:8020");
        fileSystem = FileSystem.get(URI.create("hdfs://192.168.199.101:8020"), configuration, "root");
    }

    @Test
    public void listFiles() throws IOException {
        Path path = new Path("/");
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(path, true);

        while (iterator.hasNext()) {
            LocatedFileStatus status = iterator.next();
            System.out.println(status.getPath().getName());
        }
    }

    @Test
    public void rm() throws IOException {
        Path path = new Path("/");
        fileSystem.delete(path, true);
    }

    @Test
    public void mkdir() throws IOException {
        Path path = new Path("/demo");
        fileSystem.mkdirs(path);
    }
}
