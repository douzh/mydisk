# 打印所有XX参数及值

    -XX:+PrintFlagsFinal and -XX:+PrintFlagsInitial

我们也能指定参数-XX:+UnlockExperimentalVMOptions 和-XX:+UnlockDiagnosticVMOptions ；来解锁任何额外的隐藏参数。

如果我们只想看下所有XX参数的默认值，能够用一个相关的参数，-XX:+PrintFlagsInitial  。 用 -XX:+PrintFlagsInitial, 只是展示了第三列为“=”的数据（也包括那些被设置其他值的参数）。

    -XX:+PrintCommandLineFlags

这个参数让JVM打印出那些已经被用户或者JVM设置过的详细的XX参数的名称和值。


# -server and -client

有两种类型的 HotSpot JVM，即”server”和”client”。服务端的VM中的默认为堆提供了一个更大的空间以及一个并行的垃圾收集器，并且在运行时可以更大程度地优化代码。客户端的VM更加保守一些。

这样可以缩短JVM的启动时间和占用更少的内存。

客户端的VM只在32位系统中可用。

我们可以使用-server和-client参数来设置使用服务端或客户端的VM。

# -version and -showversion

    $ java -version
    java version "1.6.0_24"
    Java(TM) SE Runtime Environment (build 1.6.0_24-b07)
    Java HotSpot(TM) Client VM (build 19.1-b02, mixed mode, sharing)

输出显示的是Java版本号(1.6.0_24)和JRE确切的build号(1.6.0_24-b07)。我们也可以看到JVM的名字(HotSpot)、类型(client)和build ID（19.1-b02) ）。除此之外，我们还知道JVM以混合模式(mixed mode)在运行，这是HotSpot默认的运行模式，意味着JVM在运行时可以动态的把字节码编译为本地代码。我们也可以看到类数据共享（class data sharing）是开启的，类数据共享（class data sharing）是一种在只读缓存（在jsa文件中，”Java Shared Archive”）中存储JRE的系统类，被所有Java进程的类加载器用来当做共享资源。类数据共享(Class data sharing)可能在经常从jar文档中读所有的类数据的情况下显示出性能优势。

-showversion可以用来输出相同的信息，但是-showversion紧接着会处理并执行Java程序。因此，-showversion对几乎所有Java应用的命令行都是一个有效的补充。你永远不知道你什么时候，突然需要了解一个特定的Java应用（崩溃时）使用的JVM的一些信息。在启动时添加-showversion，我们就能保证当我们需要时可以得到这些信息。

# -Xint, -Xcomp, 和 -Xmixed

-Xint标记会强制JVM执行所有的字节码，当然这会降低运行速度，通常低10倍或更多。

-Xcomp参数与它（-Xint）正好相反，JVM在第一次使用时会把所有的字节码编译成本地代码，从而带来最大程度的优化。

-Xmixed最新版本的HotSpot的默认模式是混合模式，所以我们不需要特别指定这个标记。

# 参数分类

第一类包括了标准参数。 java -help

第二类是X参数，java -X来检索

第三类是包含XX参数。
对于布尔类型的参数，我们有”+”或”-“，然后才设置JVM选项的实际名称。例如，-XX:+<name>用于激活<name>选项，而-XX:-<name>用于注销选项。
对于需要非布尔值的参数，如string或者integer，我们先写参数的名称，后面加上”=”，最后赋值。例如，  -XX:<name>=<value>给<name>赋值<value>。


# -XX:+PrintCompilation and -XX:+CITime

当一个Java应用运行时，非常容易查看JIT编译工作。通过设置-XX:+PrintCompilation，我们可以简单的输出一些关于从字节码转化成本地代码的编译过程。
