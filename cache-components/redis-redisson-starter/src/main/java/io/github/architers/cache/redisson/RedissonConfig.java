package io.github.architers.redisson;

import io.netty.channel.EventLoopGroup;
import org.redisson.client.NettyHook;
import org.redisson.client.codec.Codec;
import org.redisson.config.*;
import org.redisson.connection.AddressResolverGroupFactory;
import org.redisson.connection.ConnectionListener;
import org.redisson.connection.ConnectionManager;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;

/**
 * @author luyi
 * redisson配置类
 */
public class RedissonConfig extends Config implements Serializable {

    private MasterSlaveServersConfig masterSlaveServersConfig;

    private SentinelServersConfig sentinelServersConfig;


    private SingleServerConfig singleServerConfig;

    private ClusterServersConfig clusterServersConfig;

    private ReplicatedServersConfig replicatedServersConfig;
    public RedissonConfig() {
        super();
    }

    public RedissonConfig(Config oldConf) {
        super(oldConf);
    }

    @Override
    public MasterSlaveServersConfig getMasterSlaveServersConfig() {
        return masterSlaveServersConfig;
    }

    @Override
    public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveServersConfig) {
        this.masterSlaveServersConfig = masterSlaveServersConfig;
    }

    @Override
    public SentinelServersConfig getSentinelServersConfig() {
        return sentinelServersConfig;
    }

    @Override
    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        this.sentinelServersConfig = sentinelServersConfig;
    }

    @Override
    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    @Override
    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    @Override
    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    @Override
    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    @Override
    public ReplicatedServersConfig getReplicatedServersConfig() {
        return replicatedServersConfig;
    }

    @Override
    public void setReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) {
        this.replicatedServersConfig = replicatedServersConfig;
    }

    @Override
    public NettyHook getNettyHook() {
        return super.getNettyHook();
    }

    @Override
    public Config setNettyHook(NettyHook nettyHook) {
        return super.setNettyHook(nettyHook);
    }

    @Override
    public Config setCodec(Codec codec) {
        return super.setCodec(codec);
    }

    @Override
    public Codec getCodec() {
        return super.getCodec();
    }

    @Override
    public boolean isReferenceEnabled() {
        return super.isReferenceEnabled();
    }

    @Override
    public void setReferenceEnabled(boolean redissonReferenceEnabled) {
        super.setReferenceEnabled(redissonReferenceEnabled);
    }

    @Override
    public ClusterServersConfig useClusterServers() {
        return super.useClusterServers();
    }

    @Override
    public ReplicatedServersConfig useReplicatedServers() {
        return super.useReplicatedServers();
    }

    @Override
    public void useCustomServers(ConnectionManager connectionManager) {
        super.useCustomServers(connectionManager);
    }

    @Override
    public SingleServerConfig useSingleServer() {
        return super.useSingleServer();
    }

    @Override
    public SentinelServersConfig useSentinelServers() {
        return super.useSentinelServers();
    }

    @Override
    public MasterSlaveServersConfig useMasterSlaveServers() {
        return super.useMasterSlaveServers();
    }

    @Override
    public boolean isClusterConfig() {
        return super.isClusterConfig();
    }

    @Override
    public boolean isSentinelConfig() {
        return super.isSentinelConfig();
    }

    @Override
    public int getThreads() {
        return super.getThreads();
    }

    @Override
    public Config setThreads(int threads) {
        return super.setThreads(threads);
    }

    @Override
    public Config setTransportMode(TransportMode transportMode) {
        return super.setTransportMode(transportMode);
    }

    @Override
    public TransportMode getTransportMode() {
        return super.getTransportMode();
    }

    @Override
    public Config setNettyThreads(int nettyThreads) {
        return super.setNettyThreads(nettyThreads);
    }

    @Override
    public int getNettyThreads() {
        return super.getNettyThreads();
    }

    @Override
    public Config setExecutor(ExecutorService executor) {
        return super.setExecutor(executor);
    }

    @Override
    public ExecutorService getExecutor() {
        return super.getExecutor();
    }

    @Override
    public Config setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        return super.setEventLoopGroup(eventLoopGroup);
    }

    @Override
    public EventLoopGroup getEventLoopGroup() {
        return super.getEventLoopGroup();
    }

    @Override
    public Config setLockWatchdogTimeout(long lockWatchdogTimeout) {
        return super.setLockWatchdogTimeout(lockWatchdogTimeout);
    }

    @Override
    public long getLockWatchdogTimeout() {
        return super.getLockWatchdogTimeout();
    }

    @Override
    public Config setKeepPubSubOrder(boolean keepPubSubOrder) {
        return super.setKeepPubSubOrder(keepPubSubOrder);
    }

    @Override
    public boolean isKeepPubSubOrder() {
        return super.isKeepPubSubOrder();
    }

    @Override
    public Config setAddressResolverGroupFactory(AddressResolverGroupFactory addressResolverGroupFactory) {
        return super.setAddressResolverGroupFactory(addressResolverGroupFactory);
    }

    @Override
    public AddressResolverGroupFactory getAddressResolverGroupFactory() {
        return super.getAddressResolverGroupFactory();
    }

    @Override
    public String toJSON() throws IOException {
        return super.toJSON();
    }

    @Override
    public String toYAML() throws IOException {
        return super.toYAML();
    }

    @Override
    public Config setUseScriptCache(boolean useScriptCache) {
        return super.setUseScriptCache(useScriptCache);
    }

    @Override
    public boolean isUseScriptCache() {
        return super.isUseScriptCache();
    }

    @Override
    public int getMinCleanUpDelay() {
        return super.getMinCleanUpDelay();
    }

    @Override
    public Config setMinCleanUpDelay(int minCleanUpDelay) {
        return super.setMinCleanUpDelay(minCleanUpDelay);
    }

    @Override
    public int getMaxCleanUpDelay() {
        return super.getMaxCleanUpDelay();
    }

    @Override
    public Config setMaxCleanUpDelay(int maxCleanUpDelay) {
        return super.setMaxCleanUpDelay(maxCleanUpDelay);
    }

    @Override
    public int getCleanUpKeysAmount() {
        return super.getCleanUpKeysAmount();
    }

    @Override
    public Config setCleanUpKeysAmount(int cleanUpKeysAmount) {
        return super.setCleanUpKeysAmount(cleanUpKeysAmount);
    }

    @Override
    public boolean isUseThreadClassLoader() {
        return super.isUseThreadClassLoader();
    }

    @Override
    public Config setUseThreadClassLoader(boolean useThreadClassLoader) {
        return super.setUseThreadClassLoader(useThreadClassLoader);
    }

    @Override
    public long getReliableTopicWatchdogTimeout() {
        return super.getReliableTopicWatchdogTimeout();
    }

    @Override
    public Config setReliableTopicWatchdogTimeout(long timeout) {
        return super.setReliableTopicWatchdogTimeout(timeout);
    }

    @Override
    public ConnectionListener getConnectionListener() {
        return super.getConnectionListener();
    }

    @Override
    public Config setConnectionListener(ConnectionListener connectionListener) {
        return super.setConnectionListener(connectionListener);
    }


}
