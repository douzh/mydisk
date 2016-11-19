http://www.cnblogs.com/xdp-gacl/tag/JavaWeb%E5%AD%A6%E4%B9%A0%E6%80%BB%E7%BB%93/default.html?page=3


# HTTP

## HTTP请求

客户端连上服务器后，向服务器请求某个web资源，称之为客户端向服务器发送了一个HTTP请求。

一个完整的HTTP请求包括如下内容：一个请求行、若干消息头、以及实体内容

### 请求行

请求行中的GET称之为请求方式，请求方式有：POST、GET、HEAD、OPTIONS、DELETE、TRACE、PUT，常用的有： GET、 POST

用户如果没有设置，默认情况下浏览器向服务器发送的都是get请求，例如在浏览器直接输地址访问，点超链接访问等都是get，用户如想把请求方式改为post，可通过更改表单的提交方式实现。

不管POST或GET，都用于向服务器请求某个WEB资源，这两种方式的区别主要表现在数据传递上：如果请求方式为GET方式，则可以在请求的URL地址后以?的形式带上交给服务器的数据，多个数据之间以&进行分隔，例如：GET /mail/1.html?name=abc&password=xyz HTTP/1.1

GET方式的特点：在URL地址后附带的参数是有限制的，其数据容量通常不能超过1K。
如果请求方式为POST方式，则可以在请求的实体内容中向服务器发送数据，Post方式的特点：传送的数据量无限制。


### 消息头

HTTP请求中的常用消息头

- accept:浏览器通过这个头告诉服务器，它所支持的数据类型
- Accept-Charset: 浏览器通过这个头告诉服务器，它支持哪种字符集
- Accept-Encoding：浏览器通过这个头告诉服务器，支持的压缩格式
- Accept-Language：浏览器通过这个头告诉服务器，它的语言环境
- Host：浏览器通过这个头告诉服务器，想访问哪台主机
- If-Modified-Since: 浏览器通过这个头告诉服务器，缓存数据的时间
- Referer：浏览器通过这个头告诉服务器，客户机是哪个页面来的  防盗链
- Connection：浏览器通过这个头告诉服务器，请求完后是断开链接还是何持链接


### 请求正文

请求正文可以用如下方式获取

    request.getInputStream();

## HTTP响应

一个HTTP响应代表服务器向客户端回送的数据，它包括:     一个状态行、若干消息头、以及实体内容 。

### 状态行

状态行格式：

    HTTP版本号　状态码　原因叙述<CRLF>

举例：

    HTTP/1.1 200 OK
 
状态码用于表示服务器对请求的处理结果，它是一个三位的十进制数。

### 常用响应头

HTTP响应中的常用响应头(消息头)

- Location: 服务器通过这个头，来告诉浏览器跳到哪里
- Server：服务器通过这个头，告诉浏览器服务器的型号
- Content-Encoding：服务器通过这个头，告诉浏览器，数据的压缩格式
- Content-Length: 服务器通过这个头，告诉浏览器回送数据的长度
- Content-Language: 服务器通过这个头，告诉浏览器语言环境
- Content-Type：服务器通过这个头，告诉浏览器回送数据的类型
- Refresh：服务器通过这个头，告诉浏览器定时刷新
- Content-Disposition: 服务器通过这个头，告诉浏览器以下载方式打数据
- Transfer-Encoding：服务器通过这个头，告诉浏览器数据是以分块方式回送的
- Expires: -1  控制浏览器不要缓存
- Cache-Control: no-cache  
- Pragma: no-cache


浏览器能接收(Content-Type)的数据类型有: 

- image/jpeg, 
- image/gif, 
- image/pjpeg, 
- application/x-ms-application, 
- application/xaml+xml, 
- application/x-ms-xbap, 
- application/vnd.ms-excel, 
- application/vnd.ms-powerpoint, 
- application/msword, 

### 302设置Location响应头，实现请求重定向

    response.setStatus(302);//设置服务器的响应状态码
    //设置响应头，服务器通过 Location这个头，来告诉浏览器跳到哪里，这就是所谓的请求重定向
    response.setHeader("Location", "/JavaWeb_HttpProtocol_Study_20140528/1.jsp");

