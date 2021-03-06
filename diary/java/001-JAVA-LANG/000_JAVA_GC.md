# 名词

- Scavenge [ˈskævɪndʒ] 打扫
- perm permanent 永久的
- parallel [ˈpærəlel] 并行的

# 分代

## Young（年轻代） 

在GC前To 幸存区(survivor)保持清空,对象保存在 Eden 和 From 幸存区(survivor)中，GC运行时,Eden中的幸存对象被复制到 To 幸存区(survivor)。针对 From 幸存区(survivor)中的幸存对象，会考虑对象年龄,如果年龄没达到阀值(tenuring threshold)，对象会被复制到To 幸存区(survivor)。如果达到阀值对象被复制到老年代。复制阶段完成后，Eden 和From 幸存区中只保存死对象，可以视为清空。如果在复制过程中To 幸存区被填满了，剩余的对象会被复制到老年代中。最后 From 幸存区和 To幸存区会调换下名字，在下次GC时，To 幸存区会成为From 幸存区。 

## Tenured（年老代） 

年老代存放从年轻代存活的对象。一般来说年老代存放的都是生命期较长的对象。 

## Perm（持久代） 

于存放静态文件，如今Java类、方法等。持久代对垃圾回收没有显著影响，但是有些应用可能动态生成或者调用一些class，例如Hibernate等，在这种时候需要设置一个比较大的持久代空间来存放这些运行过程中新增的类。持久代大小通过-XX:MaxPermSize=<N>进行设置。 

## 吞吐量

只要GC线程是活动的，它们将与应用程序线程(application threads)争用当前可用CPU的时钟周期。 简单点来说，吞吐量是指应用程序线程用时占程序总用时的比例。 例如，吞吐量99/100意味着100秒的程序执行时间应用程序线程运行了99秒， 而在这一时间段内GC线程只运行了1秒。

## 暂停时间

指一个时间段内应用程序线程让与GC线程执行而完全暂停。 例如，GC期间100毫秒的暂停时间意味着在这100毫秒期间内没有应用程序线程是活动的。

# gc

## Scavenge GC 
一般情况下，当新对象生成，并且在Eden申请空间失败时，就好触发Scavenge GC，堆Eden区域进行GC，清除非存活对象，并且把尚且存活的对象移动到Survivor区。然后整理Survivor的两个区。

## Full GC 

对整个堆进行整理，包括Young、Tenured和Perm。Full GC比Scavenge GC要慢，因此应该尽可能减少Full GC。有如下原因可能导致Full GC：
 
- Tenured被写满 
- Perm域被写满 
- System.gc()被显示调用 
- 上一次GC之后Heap的各域分配策略动态变化 

# 收集器

## 串行收集器 

使用单线程处理所有垃圾回收工作，因为无需多线程交互，所以效率比较高。但是，也无法使用多处理器的优势，所以此收集器适合单处理器机器。当然，此收集器也可以用在小数据量（100M左右）情况下的多处理器机器上。可以使用-XX:+UseSerialGC打开。 

## 并行收集器 

对年轻代进行并行垃圾回收，因此可以减少垃圾回收时间。一般在多线程多处理器机器上使用。使用-XX:+UseParallelGC.打开。并行收集器在J2SE5.0第六6更新上引入，在Java SE6.0中进行了增强--可以堆年老代进行并行收集。如果年老代不使用并发收集的话，是使用单线程进行垃圾回收，因此会制约扩展能力。使用-XX:+UseParallelOldGC打开。 

使用-XX: ParallelGCThreads=<N>设置并行垃圾回收的线程数。此值可以设置与机器处理器数量相等。 

此收集器可以进行如下配置： 

最大垃圾回收暂停:指定垃圾回收时的最长暂停时间，通过-XX:MaxGCPauseMillis=<N>指定。<N>为毫秒.如果指定了此值的话，堆大小和垃圾回收相关参数会进行调整以达到指定值。设定此值可能会减少应用的吞吐量。 

吞吐量:吞吐量为垃圾回收时间与非垃圾回收时间的比值，通过-XX:GCTimeRatio=<N>来设定，公式为1/（1+N）。例如，-XX:GCTimeRatio=19时，表示5%的时间用于垃圾回收。默认情况为99，即1%的时间用于垃圾回收。 

## 并发收集器 

可以保证大部分工作都并发进行（应用不停止），垃圾回收只暂停很少的时间，此收集器适合对响应时间要求比较高的中、大规模应用。使用-XX:+UseConcMarkSweepGC打开。 

