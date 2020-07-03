package com.fct.daily.dailylearn.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.impl.conn.InMemoryDnsResolver;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.util.Args;

/**
 * CustomInMemoryDnsResolver
 *
 * @author xstarfct
 * @version 2020-06-26 2:58 下午
 */
public class CustomInMemoryDnsResolver implements DnsResolver {
    
    private final Log log = LogFactory.getLog(InMemoryDnsResolver.class);
    private final Map<String, InetAddress[]> dnsMap = new ConcurrentHashMap();
    
    private DnsResolver systemDefaultResolver = SystemDefaultDnsResolver.INSTANCE;
    
    public CustomInMemoryDnsResolver() {
    }
    
    public void add(String host, InetAddress... ips) {
        Args.notNull(host, "Host name");
        Args.notNull(ips, "Array of IP addresses");
        this.dnsMap.put(host, ips);
    }
    
    public InetAddress[] resolve(String host) throws UnknownHostException {
        InetAddress[] resolvedAddresses = (InetAddress[]) this.dnsMap.get(host);
        if (this.log.isInfoEnabled()) {
            this.log.info("Resolving " + host + " to " + Arrays.deepToString(resolvedAddresses));
        }
        
        if (resolvedAddresses == null) {
            return systemDefaultResolver.resolve(host);
        } else {
            return resolvedAddresses;
        }
    }
}