服务器返回一个302状态码告诉浏览器，你要的资源我没有，但是我通过Location响应头告诉你哪里有，而浏览器解析响应头Location后知道要跳转到/JavaWeb_HttpProtocol_Study_20140528/1.jsp页面，所以就会自动跳转到1.jsp

### 设置Content-Encoding响应头，告诉浏览器数据的压缩格式

        String data = "abcdabcdabcdabcdabcdabcdab";
        System.out.println("原始数据的大小为：" + data.getBytes().length);
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gout = new GZIPOutputStream(bout); //buffer
        gout.write(data.getBytes());
        gout.close();
        //得到压缩后的数据
        byte g[] = bout.toByteArray();
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Content-Length",g.length +"");
        response.getOutputStream().write(g);

### 设置content-type响应头，指定回送数据类型

http://tool.oschina.net/commons

    response.setHeader("content-type", "image/jpeg");
    InputStream in = this.getServletContext().getResourceAsStream("/img/WP_20131005_002.jpg");
    byte buffer[] = new byte[1024];
    int len = 0;
    OutputStream out = response.getOutputStream();
    while ((len = in.read(buffer)) > 0) {
    out.write(buffer, 0, len);
    }

### 设置refresh响应头，让浏览器定时刷新

    /**
     * 设置refresh响应头，让浏览器每隔3秒定时刷新
     */
    // response.setHeader("refresh", "3");
    /**
     * 设置refresh响应头，让浏览器3秒后跳转到http://www.baidu.com
     */
    response.setHeader("refresh", "3;url='http://www.baidu.com'");
    response.getWriter().write("gacl");

### 设置content-disposition响应头，让浏览器下载文件

    response.setHeader("content-disposition", "attachment;filename=xxx.jpg");
    InputStream in = this.getServletContext().getResourceAsStream("/img/1.jpg");
    byte buffer[] = new byte[1024];
    int len = 0;
    OutputStream out = response.getOutputStream();
    while ((len = in.read(buffer)) > 0) {
	out.write(buffer, 0, len);
    }

# FROM

## enctype

设置表单的MIME编码
默认情况，这个编码格式是application/x-www-form-urlencoded，不能用于文件上传；只有使用了multipart/form-data，才能完整的传递文件数据。

不传文件时

    Content-Type	application/x-www-form-urlencoded

请求正文

u   sername=asdfasdf&pwd=123

上传文件时

    Content-Type	multipart/form-data; boundary=-----------------------------7e02d3132022e

其中boundary为文件数据的分隔符，用于区分上传多个文件。

请求正文

    -----------------------------7e0e9f2022e
    Content-Disposition: form-data; name="username"
    
    asdfasdfasdf
    -----------------------------7e0e9f2022e
    Content-Disposition: form-data; name="file1"; filename="C:\Users\douzh\Pictures\javanet7p.png"
    Content-Type: image/png
    
    <二进制文件数据未显示>
    ---------------------------7e0e9f2022e
    Content-Disposition: form-data; name="file2"; filename="C:\Users\douzh\Pictures\clojure-uml.png"
    Content-Type: image/png
    
    <二进制文件数据未显示>
    ---------------------------7e0e9f2022e--

# URL

在JavaWeb开发中，只要是写URL地址，那么建议最好以"/"开头，也就是使用绝对路径的方式，那么这个"/"到底代表什么呢？

可以用如下的方式来记忆"/"：

- 如果"/"是给服务器用的，则代表当前的web工程;
- 如果"/"是给浏览器用的，则代表webapps目录。

## "/"代表当前web工程的常见应用场景

    this.getServletContext().getRealPath("/download/1.JPG");

客户端请求某个web资源，服务器跳转到另外一个web资源，这个forward也是给服务器用的

    this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

    <%@include file="/jspfragments/head.jspf" %>
    <jsp:include page="/jspfragments/demo.jsp" />

## "/"代表webapps目录的常见应用场景

    response.sendRedirect("/appname/index.jsp");

response.sendRedirect("/项目名称/文件夹目录/页面");这种写法是将项目名称写死在程序中的做法，不灵活，万一哪天项目名称变了，此时就得改程序，所以推荐使用下面的灵活写法：

    response.sendRedirect(request.getContextPath()+"/index.jsp");

- 超链接跳转
- Form表单提交
- js脚本和css样式文件的引用

