package com.fct.daily.learn.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * HttpProxyUtils
 *
 * @author xstarfct
 * @version 2020-06-26 2:37 下午
 */
@Slf4j
public class HttpProxyUtils {
    
    /**
     * UTF-8编码
     */
    public static final String ENCODE_UTF8 = "UTF-8";
    
    /**
     * application/json方式提交
     */
    public static final String TYPE_JSON = "json";
    /**
     * application/x-www-form-urlencoded方式提交
     */
    public static final String TYPE_FORM = "form";
    
    /**
     * HTTP 连接池
     */
    private static PoolingHttpClientConnectionManager connManager;
    
    /**
     * 客户端池
     */
    private static final Map<String, CloseableHttpClient> closeableHttpClientMap = new HashMap<>();
    
    /**
     * host名
     */
    private static final Set<String> hostSet = new HashSet<>();
    
    /** 自定义dns */
    private static final CustomInMemoryDnsResolver dnsResolver = new CustomInMemoryDnsResolver();
    
    private static final String DEFAULT_CLIENT_KEY = "default";
    
    private static final Integer RETRY_TIMES = 3;
    
    /**
     * 绕过SSL证书验证
     *
     * @return
     */
    private static SSLContext createIgnoreVerifySSL() {
        try {
            SSLContext sc = SSLContext.getInstance("SSLv3");
            
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }
                
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }
                
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            
            sc.init(null, new TrustManager[]{trustManager}, null);
            return sc;
        } catch (Exception e) {
            log.error("获取SSL失败", e);
            return null;
        }
    }
    
    private static CloseableHttpClient getHttpClient(String proxyHost, Integer proxyPort) {
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connManager);
        
        if (StringUtils.isNotEmpty(proxyHost) && proxyPort != null) {
            HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
            DefaultProxyRoutePlanner defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(httpHost);
            httpClientBuilder.setRoutePlanner(defaultProxyRoutePlanner);
        }
        
        return httpClientBuilder.setConnectionManager(connManager).build();
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url            请求地址
     * @param paramsMap      请求参数
     * @param type           请求类型
     * @param resultEncoding 编码
     * @param proxyHost      代理主机
     * @param proxyPort      代理端口
     * @return
     * @throws IOException
     */
    public static String executePostMethod(String url, Map<String, String> paramsMap, String type, String resultEncoding,
            String proxyHost, Integer proxyPort) throws IOException {
        return executePostMethod(url, paramsMap, null, null, type, resultEncoding, proxyHost, proxyPort);
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url            请求地址
     * @param paramsMap      请求参数
     * @param resultEncoding 编码
     * @param proxyHost      代理主机
     * @param proxyPort      代理端口
     * @return
     * @throws IOException
     */
    public static String executePostMethod(String url, Map<String, String> paramsMap, String resultEncoding,
            String proxyHost, Integer proxyPort) throws IOException {
        return executePostMethod(url, paramsMap, null, null, null, resultEncoding, proxyHost, proxyPort);
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url            请求地址
     * @param paramsMap      请求参数
     * @param resultEncoding 编码
     * @return
     * @throws IOException
     */
    public static String executePostMethod(String url, Map<String, String> paramsMap, String type, String resultEncoding) throws IOException {
        return executePostMethod(url, paramsMap, null, null, type, resultEncoding, null, null);
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url       请求地址
     * @param map       请求参数
     * @param cookie    cookie
     * @param headers   请求头
     * @param type      请求类型
     * @param encoding  编码
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return
     * @throws IOException
     */
    public static String executePostMethod(String url, Map<String, String> map, String cookie, Header[] headers, String type, String encoding,
            String proxyHost, Integer proxyPort) throws IOException {
        // 创建自定义的httpclient对象
        //如果代理参数为空，则不使用代理
        CloseableHttpClient client = getHttpClient(proxyHost, proxyPort);
        
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        // 设置参数到请求对象中
        httpPost.setEntity(getHttpEntity(map, encoding, type));
        
        setHeadAndCookie(httpPost, headers, cookie);
        
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        return getResult(encoding, response);
        
    }
    
    private static StringEntity getHttpEntity(Map<String, String> map, String encoding, String type) throws UnsupportedEncodingException {
        if (TYPE_JSON.equals(type)) {
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(map));
            stringEntity.setContentEncoding(encoding);
            stringEntity.setContentType("application/json");
            return stringEntity;
        } else {
            // 装填参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (map != null) {
                for (Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            return new UrlEncodedFormEntity(nvps, encoding);
        }
    }
    
    /**
     * 执行post请求
     *
     * @param url  url地址
     * @param body 请求内容
     * @return java.lang.String
     * @author wxx
     * @date 2019/10/30 11:51 上午
     */
    public static String executePostMethod(String url, Object body) throws IOException {
        // 创建自定义的httpclient对象
        //如果代理参数为空，则不使用代理
        CloseableHttpClient client = getHttpClient(null, null);
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(body), ContentType.APPLICATION_JSON);
        // 设置参数到请求对象中
        httpPost.setEntity(stringEntity);
        
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        return getResult(ENCODE_UTF8, response);
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url       请求地址
     * @param map       请求参数
     * @param cookie    cookie
     * @param headers   请求头
     * @param type      请求类型
     * @param encoding  编码
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return
     * @throws IOException
     */
    public static String executePostMethod2(String url, Map<String, String> map, String cookie, Header[] headers, String type, String encoding,
            String proxyHost, Integer proxyPort) throws IOException {
        // 创建自定义的httpclient对象
        //如果代理参数为空，则不使用代理
        CloseableHttpClient client = getHttpClient(proxyHost, proxyPort);
        
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        // 设置参数到请求对象中
        httpPost.setEntity(getHttpEntity2(map, encoding, type));
        
        setHeadAndCookie(httpPost, headers, cookie);
        
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        return getResult(encoding, response);
        
    }
    
    private static StringEntity getHttpEntity2(Map<String, String> map, String encoding, String type) throws UnsupportedEncodingException {
        if (TYPE_JSON.equals(type)) {
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(map), encoding);
            stringEntity.setContentEncoding(encoding);
            stringEntity.setContentType("application/json");
            return stringEntity;
        } else {
            // 装填参数
            List<NameValuePair> nvps = new ArrayList<>();
            if (map != null) {
                for (Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            return new UrlEncodedFormEntity(nvps, encoding);
        }
    }
    
    
    public static String executeGetMethod(String url, String encoding) throws IOException {
        return executeGetMethod(url, null, null, null, null, encoding, null, null, null);
        
    }
    
    /**
     * 功能描述: <br> 执行get请求
     *
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param type      请求类型
     * @param encoding  编码
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @param client
     * @return
     * @throws IOException
     */
    public static String executeGetMethod(String url, Map<String, String> paramsMap, String cookie, Header[] headers, String type, String encoding,
            String proxyHost, Integer proxyPort, CloseableHttpClient client) throws IOException {
        // 创建自定义的httpclient对象
        //如果代理参数为空，则不使用代理
        if (client == null) {
            client = getHttpClient(proxyHost, proxyPort);
        }
        
        // 创建post方式请求对象
        if (paramsMap != null) {
            StringEntity params = getHttpEntity(paramsMap, encoding, type);
            url = url + "?" + params;
        }
        HttpGet httpGet = new HttpGet(url);
        recordHost(httpGet);
        setHeadAndCookie(httpGet, headers, cookie);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGet);
        return getResult(encoding, response);
        
    }
    
    public static Map<String, String> executeHeadMethod(String url) throws IOException {
        return executeHeadMethod(url, null, null, null, null, null);
        
    }
    
    
    /**
     * 功能描述: <br> 执行head请求
     *
     * @param url       请求地址
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @param client
     * @return 头信息
     * @throws IOException
     */
    public static Map<String, String> executeHeadMethod(String url, String cookie, Header[] headers,
            String proxyHost, Integer proxyPort, CloseableHttpClient client) throws IOException {
        // 创建自定义的httpclient对象
        //如果代理参数为空，则不使用代理
        if (client == null) {
            client = getHttpClient(proxyHost, proxyPort);
        }
        
        HttpHead httpHead = new HttpHead(url);
        recordHost(httpHead);
        setHeadAndCookie(httpHead, headers, cookie);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpHead);
        
        try {
            Map<String, String> headerMap = Maps.newHashMap();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                Header[] allHeaders = response.getAllHeaders();
                for (Header header : allHeaders) {
                    headerMap.put(header.getName(), header.getValue());
                }
            }
            return headerMap;
        } finally {
            response.close();
        }
        
    }
    
    /**
     * http get请求
     *
     * @param url
     * @param encoding
     * @param client
     * @return
     * @throws IOException
     */
    public static String executeGetMethod(String url, String encoding, CloseableHttpClient client) throws IOException {
        return executeGetMethod(url, null, null, null, null, encoding, null, null, client);
    }
    
    
    private static String getResult(String encoding, CloseableHttpResponse response) throws IOException {
        try {
            // 获取结果实体
            org.apache.http.HttpEntity entity = response.getEntity();
            String body = "";
            if (entity != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            
            return body;
        } finally {
            // 释放链接
            response.close();
        }
    }
    
    
    /**
     * http保持连接策略
     *
     * @return 策略
     */
    private static ConnectionKeepAliveStrategy getKeepAliveStrategy() {
        ConnectionKeepAliveStrategy myStrategy = null;
        
        try {
            myStrategy = (response, context) -> {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                
                // used to check host
                // HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                
                // keep alive for 30 seconds
                return 30 * 1000;
            };
        } catch (Exception e) {
            log.error("获取keepAlive失败", e);
        }
        
        return myStrategy;
    }
    
    /**
     * 重试机制
     *
     * @return 重试handler
     */
    private static HttpRequestRetryHandler getHttpRequestRetryHandler() {
        // 请求失败时,进行请求重试
        return (arg0, retryTimes, arg2) -> {
            if (retryTimes >= RETRY_TIMES) {
                return false;
            }
            if (arg0 instanceof UnknownHostException || arg0 instanceof ConnectTimeoutException
                    || !(arg0 instanceof SSLException) || arg0 instanceof NoHttpResponseException || arg0 instanceof SocketTimeoutException) {
                log.error("http请求异常！", arg0);
                return true;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(arg2);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            // 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的
            return idempotent;
        };
    }
    
    /**
     * 获取连接管理器
     *
     * @return 连接管理器
     */
    private static PoolingHttpClientConnectionManager getConnManager(Integer maxTotal, Integer maxPerRoute) {
        if (connManager != null) {
            return connManager;
        }
        
        // 采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();
        
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, dnsResolver);
        // 最大总连接数
        if (maxTotal != null) {
            connManager.setMaxTotal(maxTotal);
        }
        if (maxPerRoute != null) {
            // 每个路由最大可用连接数
            connManager.setDefaultMaxPerRoute(maxPerRoute);
        }
        
        return connManager;
    }
    
    /**
     * 获取http客户端
     *
     * @param proxyHost
     * @param proxyPort
     * @param clientKey
     * @param connReqTimeout
     * @param connTimeout
     * @param socketTimeout
     * @param maxPerRoute
     * @param maxTotal
     * @return http客户端
     */
    public static CloseableHttpClient getHttpClient(String proxyHost,
            Integer proxyPort,
            Integer connReqTimeout,
            Integer connTimeout,
            Integer socketTimeout,
            String clientKey,
            Integer maxTotal,
            Integer maxPerRoute) {
        if (StringUtils.isBlank(clientKey)) {
            clientKey = DEFAULT_CLIENT_KEY;
        }
        if (closeableHttpClientMap.get(clientKey) == null) {
            synchronized (HttpProxyUtils.class) {
                if (closeableHttpClientMap.get(clientKey) == null) {
                    // 获取连接管理器
                    PoolingHttpClientConnectionManager connManager = getConnManager(maxTotal, maxPerRoute);
                    
                    /*
                     * connectionRequestTimeout 从连接池中获取连接的超时时间 connectTimeout
                     * 与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间 socketTimeout
                     * socket读数据超时时间：从服务器获取响应数据的超时时间
                     */
                    RequestConfig requestConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(connReqTimeout)
                            .setConnectTimeout(connTimeout)
                            .setSocketTimeout(socketTimeout).build();
                    
                    HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connManager)
                            .setDefaultRequestConfig(requestConfig).setRetryHandler(getHttpRequestRetryHandler());
                    
                    if (StringUtils.isNotEmpty(proxyHost) && proxyPort != null) {
                        HttpHost httpHost = new HttpHost(proxyHost, proxyPort);
                        DefaultProxyRoutePlanner defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(httpHost);
                        httpClientBuilder.setRoutePlanner(defaultProxyRoutePlanner);
                    }
                    
                    closeableHttpClientMap.put(clientKey, httpClientBuilder.setConnectionManager(connManager)
                            .setKeepAliveStrategy(getKeepAliveStrategy()).build());
                }
            }
        }
        
        return closeableHttpClientMap.get(clientKey);
    }
    
    /**
     * 功能描述: <br> 执行post请求
     *
     * @param url       请求地址
     * @param map       请求参数
     * @param cookie    cookie
     * @param headers   请求头
     * @param type      请求类型
     * @param encoding  编码
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return
     * @throws IOException
     */
    public static String newExecutePostMethod(String url, Map<String, String> map, String cookie, Header[] headers,
            String type, String encoding, String proxyHost, Integer proxyPort) throws IOException {
        // 创建自定义的httpclient对象
        // 如果代理参数为空，则不使用代理
        CloseableHttpClient client = getHttpClient(proxyHost, proxyPort, null, null, null, null, null, null);
        
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        
        // 设置参数到请求对象中
        httpPost.setEntity(getHttpEntity(map, encoding, type));
        
        // 记录host，并进行dns解析
        recordHost(httpPost);
        setHeadAndCookie(httpPost, headers, cookie);
        
        // 执行请求操作，并拿到结果（连接数不足时同步阻塞） 防止连接失效，最高可请求3次
        String body = "";
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换结果实体为String类型 保证 返回数据的服务器绝对可信和返回的内容长度是有限的 (官方不推荐使用此方法)
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            
            return body;
        }
    }
    
    /**
     * 设置head and cookie
     *
     * @param httpPost
     * @param headers
     * @param cookie
     */
    public static void setHeadAndCookie(HttpRequestBase httpPost, Header[] headers, String cookie) {
        if (headers != null) {
            for (Header header : headers) {
                httpPost.setHeader(header);
            }
        }
        
        if (StringUtils.isNotBlank(cookie)) {
            httpPost.setHeader("Cookie", cookie);
        }
    }
    
    private static void recordHost(HttpRequestBase httpPost) throws IOException {
        // 记录host
        String host = httpPost.getURI().getHost();
        if (host != null) {
            hostSet.add(host);
        }
    }
    
}