并发收集器主要减少年老代的暂停时间，他在应用不停止的情况下使用独立的垃圾回收线程，跟踪可达对象。在每个年老代垃圾回收周期中，在收集初期并发收集器会对整个应用进行简短的暂停，在收集中还会再暂停一次。第二次暂停会比第一次稍长，在此过程中多个线程同时进行垃圾回收工作。 

并发收集器使用处理器换来短暂的停顿时间。在一个N个处理器的系统上，并发收集部分使用K/N个可用处理器进行回收，一般情况下1<=K<=N/4。 

在只有一个处理器的主机上使用并发收集器，设置为incremental mode模式也可获得较短的停顿时间。 

浮动垃圾：由于在应用运行的同时进行垃圾回收，所以有些垃圾可能在垃圾回收进行完成时产生，这样就造成了“Floating Garbage”，这些垃圾需要在下次垃圾回收周期时才能回收掉。所以，并发收集器一般需要20%的预留空间用于这些浮动垃圾。 

Concurrent Mode Failure：并发收集器在应用运行时进行收集，所以需要保证堆在垃圾回收的这段时间有足够的空间供程序使用，否则，垃圾回收还未完成，堆空间先满了。这种情况下将会发生“并发模式失败”，此时整个应用将会暂停，进行垃圾回收。 

启动并发收集器：因为并发收集在应用运行时进行收集，所以必须保证收集完成之前有足够的内存空间供程序使用，否则会出现“Concurrent Mode Failure”。通过设置-XX:CMSInitiatingOccupancyFraction=<N>指定还有多少剩余堆时开始执行并发收集 

# 设置

http://ifeve.com/useful-jvm-flags-part-1-jvm-types-and-compiler-modes-2/

32位系统下，一般限制在1.5G~2G；64为操作系统对内存无限制。

整个堆大小=年轻代大小 + 年老代大小 + 持久代大小。

## -Xms and -Xmx 
允许我们指定JVM的初始和最大堆内存大小。一般来说，这两个参数的数值单位是Byte，但同时它们也支持使用速记符号，比如“k”或者“K”代表“kilo”，“m”或者“M”代表“mega”，“g”或者“G”代表“giga”。

    java -Xms128m -Xmx2g MyApp

-Xms和-Xmx实际上是-XX:InitialHeapSize和-XX:MaxHeapSize的缩写。我们也可以直接使用这两个参数，它们所起得效果是一样的

    java -XX:InitialHeapSize=128m -XX:MaxHeapSize=2g MyApp

## -XX:+HeapDumpOnOutOfMemoryError and -XX:HeapDumpPath

默认情况下，堆内存快照会保存在JVM的启动目录下名为java_pid<pid>.hprof 的文件里。
也可以通过设置-XX:HeapDumpPath=<path>来改变默认的堆内存快照生成路径，<path>可以是相对或者绝对路径。

## -XX: PermSize and -XX:MaxPermSize

    java -XX:PermSize=128m -XX:MaxPermSize=256m MyApp

这里设置的永久代大小并不会被包括在使用参数-XX:MaxHeapSize 设置的堆内存大小中。也就是说，通过-XX:MaxPermSize设置的永久代内存可能会需要由参数-XX:MaxHeapSize 设置的堆内存以外的更多的一些堆内存。

## -XX:InitialCodeCacheSize and -XX:ReservedCodeCacheSize

“代码缓存”，它是用来存储已编译方法生成的本地代码。如果代码缓存被占满，JVM会打印出一条警告消息，并切换到interpreted-only 模式：JIT编译器被停用，字节码将不再会被编译成机器码。因此，应用程序将继续运行，但运行速度会降低一个数量级，直到有人注意到这个问题。

## -XX:+UseCodeCacheFlushing

如果代码缓存不断增长，例如，因为热部署引起的内存泄漏，那么提高代码的缓存大小只会延缓其发生溢出。为了避免这种情况的发生，我们可以尝试一个有趣的新参数：当代码缓存被填满时让JVM放弃一些编译代码。

我们至少可以避免当代码缓存被填满的时候JVM切换到interpreted-only 模式。不过，我仍建议尽快解决代码缓存问题发生的根本原因，如找出内存泄漏并修复它。

## -XX:NewSize and -XX:MaxNewSize

