package com.edw.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.impl.query.RemoteQuery;
import org.infinispan.commons.configuration.XMLStringConfiguration;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <pre>
 *  com.edw.config.InfinispanInitializer
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 16 Oct 2024 16:05
 */
@Component
public class InfinispanInitializer implements CommandLineRunner {

    private RemoteCacheManager cacheManager;

    @Autowired
    public InfinispanInitializer(RemoteCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void run(String...args) throws Exception {
        Path proto = Paths.get(RemoteQuery.class.getClassLoader()
                .getResource("proto/Employee.proto").toURI());
        String protoBufCacheName = ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME;
        cacheManager.getCache(protoBufCacheName).put("Employee.proto", Files.readString(proto));
        cacheManager.administration().createCache("employee",
                new XMLStringConfiguration("<distributed-cache name=\"employee\" mode=\"ASYNC\" statistics=\"true\">\n" +
                        "\t<encoding media-type=\"application/x-protostream\" />\n" +
                        "\t<indexing enabled=\"true\"\n" +
                        "            storage=\"local-heap\">" +
                        "    <indexed-entities>\n" +
                        "      <indexed-entity>proto.Employee</indexed-entity>\n" +
                        "    </indexed-entities>" +
                        "\t</indexing>\n" +
                        "</distributed-cache>"));
    }
}