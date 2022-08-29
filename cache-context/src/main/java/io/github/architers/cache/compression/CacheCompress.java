package io.github.architers.compression;

/**
 * @author luyi
 * 缓存压缩
 */
public interface CacheCompress {
    /**
     * 压缩
     *
     * @param str 需要压缩的字符串
     * @return 压缩后返回的字符串
     */
    String compress(String str);
}
