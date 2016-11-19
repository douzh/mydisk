# 概念

关键的概念是理解annotation是与一个程序元素相关联信息或者元数据的标注。它从不影响java程序的执行，但是对例如编译器警告或者像文档生成器等辅助工具产生影响。

## annotation类型

### marker annotation

一个没有成员定义的annotation类型被称为marker annotation。这种annotation类型仅使用自身的存在与否来为我们提供信息。如后面要说的Override。


### meta-annotation

meta -annotation也称为元annotation，它是被用来声明annotation类型的annotation。Java5.0提供了一些标准的元-annotation类型。下面介绍的target、retention就是meta-annotation。

### 内建annotation

@Deprecated用于修饰已经过时的方法；
@Override用于修饰此方法覆盖了父类的方法（而非重载）；
@SuppressWarnings用于通知java编译器禁止特定的编译警告。

## metadata

由于metadata被广泛使用于各种计算机开发过程中，所以当我们在这里谈论的metadata即元数据通常指被annotation装载的信息或者annotation本身。


# 声明

## mark annotation

    @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface Test { }

## enum成员

     public @interface Review {
          // 内嵌的枚举类型
          public static enum Grade { EXCELLENT, SATISFACTORY, UNSATISFACTORY };
      
          // 下面的方法定义了annotation的成员
          Grade grade();                
          String reviewer();          
          String comment() default "";  
      }

## 复杂成员

    @Retention(RetentionPolicy.RUNTIME)
      public @interface Reviews {
          Review[] value();
      }

## 类成员

    public @interface UncheckedExceptions {
          Class<? extends RuntimeException>[] value();
      }


## 默认值

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Column {
    	boolean isKey() default false;//用于判定是否为数据库中的主键列
    	boolean canNull() default true;//用于插入时判定not null列是否有值
    	String value() default "[ColumnName]";//数据库中对应的列名
    }

## Target

annotation 的target是一个被标注的程序元素。

target说明了annotation所修饰的对象范围：
packages
types（类、接口、枚举、annotation类型）
类型成员（方法、构造方法、成员变量、枚举值）
方法参数和本地变量（如循环变量、catch 参数）

在annotation类型的声明中使用了target可更加明晰其修饰的目标。

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface Target {
        ElementType[] value();
    }

### ElementType

    public enum ElementType {
        /## Class, interface (including annotation type), or enum declaration #/
        TYPE,
    
        /** Field declaration (includes enum constants) */
        FIELD,
    
        /## Method declaration #/
        METHOD,
    
        /** Parameter declaration */
        PARAMETER,
    
        /** Constructor declaration */
        CONSTRUCTOR,
    
        /** Local variable declaration */
        LOCAL_VARIABLE,
    
        /** Annotation type declaration */
        ANNOTATION_TYPE,
    
        /** Package declaration */
        PACKAGE
    }


## @Retention

定义annotation被保留的时间长短：
某些annotation仅出现在源代码中，而被编译器丢弃；
而另一些却被编译在 class文件中；
编译在class文件中的annotation可能会被虚拟机忽略，而另一些在class被装载时将被读取（请注意并不影响class 的执行，因为annotation与class在使用上是被分离的）。

使用这个meta-annotation可以对annotation的“生命周期” 限制。

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    public @interface Retention {
        RetentionPolicy value();
    }

### RetentionPolicy

    public enum RetentionPolicy {
        /**
         * 在编译时会删除.
         */
        SOURCE,
    
        /**
         *保留在类里，但vm不加载
         */
        CLASS,
    
        /**
         * 可以被反射获取
         */
        RUNTIME
    }


# 使用

所有可用注解的成员都要实现AnnotatedElement接口

## AnnotatedElement

成员是否含有指定注解

    boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);
    
    <T extends Annotation> T getAnnotation(Class<T> annotationClass);
    Annotation[] getAnnotations();
    Annotation[] getDeclaredAnnotations();

## 反射示例

    public JSONHolder(Field field) {
        // 根据注释获取属性对应的列名
        JSON json = field.getAnnotation(JSON.class);
        if (json != null) {
    	    if (!json.value().equals(JSON.defaultHttpParamName))
    		    httpParamName = json.value();
    	    else
    		    httpParamName = field.getName();
    	    isHttpParam = json.isHttpParam();
    	    isList = json.isList();
    	    if(isList)
    		    listClass=json.listClass();
        } else {
    	    // 没有注释则根据属性名小写化后的名字作列名
    	    httpParamName = field.getName();
        }
    }
