## 线程状态

- 初始态：new之后
- runable:start(),lockpool->获取锁，blocking->...
- running:runable->系统调度
- waitlist:runnig->wait(),await()
- lockpool:waitlist->notify[All](),running->synchronized,lock()
- blocking:running->join(),sleep(),io
- deal:running->run结束，main结束

## synchronized/wait()/notify()

wait()/notify()必须在synchronized块中。

wait()必须在while循环中。

## Atomic类

## ThreadLocal

## ReentranLock

    Lock lock = new ReentrantLock();
    lock.lock();
    try { 
      // update object state
    }
    finally {
      lock.unlock(); 
    }
    
    lock.newCondition()

## Condition

    await signal signalAll 

## ReentranReadWriteLock

    rwl.readLock().lock();
    rwl.readLock().unlock();
    rwl.writeLock().lock();
    rwl.writeLock().unlock();


## Queue

## ThreadPool

## 生产者-消费者

用 wait-notify 写一段代码来解决生产者-消费者问题。
只要记住在同步块中调用 wait() 和 notify()方法，如果阻塞，通过循环来测试等待条件。

