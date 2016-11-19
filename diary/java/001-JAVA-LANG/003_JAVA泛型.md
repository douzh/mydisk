# 泛型类

一个

    public class GenericFoo<T> {
    	private T foo;
    	public void setFoo(T foo) {
    		this.foo = foo;
    	}
    	public T getFoo() {
    		return foo;
    	}
    }

多个

    public class GenericFoo<T1, T2> {
    	private T1 foo1;
    	private T2 foo2;
    	public void setFoo1(T1 foo1) {
    		this.foo1 = foo1;
    	}
    	public T1 getFoo1() {
    		return foo1;
    	}
    	public void setFoo2(T2 foo2) {
    		this.foo2 = foo2;
    	}
    	public T2 getFoo2() {
    		return foo2;
    	}
    }

数组

    public class GenericFoo<T> {
    	private T[] fooArray;
    	public void setFooArray(T[] fooArray) {
    		this.fooArray = fooArray;
    	}
    	public T[] getFooArray() {
    		return fooArray;
    	}
    }

    String[] strs = {"caterpillar", "momor", "bush"};
    GenericFoo<String> foo = new GenericFoo<String>();
    foo.setFooArray(strs);
    strs = foo.getFooArray();

泛型类中的泛型类

    public class WrapperFoo<T> {
    	private GenericFoo<T> foo;
    	public void setFoo(GenericFoo<T> foo) {
    		this.foo = foo;
    	}
    	public GenericFoo<T> getFoo() {
    		return foo;
    	}
    }

    GenericFoo<Integer> foo = new GenericFoo<Integer>();
    foo.setFoo(new Integer(10));
    
    WrapperFoo<Integer> wrapper = new WrapperFoo<Integer>();
    wrapper.setFoo(foo);

# 泛型方法

    public <T> void f(T x) {
    	System.out.println(x.getClass().getName());
    }

# 通配符泛型


    <? extends Collection>  

1、如果只指定了<?>，而没有extends，则默认是允许Object及其下的任何Java类了。也就是任意类。

2、通配符泛型不单可以向下限制，如<? extends Collection>，还可以向上限制，如<? super Double>，表示类型只能接受Double及其上层父类类型，如Number、Object类型的实例。

3、泛型类定义可以有多个泛型参数，中间用逗号隔开，还可以定义泛型接口，泛型方法。这些都泛型类中泛型的使用规则类似。