"/"是给浏览器使用的，此时"/"代表的就是webapps目录。

    <a href="${pageContext.request.contextPath}/index.jsp">跳转到首页</a>
    <form action="${pageContext.request.contextPath}/servlet/CheckServlet" method="post">
    
    <%--使用绝对路径的方式引用js脚本--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" type="text/css"/>

# servlet

## ServletConfig

web.xml中配制servlet时可以添加配制参数：

    <servlet>
        <servlet-name>ServletConfigDemo1</servlet-name>
        <servlet-class>com.iteedu.ServletConfigDemo1</servlet-class>
        <!--配置ServletConfigDemo1的初始化参数 -->
        <init-param>
            <param-name>name</param-name>
            <param-value>iteedu</param-value>
        </init-param>
         <init-param>
            <param-name>password</param-name>
            <param-value>123</param-value>
        </init-param>
        <init-param>
            <param-name>charset</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </servlet>

当servlet配置了初始化参数后，web容器在创建servlet实例对象时，会自动将这些初始化参数封装到ServletConfig对象中，并在调用servlet的init方法时，将ServletConfig对象传递给servlet。进而，我们通过ServletConfig对象就可以得到当前servlet的初始化参数信息。

    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取在web.xml中配置的初始化参数
        String paramVal = this.config.getInitParameter("name");//获取指定的初始化参数
    ...
    }

## ServletContext

WEB容器在启动时，它会为每个WEB应用程序都创建一个对应的ServletContext对象，它代表当前web应用。

WEB容器在启动时，它会为每个WEB应用程序都创建一个对应的ServletContext对象，它代表当前web应用。

### 共享数据

servlet1:

    ServletContext context = this.getServletContext();//获得ServletContext对象
    context.setAttribute("data", data);  //将data存储到ServletContext对象中

servlet2:

    ServletContext context = this.getServletContext();
    String data = (String) context.getAttribute("data");//从ServletContext对象中取出数据

### 获取WEB应用的初始化参数

    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
        http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
        <display-name></display-name>
        <!-- 配置WEB应用的初始化参数 -->
        <context-param>
            <param-name>url</param-name>
            <param-value>jdbc:mysql://localhost:3306/test</param-value>
        </context-param>
    
        <welcome-file-list>
            <welcome-file>index.jsp</welcome-file>
        </welcome-file-list>
    </web-app>

    ServletContext context = this.getServletContext();
    //获取整个web站点的初始化参数
    String contextInitParam = context.getInitParameter("url");

### 实现请求转发

    ServletContext context = this.getServletContext();//获取ServletContext对象
    RequestDispatcher rd = context.getRequestDispatcher("/servlet/ServletContextDemo5");//获取请求转发对象(RequestDispatcher)
    rd.forward(request, response);//调用forward方法实现请求转发

### 读取资源文件

    InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/gacl/servlet/study/db4.properties");
    String path = this.getServletContext().getRealPath("/WEB-INF/classes/db/config/db3.properties");
    InputStream in = new FileInputStream(path);
    Properties prop = new Properties();
    prop.load(in);

## HttpServletResponse

getOutputStream和getWriter这两个方法互相排斥，调用了其中的任何一个方法后，就不能再调用另一方法。  

Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，Servlet引擎将这些数据当作响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。 

Serlvet的service方法结束后，Servlet引擎将检查getWriter或getOutputStream方法返回的输出流对象是否已经调用过close方法，如果没有，Servlet引擎将调用close方法关闭该输出流对象。

### 常用功能

输出数据

    getOutputStream()
    getWriter()

添加响应头

    addDateHeader(String name,long date)
    addheader(String name,String value)
    addIntHeader(String name,int value)
    containsHeader(String name)
    setDateHeader(String name,long date)
    setheader(String name,String value)
    setIntHeader(String name,int value)

状态码

    setStatus(int sc)

- 404:SC_NOT_FOUND
- 200:SC_OK
- 500:SC_INTERNAL_SERVER_ERROR

### OutputStream输出中文

在服务器端，数据是以哪个码表输出的，那么就要控制客户端浏览器以相应的码表打开。

比如：

    outputStream.write("中国".getBytes("UTF-8"));

使用OutputStream流向客户端浏览器输出中文，以UTF-8的编码进行输出，此时就要控制客户端浏览器以UTF-8的编码打开，否则显示的时候就会出现中文乱码。

