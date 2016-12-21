import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class Test2 {
    class father{
        int a=5;

        public father(int a) {
            this.a = a;
        }

        public void sss(){
            System.out.println(this.getClass().getAnnotation(MyClassAnnotation.class).id());
        }
        public void sss(String a){
            System.out.println(this.getClass().getAnnotation(MyClassAnnotation.class).id());
        }
    }
    @MyClassAnnotation(id = "asd")
    class child extends father{
        int a=4;
        public child() {
            super(6);
        }
    }
    @Test
    public void sd(){
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public   @interface  MyClassAnnotation
    {
        String id();
    }
}
