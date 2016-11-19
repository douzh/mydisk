# Class对象

众所周知Java有个Object class，是所有Java classes的继承根源，其内声明了数个应该在所有Java class中被改写的methods：hashCode()、equals()、clone()、toString()、getClass()等。其中getClass()返回一个Class object。

Class class十分特殊，它和一般classes一样继承自Object，其实体用以表达Java程序运行时的classes和interfaces，也用来表达enum、array、primitive Java types（boolean, byte, char, short, int, long, float, double）以及关键词void。

当一个class被加载，或当加载器（class loader）的defineClass()被JVM调用，JVM 便自动产生一个Class object。

## 获取Class

    Class c = 对象名.getClass();
    Class c = java.awt.Button.class; 
    Class c = Integer.TYPE;
    Class c = Class.forName(xxx);

# Method

Class对象：
没有Declared返回方法包括父类中的方法

    public Method[] getDeclaredMethods() 
    public Method getDeclaredMethod(String name, Class<?>... parameterTypes)

Method对象：

    public Class<?>[] getParameterTypes()
    public Class<?> getReturnType()

## 获取指定方法

    Class partypes[] = new Class[2];
    partypes[0] = Integer.TYPE;
    partypes[1] = Integer.TYPE;
    Method meth = cls.getMethod("add", partypes);

## 执行指定方法

    Object arglist[] = new Object[2];
    arglist[0] = new Integer(37);
    arglist[1] = new Integer(47);
    Object retobj = meth.invoke(obj, arglist);

# Constructor 

Class方法：

    public Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)
    public Constructor<?>[] getDeclaredConstructors()

构造器方法：

    public T newInstance(Object ... initargs)

## 获取指定

    Class partypes[] = new Class[2];
    partypes[0] = Integer.TYPE;
    partypes[1] = Integer.TYPE;
    Constructor ct = cls.getConstructor(partypes);

## 执行指定

    Object arglist[] = new Object[2];
    arglist[0] = new Integer(37);
    arglist[1] = new Integer(47);
    Object retobj = ct.newInstance(arglist);

# Field 

## 获取

    Field fieldlist[] = cls.getDeclaredFields();
    Field fld = cls.getField("fieldname");

## 直接获取/设置属性值


Test类有一属性：

    public double d;
    Field fld = cls.getField("d");
    //fld.isAccessible();//非public属性要加
    Test obj = new Test();
    System.out.println("d = " + fld.getDouble(obj));
    fld.setDouble(obj, 12.34);
    System.out.println("d = " +fld.getDouble(obj));

# java.lang.reflect.Array

    Class cls = Class.forName("java.lang.String");
    Object arr = Array.newInstance(cls, 10);
    Array.set(arr, 5, "this is a test");
    String s = (String) Array.get(arr, 5);
    System.out.println(s);