新生代只是整个堆的一部分，新生代设置的越大，老年代区域就会减少。一般不允许新生代比老年代还大，因为要考虑GC时最坏情况，所有对象都晋升到老年代。(译者:会发生OOM错误) -XX:MaxNewSize 最大可以设置为-Xmx/2 .

## -XX:NewRatio=n

设置老年代与新生代的比例。例如 -XX:NewRatio=3 指定老年代/新生代为3/1. 老年代占堆大小的 3/4 ，新生代占 1/4 .

## -XX:SurvivorRatio=n

指定伊甸园区(Eden)与幸存区大小比例. 例如, -XX:SurvivorRatio=10 表示伊甸园区(Eden)是 幸存区To 大小的10倍(也是幸存区From的10倍).所以,伊甸园区(Eden)占新生代大小的10/12, 幸存区From和幸存区To 每个占新生代的1/12 .注意,两个幸存区永远是一样大的..

## -XX:+PrintTenuringDistribution

指定JVM 在每次新生代GC时，输出幸存区中对象的年龄分布。


## 收集器设置

### -XX:+UseSerialGC
我们使用该标志来激活串行垃圾收集器，例如单线程面向吞吐量垃圾收集器。 无论年轻代还是年老代都将只有一个线程执行垃圾收集。 该标志被推荐用于只有单个可用处理器核心的JVM。 在这种情况下，使用多个垃圾收集线程甚至会适得其反，因为这些线程将争用CPU资源，造成同步开销，却从未真正并行运行。

### -XX:+UseParallelGC

有了这个标志，我们告诉JVM使用多线程并行执行年轻代垃圾收集。 在我看来，Java 6中不应该使用该标志因为-XX:+UseParallelOldGC显然更合适。 需要注意的是Java 7中该情况改变了一点(详见本概述)，就是-XX:+UseParallelGC能达到-XX:+UseParallelOldGC一样的效果。

### -XX:+UseParalledlOldGC

该标志的命名有点不巧，因为”老”听起来像”过时”。 然而，”老”实际上是指年老代，这也解释了为什么-XX:+UseParallelOldGC要优于-XX:+UseParallelGC：除了激活年轻代并行垃圾收集，也激活了年老代并行垃圾收集。 当期望高吞吐量，并且JVM有两个或更多可用处理器核心时，我建议使用该标志。

### -XX: ParallelGCThreads

通过-XX: ParallelGCThreads=<value>我们可以指定并行垃圾收集的线程数量。 例如，-XX: ParallelGCThreads=6表示每次并行垃圾收集将有6个线程执行。 如果不明确设置该标志，虚拟机将使用基于可用(虚拟)处理器数量计算的默认值。 决定因素是由Java Runtime。availableProcessors()方法的返回值N，如果N<=8，并行垃圾收集器将使用N个垃圾收集线程，如果N>8个可用处理器，垃圾收集线程数量应为3+5N/8。

当JVM独占地使用系统和处理器时使用默认设置更有意义。 但是，如果有多个JVM(或其他耗CPU的系统)在同一台机器上运行，我们应该使用-XX: ParallelGCThreads来减少垃圾收集线程数到一个适当的值。 例如，如果4个以服务器方式运行的JVM同时跑在在一个具有16核处理器的机器上，设置-XX: ParallelGCThreads=4是明智的，它能使不同JVM的垃圾收集器不会相互干扰。

### -XX:GCTimeRatio

通过-XX:GCTimeRatio=<value>我们告诉JVM吞吐量要达到的目标值。 更准确地说，-XX:GCTimeRatio=N指定目标应用程序线程的执行时间(与总的程序执行时间)达到N/(N+1)的目标比值。 例如，通过-XX:GCTimeRatio=9我们要求应用程序线程在整个执行时间中至少9/10是活动的(因此，GC线程占用其余1/10)。 基于运行时的测量，JVM将会尝试修改堆和GC设置以期达到目标吞吐量。 -XX:GCTimeRatio的默认值是99，也就是说，应用程序线程应该运行至少99%的总执行时间。

### -XX:MaxGCPauseMillis

通过-XX:GCTimeRatio=<value>告诉JVM最大暂停时间的目标值(以毫秒为单位)。 在运行时，吞吐量收集器计算在暂停期间观察到的统计数据(加权平均和标准偏差)。 如果统计表明正在经历的暂停其时间存在超过目标值的风险时，JVM会修改堆和GC设置以降低它们。 需要注意的是，年轻代和年老代垃圾收集的统计数据是分开计算的，还要注意，默认情况下，最大暂停时间没有被设置。

