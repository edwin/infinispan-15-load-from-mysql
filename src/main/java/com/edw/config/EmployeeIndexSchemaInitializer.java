package com.edw.config;

import com.edw.model.Employee;
import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.ProtoSchema;

/**
 * <pre>
 *  com.edw.config.EmployeeIndexSchemaInitializer
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 16 Oct 2024 16:15
 */
@ProtoSchema(
        includeClasses = {
                Employee.class
        },
        schemaFileName = "Employee.proto",
        schemaFilePath = "proto/",
        schemaPackageName = "proto")
public interface EmployeeIndexSchemaInitializer extends SerializationContextInitializer {
}