那么在服务器端如何控制客户端浏览器以以UTF-8的编码显示数据呢？

可以通过设置响应头控制浏览器的行为，例如：

    response.setHeader("content-type", "text/html;charset=UTF-8");

通过设置响应头控制浏览器以UTF-8的编码显示数据。

注意：

data.getBytes()是一个将字符转换成字节数组的过程，这个过程中一定会去查码表。

如果是中文的操作系统环境，默认就是查找查GB2312的码表。

将字符转换成字节数组的过程就是将中文字符转换成GB2312的码表上对应的数字。

比如： "中"在GB2312的码表上对应的数字是98。"国"在GB2312的码表上对应的数字是99

    String data = "中国";
    OutputStream outputStream = response.getOutputStream();
    response.setHeader("content-type", "text/html;charset=UTF-8");
    byte[] dataByteArr = data.getBytes("UTF-8");
    outputStream.write(dataByteArr);

响应头类型

    Content-Type: text/html;charset=UTF-8

### PrintWriter输出中文

在获取PrintWriter输出流之前首先使用"response.setCharacterEncoding(charset)"设置字符以什么样的编码输出到浏览器，如：response.setCharacterEncoding("UTF-8");设置将字符以"UTF-8"编码输出到客户端浏览器，然后再使用response.getWriter();获取PrintWriter输出流，这两个步骤不能颠倒。


    String data = "中国";
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'/>");
    out.write(data);


### 下载文件

    response.setHeader("content-disposition", "attachment;filename=xxx.jpg");
    InputStream in = this.getServletContext().getResourceAsStream("/img/1.jpg");
    byte buffer[] = new byte[1024];
    int len = 0;
    OutputStream out = response.getOutputStream();
    while ((len = in.read(buffer)) > 0) {
	out.write(buffer, 0, len);
    }

下载中文文件时，需要注意的地方就是中文文件名要使用URLEncoder.encode方法进行编码(URLEncoder.encode(fileName, "字符编码"))，否则会出现文件名乱码。

    response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));

### 生成验证码

    response.setHeader("refresh", "5");//设置refresh响应头控制浏览器每隔5秒钟刷新一次
    //1.在内存中创建一张图片
    BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
    //2.得到图片
    //Graphics g = image.getGraphics();
    Graphics2D g = (Graphics2D)image.getGraphics();
    g.setColor(Color.WHITE);//设置图片的背景色
    g.fillRect(0, 0, 80, 20);//填充背景色
    //3.向图片上写数据
    g.setColor(Color.BLUE);//设置图片上字体的颜色
    g.setFont(new Font(null, Font.BOLD, 20));
    g.drawString(makeNum(), 0, 20);
    //4.设置响应头控制浏览器浏览器以图片的方式打开
    response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
    //5.设置响应头控制浏览器不缓存图片数据
    response.setDateHeader("expries", -1);
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    //6.将图片写给浏览器
    ImageIO.write(image, "jpg", response.getOutputStream());

### 请求重定向

    response.sendRedirect("/JavaWeb_HttpServletResponse_Study_20140615/index.jsp");

sendRedirect方法内部调用了

    response.setHeader("Location", "/JavaWeb_HttpServletResponse_Study_20140615/index.jsp");
    response.setStatus(HttpServletResponse.SC_FOUND);//设置302状态码，等同于response.setStatus(302);


## HttpServletRequest

### 常用方法

获得客户机信息

- getRequestURL方法返回客户端发出请求时的完整URL。
- getRequestURI方法返回请求行中的资源名部分。
- getQueryString 方法返回请求行中的参数部分。
- getPathInfo方法返回请求URL中的额外路径信息。额外路径信息是请求URL中的位于Servlet的路径之后和查询参数之前的内容，它以“/”开头。
- getRemoteAddr方法返回发出请求的客户机的IP地址。
- getRemoteHost方法返回发出请求的客户机的完整主机名。
- getRemotePort方法返回客户机所使用的网络端口号。
- getLocalAddr方法返回WEB服务器的IP地址。
- getLocalName方法返回WEB服务器的主机名。

获得客户机请求头

- getHeader(string name)方法:String 
- getHeaders(String name)方法:Enumeration 
- getHeaderNames()方法

获得客户机请求参数(客户端提交的数据)

