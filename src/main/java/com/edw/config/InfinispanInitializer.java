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
        cacheManager.administration().getOrCreateCache("employee",
                new XMLStringConfiguration("<replicated-cache name=\"employee\" statistics=\"true\">\n" +
                        " <encoding media-type=\"application/x-protostream\"/> " +
                        "\t<indexing enabled=\"true\"\n" +
                        "            storage=\"local-heap\">" +
                        "    <indexed-entities>\n" +
                        "      <indexed-entity>proto.Employee</indexed-entity>\n" +
                        "    </indexed-entities>" +
                        "\t</indexing>\n" +
                        "<persistence>" +
                        "    <table-jdbc-store xmlns=\"urn:infinispan:config:store:sql:15.0\"\n" +
                        "                      dialect=\"MYSQL\"\n" +
                        "                      shared=\"true\"\n" +
                        "                      table-name=\"t_employee\">\n" +
                        "     <connection-pool connection-url=\"jdbc:mysql://localhost:3306/test_db\"\n" +
                        "                      username=\"root\"\n" +
                        "                      password=\"password\"\n" +
                        "                      driver=\"com.mysql.cj.jdbc.Driver\"/>" +
                        "   <schema message-name=\"Employee\"\n" +
                        "              package=\"proto\"" +
                        "               embedded-key=\"true\"/>" +
                        "      <write-behind modification-queue-size=\"2048\"\n" +
                        "                    fail-silently=\"true\"/>\n" +
                        "    </table-jdbc-store>\n" +
                        "  </persistence>" +
                        "</replicated-cache>"));
    }
}