## CMS



并发标记清理收集器(CMS收集器)的主要目标就是：低应用停顿时间。

CMS收集器处理老年代的对象,然而其操作要复杂得多。吞吐量收集器总是暂停应用程序线程，并且可能是相当长的一段时间，然而这能够使该算法安全地忽略应用程序。相比之下，CMS收集器被设计成在大多数时间能与应用程序线程并行执行，仅仅会有一点(短暂的)停顿时间。GC与应用程序并行的缺点就是，可能会出现各种同步和数据不一致的问题。为了实现安全且正确的并发执行，CMS收集器的GC周期被分为了好几个连续的阶段。

CMS收集器并没有任何碎片整理的机制。因此，应用程序有可能出现这样的情形，即使总的堆大小远没有耗尽，但却不能分配对象——仅仅是因为没有足够连续的空间完全容纳对象。当这种事发生后，并发算法不会帮上任何忙，因此，万不得已JVM会触发Full GC。回想一下，Full GC 将运行吞吐量收集器的算法，从而解决碎片问题——但却暂停了应用程序线程。因此尽管CMS收集器带来完全的并发性，但仍然有可能发生长时间的“stop-the-world”的风险。

第二个挑战就是应用的对象分配率高。如果获取对象实例的频率高于收集器清除堆里死对象的频率，并发算法将再次失败。从某种程度上说，老年代将没有足够的可用空间来容纳一个从年轻代提升过来的对象。这种情况被称为“并发模式失败”，并且JVM会执行堆碎片整理：触发Full GC。

### -XX:+UseConcMarkSweepGC

该标志首先是激活CMS收集器。默认HotSpot JVM使用的是并行收集器。

### -XX：UseParNewGC

当使用CMS收集器时，该标志激活年轻代使用多线程并行执行垃圾回收。这令人很惊讶，我们不能简单在并行收集器中重用-XX：UserParNewGC标志，因为概念上年轻代用的算法是一样的。然而，对于CMS收集器，年轻代GC算法和老年代GC算法是不同的，因此年轻代GC有两种不同的实现，并且是两个不同的标志。

注意最新的JVM版本，当使用-XX：+UseConcMarkSweepGC时，-XX：UseParNewGC会自动开启。因此，如果年轻代的并行GC不想开启，可以通过设置-XX：-UseParNewGC来关掉。

### -XX：+CMSConcurrentMTEnabled

当该标志被启用时，并发的CMS阶段将以多线程执行(因此，多个GC线程会与所有的应用程序线程并行工作)。该标志已经默认开启，如果顺序执行更好，这取决于所使用的硬件，多线程执行可以通过-XX：-CMSConcurremntMTEnabled禁用。

### -XX：ConcGCThreads

标志-XX：ConcGCThreads=<value>(早期JVM版本也叫-XX: ParallelCMSThreads)定义并发CMS过程运行时的线程数。比如value=4意味着CMS周期的所有阶段都以4个线程来执行。尽管更多的线程会加快并发CMS过程，但其也会带来额外的同步开销。因此，对于特定的应用程序，应该通过测试来判断增加CMS线程数是否真的能够带来性能的提升。

如果还标志未设置，JVM会根据并行收集器中的-XX：ParallelGCThreads参数的值来计算出默认的并行CMS线程数。该公式是ConcGCThreads = (ParallelGCThreads + 3)/4。因此，对于CMS收集器， -XX: ParallelGCThreads标志不仅影响“stop-the-world”垃圾收集阶段，还影响并发阶段。

### -XX:CMSInitiatingOccupancyFraction

该线索由 -XX:CMSInitiatingOccupancyFraction=<value>来设置，该值代表老年代堆空间的使用率。比如，value=75意味着第一次CMS垃圾收集会在老年代被占用75%时被触发。通常CMSInitiatingOccupancyFraction的默认值为68(之前很长时间的经历来决定的)。

### -XX：+UseCMSInitiatingOccupancyOnly

我们用-XX+UseCMSInitiatingOccupancyOnly标志来命令JVM不基于运行时收集的数据来启动CMS垃圾收集周期。而是，当该标志被开启时，JVM通过CMSInitiatingOccupancyFraction的值进行每一次CMS收集，而不仅仅是第一次。然而，请记住大多数情况下，JVM比我们自己能作出更好的垃圾收集决策。因此，只有当我们充足的理由(比如测试)并且对应用程序产生的对象的生命周期有深刻的认知时，才应该使用该标志。