- getParameter(String)方法(常用)
- getParameterValues(String name)方法(常用)
- getParameterNames()方法(不常用)
- getParameterMap()方法(编写框架时常用)

# jsp

## JSP编译过程

### JSP->JAVA：pageEncoding

jsp编译成.java，容器会根据pageEncoding的设定读取jsp，结果是由指定的编码方案翻译成统一的UTF-8 JAVA源码（即.java）。

所以，pageEncoding应该和文件编码一样，如果pageEncoding设定错了，JAVA文件中的对应字符串就会是乱码，进而输出也是乱码。

注意：

pageEncoding的编码不会根据文件编码自动修改，需要自己指定，最好在开发前定义好，配制好开发环境，开发过程中尽量不要修改。

比如，把JSP由GBK改为UTF-8，pageEncoding不会自动改为UTF-8，要开发人员自己修改。

关键是文件编码我们不能一眼看出来，如果IDE生成的文件编码和pageEncoding不一致，将是很麻烦的一件事。

### JAVA->CLASS

JAVA源码至java byteCode的编译，不论JSP编写时候用的是什么编码方案，经过这个阶段的结果全部是UTF-8的encoding的java源码。

JAVAC用UTF-8的encoding读取java源码，编译成UTF-8
encoding的二进制码（即.class），这是JVM对常数字串在二进制码（java encoding）内表达的规范。

### contentType

    <%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
    
    contentType="text/html; charset=UTF-8"

在JSP被编译成servlet后，被翻译成：

    response.setContentType("text/html;charset=UTF-8");

实质是在设置响应头,并指定字符编码,功能同下：

    response.setHeader("content-type", "text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");


##  语法

JSP表达式

语法：<%= 变量或表达式 %>

JSP脚本片断

    <% 
         多行java代码 
    %>

JSP声明

JSP页面中编写的所有代码，默认会翻译到servlet的service方法中， 而Jsp声明中的java代码被翻译到_jspService方法的外面。

    <%！ 
        　java代码
    %>

## 指令

JSP指令的基本语法格式：<%@ 指令 属性名="值" %>

在JSP 2.0规范中共定义了三个指令：

- page指令
- Include指令
- taglib指令

### Page指令

page指令用于定义JSP页面的各种属性，无论page指令出现在JSP页面中的什么地方，它作用的都是整个JSP页面，为了保持程序的可读性和遵循良好的编程习惯，page指令最好是放在整个JSP页面的起始位置。

    <%@ page 
        [ language="java" ] 
        [ extends="package.class" ] 
        [ import="{package.class | package.#}, ..." ] 
        [ session="true | false" ] 
        [ buffer="none | 8kb | sizekb" ] 
        [ autoFlush="true | false" ] 
        [ isThreadSafe="true | false" ] 
        [ info="text" ] 
        [ errorPage="relative_url" ] 
        [ isErrorPage="true | false" ] 
        [ contentType="mimeType [ ;charset=characterSet ]" | "text/html ; charset=ISO-8859-1" ] 
        [ pageEncoding="characterSet | ISO-8859-1" ] 
        [ isELIgnored="true | false" ] 
    %>

### include指令

include指令用于引入其它JSP页面，如果使用include指令引入了其它JSP页面，那么JSP引擎将把这两个JSP翻译成一个servlet。所以include指令引入通常也称之为静态引入。

语法：

    <%@ include file="relativeURL"%>

include指令细节注意问题：

被引入的文件必须遵循JSP语法。

被引入的文件可以使用任意的扩展名，即使其扩展名是html，JSP引擎也会按照处理jsp页面的方式处理它里面的内容，为了见明知意，JSP规范建议使用.jspf（JSP fragments(片段)）作为静态引入文件的扩展名。

由于使用include指令将会涉及到2个JSP页面，并会把2个JSP翻译成一个servlet，所以这2个JSP页面的指令不能冲突（除了pageEncoding和导包除外）。


### taglib指令

    <%@taglib uri="标签库的uri"  prefix="标签的使用前缀"%>

## 9大隐式对象

- pageContext：javax.servlet.jsp.PageContext
- request    ：javax.servlet.http.HttpServletRequest
- response   ：javax.servlet.http.HttpServletResponse
- session    ：javax.servlet.http.HttpSession
- application：javax.servlet.ServletContext
- config     ：javax.servlet.ServletConfig
- out        ：javax.servlet.jsp.JspWriter
- page       ：java.lang.Object
- exception  ：java.lang.Throwable

