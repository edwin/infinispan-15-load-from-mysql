package com.edw.service;

import com.edw.model.Employee;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  com.edw.service.EmployeeService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 16 Oct 2024 16:32
 */
@Service
public class EmployeeService {

    private RemoteCacheManager remoteCacheManager;

    @Autowired
    public EmployeeService(RemoteCacheManager remoteCacheManager) {
        this.remoteCacheManager = remoteCacheManager;
    }

    public List<Employee> getUsers() {
        final RemoteCache cache = remoteCacheManager.getCache("employee");
        QueryFactory queryFactory = Search.getQueryFactory(cache);
        Query<Employee> query = queryFactory.create("FROM proto.Employee order by id ASC");

        // execute the query
        return query.execute().list();
    }
}
