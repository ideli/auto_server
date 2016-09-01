package cn.mwee.auto.misc.main;


import java.io.File;
import java.util.Map.Entry;
import java.util.concurrent.ThreadPoolExecutor;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.mwee.auto.misc.common.Config;
import cn.mwee.auto.misc.common.server.BaseNioServer;
import cn.mwee.auto.misc.common.server.RestChannelHandler;
import cn.mwee.auto.misc.common.server.ServerAddress;
import cn.mwee.auto.misc.common.util.Utilities;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.handler.CorsConfig;
import cn.mwee.auto.misc.handler.CorsHandler;
import cn.mwee.auto.misc.handler.IRouter;
import cn.mwee.auto.misc.handler.StringHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;
import cn.mwee.auto.misc.server.ShutdownThread;


public class ServicesServer extends BaseNioServer {

    private static Logger log;

    static
    {
        /** 加载 log4j2 配置文件 */
        File file = new File("conf/log4j2.xml");
        String log4jCfgFile =file.getAbsolutePath();
        System.setProperty("log4j.configurationFile", log4jCfgFile);

        log = LoggerFactory.getLogger(ServicesServer.class);

//		checkHttpProxy();
    }

    protected RestChannelHandler restHandler = new RestChannelHandler(new OrderedMemoryAwareThreadPoolExecutor(50, 2*1048576, 10*1048576));

    public ServicesServer() {
    }

    public RestChannelHandler getRestHandler() {
        return restHandler;
    }

    @Override
    protected ChannelPipelineFactory getChannelPipelineFactory() {
        return new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                // Create a default pipeline implementation.
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new HttpRequestDecoder());
                pipeline.addLast("encoder", new HttpResponseEncoder());
                pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
                pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
                CorsConfig corsConfig = CorsConfig.withAnyOrigin().build();
                pipeline.addLast("cors",new CorsHandler(corsConfig));
                pipeline.addLast("query", restHandler);
                return pipeline;
            }
        };
    }

    @Override
    protected int defaultPort() {
        return 10001;
    }

    @Override
    public String serverName() {
        return "mw_auto";
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop()
    {
        //关闭netty，确保netty不接收新的请求
        log.info("shutting down netty");
        super.stop();
        log.info("shutting down RestChannelHandler");

        //关闭ExecutionHandler的线程池，确保完成线程池中已有的任务
        if(restHandler != null){
            ThreadPoolExecutor executor = (ThreadPoolExecutor)restHandler.getExecutor();
            if(executor != null){
                executor.shutdown();
            }
        }

        log.info("shutting down Service Api");
        //关闭应用定义的线程池，确保完成线程池中已有的任务
        ThreadPoolExecutor executor = Utilities.getExecutor();
        if(executor != null){
            executor.shutdown();
        }
    }

    @Override
    protected ServerAddress getServerAddress() {
        String listen = Config.get().get("server.listen");
        String address = Config.get().get("server.listen.address");
        String port = Config.get().get("server.listen.port");
        if(listen != null){
            return new ServerAddress(listen);
        }
        return new ServerAddress(address, (port!=null?Integer.parseInt(port):defaultPort()));
    }

    @Override
    protected ChannelUpstreamHandler finalChannelUpstreamHandler() {
        return null;
    }


    //private static final boolean SSL = System.getProperty("ssl") != null;
    //private static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));

    public static void main(String[] args) throws Exception
    {
        MyApplicationContext.getInstance();
        ServicesServer o = new ServicesServer();
        registerHandler(o, "jsonRouter");
        o.init();
        o.start();
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(o));
    }

    private static void registerHandler(ServicesServer server, String sRouter)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        IRouter router = (IRouter) MyApplicationContext.getInstance().getService(sRouter);
        if(router != null){
            for(Entry<String, String> entry : router.getMapRouter().entrySet()){
                BaseHandler handler = (BaseHandler) Class.forName(entry.getValue()).newInstance();
                server.getRestHandler().getRestProcosser().registerHandler(HttpMethod.POST, entry.getKey(), handler);
            }
        }
    }

    private static void registerGetHandler(ServicesServer server, String sRouter)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        IRouter router = (IRouter) MyApplicationContext.getInstance().getService(sRouter);
        if(router != null){
            for(Entry<String, String> entry : router.getMapRouter().entrySet()){
                BaseHandler handler = (BaseHandler) Class.forName(entry.getValue()).newInstance();
                server.getRestHandler().getRestProcosser().registerHandler(HttpMethod.GET, entry.getKey(), handler);
            }
        }
    }

    private static void registerStringHandler(ServicesServer server, String sRouter)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        IRouter router = (IRouter) MyApplicationContext.getInstance().getService(sRouter);
        if(router != null){
            for(Entry<String, String> entry : router.getMapRouter().entrySet()){
                StringHandler handler = (StringHandler) Class.forName(entry.getValue()).newInstance();
                server.getRestHandler().getRestProcosser().registerHandler(HttpMethod.POST, entry.getKey(), handler);
            }
        }
    }
}
