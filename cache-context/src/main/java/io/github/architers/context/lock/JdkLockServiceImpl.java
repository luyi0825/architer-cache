//package com.architecture.context.cache.lock;
//
//import org.springframework.cglib.core.ClassInfo;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @author luyi
// * jdk的锁
// */
//public class JdkLockServiceImpl implements LockService {
//    Map<String, CacheLock> lockMap = new ConcurrentHashMap<>();
//
//    class CacheLock {
//        private long expireTime;
//        private Lock lock;
//
//        public long getExpireTime() {
//            return expireTime;
//        }
//
//        public void setExpireTime(long expireTime) {
//            this.expireTime = expireTime;
//        }
//
//        public Lock getLock() {
//            return lock;
//        }
//
//        public void setLock(Lock lock) {
//            this.lock = lock;
//        }
//    }
//
//    @Override
//    public Lock tryLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
//        CacheLock lock = lockMap.get(lockName);
//        if (lock == null) {
//            synchronized (this) {
//                lock = lockMap.get(lockName);
//                if (lock == null) {
//                    lock = new CacheLock();
//                    lock.setLock(new ReentrantLock());
//                }
//                if (lock.getLock().tryLock(time, timeUnit)) {
//                    lockMap.put(lockName, lock);
//                    return lock.getLock();
//                }
//            }
//        } else {
//            if (lock.tryLock(time, timeUnit)) {
//                return lock;
//            }
//        }
//
//
//    }
//
//    @Override
//    public Lock tryLock(String lockName) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void unLock(Lock lock) {
//        lock.unlock();
//    }
//
//
//}