### pageContext

pageContext对象是JSP技术中最重要的一个对象，它代表JSP页面的运行环境，这个对象不仅封装了对其它8大隐式对象的引用，它自身还是一个域对象(容器)，可以用来保存数据。并且，这个对象还封装了web开发中经常涉及到的一些常用操作，例如引入和跳转其它资源、检索其它域对象中的属性等。

- getException方法返回exception隐式对象
- getPage方法返回page隐式对象
- getRequest方法返回request隐式对象
- getResponse方法返回response隐式对象
- getServletConfig方法返回config隐式对象
- getServletContext方法返回application隐式对象
- getSession方法返回session隐式对象
- getOut方法返回out隐式对象

如果在编程过程中，把pageContext对象传递给一个普通java对象，那么这个java对象将可以获取8大隐式对象，此时这个java对象就可以和浏览器交互了，此时这个java对象就成为了一个动态web资源了，这就是pageContext封装其它8大内置对象的意义，把pageContext传递给谁，谁就能成为一个动态web资源，那么什么情况下需要把pageContext传递给另外一个java类呢，什么情况下需要使用这种技术呢，在比较正规的开发中，jsp页面是不允许出现java代码的，如果jsp页面出现了java代码，那么就应该想办法把java代码移除掉，我们可以开发一个自定义标签来移除jsp页面上的java代码，首先围绕自定义标签写一个java类，jsp引擎在执行自定义标签的时候就会调用围绕自定义标签写的那个java类，在调用java类的时候就会把pageContext对象传递给这个java类，由于pageContext对象封装了对其它8大隐式对象的引用，因此在这个java类中就可以使用jsp页面中的8大隐式对象(request，response，config，application，exception，Session，page，out)了，pageContext对象在jsp自定义标签开发中特别重要。

    public void setAttribute(java.lang.String name,java.lang.Object value)
    public java.lang.Object getAttribute(java.lang.String name)
    public void removeAttribute(java.lang.String name)
    public java.lang.Object findAttribute(java.lang.String name)

重点介绍一下findAttribute方法，这个方法是用来查找各个域中的属性的，findAttribute方法按照查找顺序"page→request→session→application"在这四个对象中去查找，只要找到了就返回属性值，如果四个对象都没有找到要查找的属性，则返回一个null。

PageContext类中定义了一个forward方法(用来跳转页面)和两个include方法(用来引入页面)来分别简化和替代RequestDispatcher.forward方法和include方法。
方法接收的资源如果以“/”开头， “/”代表当前web应用。

    pageContext.forward("/pageContextDemo05.jsp");
    
    pageContext.getRequest().getRequestDispatcher("/pageContextDemo05.jsp").forward(request, response);
    
    pageContext.include("/jspfragments/head.jsp");


## JSP标签

JSP标签也称之为Jsp Action(JSP动作)元素，它用于在Jsp页面中提供业务逻辑功能，避免在JSP页面中直接编写java代码，造成jsp页面难以维护。

jsp的常用标签有以下三个

- <jsp:include>标签  
- <jsp:forward>标签  
- <jsp:param>标签

### <jsp:include>标签

<jsp:include>标签用于把另外一个资源的输出内容插入进当前JSP页面的输出内容之中，这种在JSP页面执行时的引入方式称之为动态引入。

语法：

    <jsp:include page="relativeURL | <%=expression%>" flush="true|false" /> 

<jsp:include>标签是动态引入， <jsp:include>标签涉及到的2个JSP页面会被翻译成2个servlet，这2个servlet的内容在执行时进行合并。 

而include指令是静态引入，涉及到的2个JSP页面会被翻译成一个servlet，其内容是在源文件级别进行合并。

### <jsp:forward>标签

<jsp:forward>标签用于把请求转发给另外一个资源。

语法：

    <jsp:forward page="relativeURL | <%=expression%>" /> 
  
page属性用于指定请求转发到的资源的相对路径，它也可以通过执行一个表达式来获得。

### <jsp:param>标签

当使用<jsp:include>和<jsp:forward>标签引入或将请求转发给其它资源时，可以使用<jsp:param>标签向这个资源传递参数。

