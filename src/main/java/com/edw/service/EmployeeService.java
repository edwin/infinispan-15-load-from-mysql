package com.edw.service;

import com.edw.model.Employee;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Employee> get() throws Exception {
        final RemoteCache cache = remoteCacheManager.getCache("employee");

        return (List<Employee>) cache.keySet().stream()
                .map(key -> {
                    return cache.get(key);
                })
                .collect(Collectors.toList());
    }

    public void save(Employee employee) throws Exception {
        final RemoteCache cache = remoteCacheManager.getCache("employee");
        cache.put(employee.getId(), employee);
    }

    public Employee get(Long id) throws Exception {
        final RemoteCache cache = remoteCacheManager.getCache("employee");
        return (Employee) cache.get(id);
    }
}