### -XX:+CMSClassUnloadingEnabled

相对于并行收集器，CMS收集器默认不会对永久代进行垃圾回收。如果希望对永久代进行垃圾回收，可用设置标志-XX:+CMSClassUnloadingEnabled。在早期JVM版本中，要求设置额外的标志-XX:+CMSPermGenSweepingEnabled。注意，即使没有设置这个标志，一旦永久代耗尽空间也会尝试进行垃圾回收，但是收集不会是并行的，而再一次进行Full GC。

### -XX:+CMSIncrementalMode

该标志将开启CMS收集器的增量模式。增量模式经常暂停CMS过程，以便对应用程序线程作出完全的让步。因此，收集器将花更长的时间完成整个收集周期。因此，只有通过测试后发现正常CMS周期对应用程序线程干扰太大时，才应该使用增量模式。由于现代服务器有足够的处理器来适应并发的垃圾收集，所以这种情况发生得很少。

### -XX:+ExplicitGCInvokesConcurrent and -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses

如今,被广泛接受的最佳实践是避免显式地调用GC(所谓的“系统GC”)，即在应用程序中调用system.gc()。然而，这个建议是不管使用的GC算法的，值得一提的是，当使用CMS收集器时，系统GC将是一件很不幸的事，因为它默认会触发一次Full GC。幸运的是，有一种方式可以改变默认设置。标志-XX:+ExplicitGCInvokesConcurrent命令JVM无论什么时候调用系统GC，都执行CMS GC，而不是Full GC。第二个标志-XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses保证当有系统GC调用时，永久代也被包括进CMS垃圾回收的范围内。因此，通过使用这些标志，我们可以防止出现意料之外的”stop-the-world”的系统GC。

### -XX:+DisableExplicitGC

然而在这个问题上…这是一个很好提到- XX:+ DisableExplicitGC标志的机会，该标志将告诉JVM完全忽略系统的GC调用(不管使用的收集器是什么类型)。对于我而言，该标志属于默认的标志集合中，可以安全地定义在每个JVM上运行，而不需要进一步思考。

## 垃圾回收统计信息
### -XX:+PrintGC

参数-XX:+PrintGC（或者-verbose:gc）开启了简单GC日志模式，为每一次新生代（young generation）的GC和每一次的Full GC打印一行信息。

[GC 246656K->243120K(376320K), 0.0929090 secs]
[Full GC 243120K->241951K(629760K), 1.5589690 secs]

每行开始首先是GC的类型（可以是“GC”或者“Full GC”），然后是在GC之前和GC之后已使用的堆空间，再然后是当前的堆容量，最后是GC持续的时间（以秒计）。

### -XX:+PrintGCDetails

    [GC
        [PSYoungGen: 142816K->10752K(142848K)] 246648K->243136K(375296K), 0.0935090 secs
    ]
    [Times: user=0.55 sys=0.10, real=0.09 secs]

我们可以很容易发现：这是一次在young generation中的GC，它将已使用的堆空间从246648K减少到了243136K，用时0.0935090秒。此外我们还可以得到更多的信息：所使用的垃圾收集器（即PSYoungGen）、young generation的大小和使用情况（在这个例子中“PSYoungGen”垃圾收集器将young generation所使用的堆空间从142816K减少到10752K）。

既然我们已经知道了young generation的大小，所以很容易判定发生了GC，因为young generation无法分配更多的对象空间：已经使用了142848K中的142816K。我们可以进一步得出结论，多数从young generation移除的对象仍然在堆空间中，只是被移到了old generation：即使young generation几乎被完全清空（从142816K减少到10752K），但是所占用的堆空间仍然基本相同（从246648K到243136K）。

详细日志的“Times”部分包含了GC所使用的CPU时间信息，分别为操作系统的用户空间和系统空间所使用的时间。同时，它显示了GC运行的“真实”时间（0.09秒是0.0929090秒的近似值）。如果CPU时间（译者注：0.55秒+0.10秒）明显多于”真实“时间（译者注：0.09秒），我们可以得出结论：GC使用了多线程运行。这样的话CPU时间就是所有GC线程所花费的CPU时间的总和。

    [Full GC
        [PSYoungGen: 10752K->9707K(142848K)]
        [ParOldGen: 232384K->232244K(485888K)] 243136K->241951K(628736K)
        [PSPermGen: 3162K->3161K(21504K)], 1.5265450 secs
    ]