语法1：
　　
    <jsp:include page="relativeURL | <%=expression%>">
        <jsp:param name="parameterName" value="parameterValue|<%= expression %>" />
    </jsp:include>
    
语法2：
　　
    <jsp:forward page="relativeURL | <%=expression%>">
        <jsp:param name="parameterName" value="parameterValue|<%= expression %>" />
    </jsp:include>
    
<jsp:param>标签的name属性用于指定参数名，value属性用于指定参数值。在<jsp:include>和<jsp:forward>标签中可以使用多个<jsp:param>标签来传递多个参数。


### JSTL

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

从功能上可以分为4类：表达式控制标签、流程控制标签、循环标签、URL操作标签。

- 表达式控制标签：out标签、set标签、remove标签、catch标签。
- 流程控制标签：if标签、choose标签、when标签、otherwise标签。
- 循环标签：forEach标签、forTokens标签。
- URL操作标签：import标签、url标签、redirect标签、param标签。

#### out

    1 <c:out value=”字符串”>
    2 <c:out value=”EL表达式”>

    <c:out value=”要显示的数据对象” [escapeXml=”true|false”] [default=”默认值”]/>

#### set

    <c:set value=”值1” var=”name1” [scope=”page|request|session|application”]/>
    <c:set value=”值3” target=”JavaBean对象” property=”属性名”/>

#### remove

    <c:remove var=”变量名” [scope=”page|request|session|application”]/>

#### if


    <c:if test="testCondition" [var="varName"] [scope="{page|request|session|application}"]>
        　　　　　　标签体内容
    </c:if>

var属性用来存放判断的结果，类型为true或false。

#### choose

    　<c:choose>
         <c:when test="条件1">
    　　　　　　//业务逻辑1
         <c:when>
    　　　<c:when test="条件2">
    　　　　　　//业务逻辑2
         <c:when>
    　　　<c:when test="条件n">
    　　　　　　//业务逻辑n
         <c:when>
         <c:otherwise>
    　　　　　　//业务逻辑
    　　　</c:otherwise>
    　</c:choose>

#### forEach

    <c:forEach 
    　　var=”name” 
    　　items=”Collection” 
    　　varStatus=”StatusName” 
    　　begin=”begin” 
    　　end=”end” 
    　　step=”step”>
        本体内容
    </c:forEach>

- var设定变量名用于存储从集合中取出元素。
- items指定要遍历的集合。
- varStatus设定变量名，该变量用于存放集合中元素的信息。    
- begin、end用于指定遍历的起始位置和终止位置（可选）。
- step指定循环的步长。


### EL

式获取数据：

    ${标识符}

执行运算：

    ${运算表达式}

获得web开发常用对象：

    ${隐式对象名称}

# filter

Filter的创建和销毁由WEB服务器负责。 web 应用程序启动时，web 服务器将创建Filter 的实例对象，并调用其init方法，完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作，filter对象只会创建一次，init方法也只会执行一次。通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。

web服务器根据Filter在web.xml文件中的注册顺序，决定先调用哪个Filter，当第一个Filter的doFilter方法被调用时，web服务器会创建一个代表Filter链的FilterChain对象传递给该方法。在doFilter方法中，开发人员如果调用了FilterChain对象的doFilter方法，则web服务器会检查FilterChain对象中是否还有filter，如果有，则调用第2个filter，如果没有，则调用目标资源。

     public void doFilter(ServletRequest request, ServletResponse response,
                 FilterChain chain) throws IOException, ServletException {
             System.out.println("FilterDemo02执行前！！！");
             chain.doFilter(request, response);  //让目标资源执行，放行
             System.out.println("FilterDemo02执行后！！！");
         }

# listener

监听web应用程序中的ServletContext, HttpSession和 ServletRequest等域对象的创建与销毁事件，以及监听这些域对象中的属性发生修改的事件。


## 创建与销毁

ServletContextListener接口用于监听ServletContext对象的创建和销毁事件。

HttpSessionListener 接口用于监听HttpSession对象的创建和销毁

ServletRequestListener接口用于监听ServletRequest 对象的创建和销毁

## 变更属性

域对象中属性的变更的事件监听器

    ServletContextAttributeListener
    ServletRequestAttributeListener
    HttpSessionAttributeListener
    
    attributeAdded 
    attributeRemoved 
    attributeReplaced

## 感知Session

