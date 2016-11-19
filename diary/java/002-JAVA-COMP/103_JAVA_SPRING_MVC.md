# 配制

## web.xml

    <!-- Spring MVC配置 -->
    <servlet>
        <servlet-name>spring</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 可以自定义servlet.xml配置文件的位置和名称，默认为WEB-INF目录下，名称为[<servlet-name>]-servlet.xml，如spring-servlet.xml
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring-servlet.xml</param-value>
        </init-param>
        -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>spring</servlet-name>
        <url-pattern>#.do</url-pattern>
    </servlet-mapping>
      
    
    <!-- Spring配置 -->
    <!-- ====================================== -->
    <listener>
       <listenerclass>
         org.springframework.web.context.ContextLoaderListener
       </listener-class>
    </listener>
      
    
    <!-- 指定Spring Bean的配置文件所在目录。默认配置在WEB-INF目录下 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:config/applicationContext.xml</param-value>
    </context-param>

## spring-servlet.xml

    <!-- 启用spring mvc 注解 -->
    <context:annotation-config />
    
    <!-- 设置使用注解的类所在的jar包 -->
    <context:component-scan base-package="controller"></context:component-scan>
    
    <!-- 完成请求和注解POJO的映射 -->
    <bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter" />
    　　
    <!-- 对转向页面的路径解析。prefix：前缀， suffix：后缀 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" p:prefix="/jsp/" p:suffix=".jsp" />

# 原理

## ViewResovler

Controller只负责传回来一个值，然后到底返回的是什么视图，是由视图解析器控制的，在jsp中常用的视图解析器是InternalResourceViewResovler，它会要求一个前缀和一个后缀

## ModelAndView

重定向

    return new ModelAndView(new RedirectView("../index.jsp")); 
    return new ModelAndView("redirect:../index.jsp");

## @Controller



## @RequestMapping

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface RequestMapping {
    	RequestMethod[] method() default {};
    	String[] params() default {};
    	String[] value() default {};
    }

类加方法组合方式

- 类：@RequestMapping("/test/#") 
- 方法：@RequestMapping("login.do")

- 类：@RequestMapping("/test/login.do")
- 方法：@RequestMapping(params = "method=1", method=RequestMethod.POST)
- 方法：@RequestMapping(params = "method=2")

# 参数绑定

## 无注释情况

在不给定注解的情况下，参数是怎样绑定的？

通过分析AnnotationMethodHandlerAdapter和RequestMappingHandlerAdapter的源代码发现，方法的参数在不给定参数的情况下：

若要绑定的对象时简单类型：  调用@RequestParam来处理的。  

若要绑定的对象时复杂类型：  调用@ModelAttribute来处理的。

## @RequestParam

常用来处理简单类型的绑定，通过Request.getParameter() 获取的String可直接转换为简单类型的情况

    @RequestParam("petId") int petId

## @RequestBody

该注解常用来处理Content-Type: 不是application/x-www-form-urlencoded编码的内容，例如application/json, application/xml等；

它是通过使用HandlerAdapter 配置的HttpMessageConverters来解析post data body，然后绑定到相应的bean上的。

因为配置有FormHttpMessageConverter，所以也可以用来处理 application/x-www-form-urlencoded的内容，处理完的结果放在一个MultiValueMap<String, String>里，这种情况在某些特殊需求下使用，详情查看FormHttpMessageConverter api;

    @RequestBody String body


## @PathVariable

处理requet uri 部分（这里指uri template中variable，不含queryString部分）

    @Controller
    @RequestMapping("/owners/{ownerId}")
    public class RelativePathUriTemplateController {
    
      @RequestMapping("/pets/{petId}")
      public void findPet(@PathVariable String ownerId, @PathVariable String petId, Model model) {    
        // implementation omitted
      }
    }

## @RequestHeader

    @RequestHeader("Accept-Encoding") String encoding

## @CookieValue

    @CookieValue("JSESSIONID") String cookie

## @SessionAttributes

## @ModelAttribute

该注解有两个用法，一个是用于方法上，一个是用于参数上；