对于这三个generations，一样也可以看到所使用的垃圾收集器、堆空间的大小、GC前后的堆使用情况。需要注意的是显示堆空间的大小等于young generation和old generation各自堆空间的和。以上面为例，堆空间总共占用了241951K，其中9707K在young generation，232244K在old generation。Full GC持续了大约1.53秒，用户空间的CPU执行时间为10.96秒，说明GC使用了多线程（和之前一样8个线程）。

Full GC也可以通过显式的请求而触发，可以是通过应用程序，或者是一个外部的JVM接口。这样触发的GC可以很容易在日志里分辨出来，因为输出的日志是以“Full GC(System)”开头的，而不是“Full GC”。

### -XX:+PrintGCTimeStamps和-XX:+PrintGCDateStamps

使用-XX:+PrintGCTimeStamps可以将时间和日期也加到GC日志中。表示自JVM启动至今的时间戳会被添加到每一行中。

如果指定了-XX:+PrintGCDateStamps，每一行就添加上了绝对的日期和时间。

### -Xloggc:filename

缺省的GC日志时输出到终端的，使用-Xloggc:也可以输出到指定的文件。需要注意这个参数隐式的设置了参数-XX:+PrintGC和-XX:+PrintGCTimeStamps，但为了以防在新版本的JVM中有任何变化，我仍建议显示的设置这些参数。

## 可管理的JVM参数

HotSpot JVM有一类特别的参数叫做可管理的参数。对于这些参数，可以在运行时修改他们的值。我们这里所讨论的所有参数以及以“PrintGC”开头的参数都是可管理的参数。这样在任何时候我们都可以开启或是关闭GC日志。比如我们可以使用JDK自带的jinfo工具来设置这些参数，或者是通过JMX客户端调用HotSpotDiagnostic MXBean的setVMOption方法来设置这些参数。

## 并行收集器设置

- -XX: ParallelGCThreads=n:设置并行收集器收集时使用的CPU数。并行收集线程数。
- -XX: MaxGCPauseMillis=n:设置并行收集最大暂停时间
- -XX: GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)

## 并发收集器设置

- -XX:+CMSIncrementalMode:设置为增量模式。适用于单CPU情况。
- -XX: ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数。并行收集线程数。

# 调优总结

## 年轻代大小选择

响应时间优先的应用：尽可能设大，直到接近系统的最低响应时间限制（根据实际情况选择）。在此种情况下，年轻代收集发生的频率也是最小的。同时，减少到达年老代的对象。

吞吐量优先的应用：尽可能的设置大，可能到达Gbit的程度。因为对响应时间没有要求，垃圾收集可以并行进行，一般适合8CPU以上的应用。

## 年老代大小选择

响应时间优先的应用：年老代使用并发收集器，所以其大小需要小心设置，一般要考虑并发会话率和会话持续时间等一些参数。如果堆设置小了，可以会造成内存碎片、高回收频率以及应用暂停而使用传统的标记清除方式；如果堆大了，则需要较长的收集时间。最优化的方案，一般需要参考以下数据获得：

- 并发垃圾收集信息
- 持久代并发收集次数
- 传统GC信息
- 花在年轻代和年老代回收上的时间比例
- 减少年轻代和年老代花费的时间，一般会提高应用的效率

吞吐量优先的应用：一般吞吐量优先的应用都有一个很大的年轻代和一个较小的年老代。原因是，这样可以尽可能回收掉大部分短期对象，减少中期的对象，而年老代尽存放长期存活对象。

## 较小堆引起的碎片问题

因为年老代的并发收集器使用标记、清除算法，所以不会对堆进行压缩。当收集器回收时，他会把相邻的空间进行合并，这样可以分配给较大的对象。但是，当堆空间较小时，运行一段时间以后，就会出现“碎片”，如果并发收集器找不到足够的空间，那么并发收集器将会停止，然后使用传统的标记、清除方式进行回收。如果出现“碎片”，可能需要进行如下配置：

- -XX:+UseCMSCompactAtFullCollection：使用并发收集器时，开启对年老代的压缩。
- -XX:CMSFullGCsBeforeCompaction=0：上面配置开启的情况下，这里设置多少次Full GC后，对年老代进行压缩