注意：下面的接口是添加到session中的属性对象要实现的。所以，实现这两个接口的类不需要 web.xml 文件中进行注册。

保存在Session域中的对象可以有多种状态：绑定(session.setAttribute("bean",Object))到Session中；从 Session域中解除(session.removeAttribute("bean"))绑定；随Session对象持久化到一个存储设备中；随Session对象从一个存储设备中恢复

Servlet 规范中定义了两个特殊的监听器接口"HttpSessionBindingListener和HttpSessionActivationListener"来帮助JavaBean 对象了解自己在Session域中的这些状态。

HttpSessionBindingListener

实现了HttpSessionBindingListener接口的JavaBean对象可以感知自己被绑定到Session中和 Session中删除的事件

HttpSessionActivationListener

实现了HttpSessionActivationListener接口的JavaBean对象可以感知自己被活化(反序列化)和钝化(序列化)的事件

为了观察绑定到HttpSession对象中的javabean对象随HttpSession对象一起被钝化到硬盘上和从硬盘上重新活化回到内存中的的过程，我们需要借助tomcat服务器帮助我们完成HttpSession对象的钝化和活化过程

在web\META-INF文件夹下创建一个context.xml文件

    <Context>
         <Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1">
         <Store className="org.apache.catalina.session.FileStore" directory="gacl"/>
         </Manager>
    </Context>

在tomcat服务器的work\Catalina\localhost\JavaWeb_Listener_20140908\gacl文件夹下找到序列化到本地存储的session。

# 编码问题

## 查看/修改浏览器编码

IE:左键菜单->编码

chrome:菜单->更多工具->编码

## request编码

问题：

浏览器以什么编码encode数据？

浏览器提交数据“中国”两字的编码：

GB2312：%D6%D0%B9%FA

UTF-8:%E4%B8%AD%E5%9B%BD

格式：解成byte[]，转为字符，前加%，拼串。

### POST方式

网页编码决定post内容的编码。

由于客户端是以UTF-8字符编码将表单数据传输到服务器端的，因此服务器也需要设置以UTF-8字符编码进行接收，要想完成此操作，服务器可以直接使用从ServletRequest接口继承而来的"setCharacterEncoding(charset)"方法进行统一的编码设置。

    request.setCharacterEncoding("UTF-8");
    String userName = request.getParameter("userName");

### GET方式

利用request.setCharacterEncoding("UTF-8");来设置Tomcat接收请求的编码格式，只对POST方式提交的数据有效，对GET方式提交的数据无效!

要设置GET的编码，可以修改server.xml文件中，相应的端口的Connector的属性：URIEncoding="UTF-8"，这样，GET方式提交的数据才会被正确解码。

？tomcat的connector会处理url，url在处理过程中进行了


对于以get方式传输的数据，默认的还是使用ISO8859-1这个字符编码来接收数据，客户端以UTF-8的编码传输数据到服务器端，而服务器端的request对象使用的是ISO8859-1这个字符编码来接收数据。

解决办法：在接收到数据后，先获取request对象以ISO8859-1字符编码接收到的原始数据的字节数组，然后通过字节数组以指定的编码构建字符串，解决乱码问题。

    String name = request.getParameter("name");
    name =new String(name.getBytes("ISO8859-1"), "UTF-8") ;

通过字节数组以指定的编码构建字符串，这里指定的编码是根据客户端那边提交数据时使用的字符编码来定的，如果是GB2312，那么就设置成data = new String(source, "GB2312")，如果是UTF-8，那么就设置成data = new String(source, "UTF-8")

    org.apache.catalina.core.ApplicationHttpRequest
    private void mergeParameters() {
    
            if ((queryParamString == null) || (queryParamString.length() < 1))
                return;
    
            HashMap queryParameters = new HashMap();
            String encoding = getCharacterEncoding();
            if (encoding == null)
                encoding = "ISO-8859-1";
            try {
                RequestUtil.parseParameters
                    (queryParameters, queryParamString, encoding);
            } catch (Exception e) {
                ;
            }
            Iterator keys = parameters.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = queryParameters.get(key);
                if (value == null) {
                    queryParameters.put(key, parameters.get(key));
                    continue;
                }
                queryParameters.put
                    (key, mergeValues(value, parameters.get(key)));
            }
            parameters = queryParameters;
    
        }


