package org.github.azirzsk.fcp.invoke;

import org.github.azirzsk.fcp.FCP;
import org.github.azirzsk.fcp.testclass.invoke.RepeatMethod;
import org.github.azirzsk.fcp.testclass.invoke.SingleNoArgMethod;
import org.github.azirzsk.fcp.testclass.invoke.SingleNoReturnMethod;
import org.github.azirzsk.fcp.testclass.invoke.SingleReturnMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public class FunctionCallTest {

    @Test
    public void testInvoke() {
        SingleReturnMethod toolTestClass = new SingleReturnMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(toolTestClass);

        String invokeStr1 = "{\"a\":25,\"b\":230}";
        Assertions.assertEquals(5, fcp.functionCall("gcd", invokeStr1));
        String invokeStr2 = "{\"b\":25,\"a\":230}";
        Assertions.assertEquals(5, fcp.functionCall("gcd", invokeStr2));
    }

    @Test
    public void testInvokeException() {
        SingleReturnMethod toolTestClass = new SingleReturnMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(toolTestClass);
        String invokeStr = "{\"b\":25,\"b\":230}";
        Assertions.assertThrows(NullPointerException.class, () -> fcp.functionCall("gcd", invokeStr), "没有找到对应的方法：gcd.a");
    }

    @Test
    public void testVoidReturn() {
        SingleNoReturnMethod singleNoReturnMethod = new SingleNoReturnMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(singleNoReturnMethod);

        String invokeStr = "{\"message\":\"hello world\"}";
        Assertions.assertNull(fcp.functionCall("printArg", invokeStr));
    }

    @Test
    public void testNoArg() {
        SingleNoArgMethod singleNoArgMethod = new SingleNoArgMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(singleNoArgMethod);

        String invokeStr = "{}";
        Assertions.assertEquals(123, fcp.functionCall("noArg", invokeStr));
    }

    @Test
    public void testCustomObject() {
        SingleReturnMethod singleReturnMethod = new SingleReturnMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(singleReturnMethod);

        String invokeStr = "{\"user\":{\"name\":\"azirzsk\",\"age\":25}}";
        Assertions.assertEquals("姓名：azirzsk，年龄：25", fcp.functionCall("print", invokeStr));
    }

    @Test
    public void testNestCustomObject() {
        SingleReturnMethod singleReturnMethod = new SingleReturnMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(singleReturnMethod);

        String invokeStr = "{\"home\":{\"address\":\"北京市朝阳区\",\"userInfo\":{\"name\":\"azirzsk\",\"age\":25}}}";
        Assertions.assertEquals("地址：北京市朝阳区，用户信息：姓名：azirzsk，年龄：25", fcp.functionCall("printHome", invokeStr));
    }

    @Test
    public void testRepeatMethod() {
        RepeatMethod repeatMethod = new RepeatMethod();
        FCP fcp = FCP.create();
        fcp.functionCall(repeatMethod);

        String invokeStr = "{\"str\":\"hello world\",\"repeat\":3}";
        Assertions.assertEquals("hello worldhello worldhello world", fcp.functionCall("print(String, int)", invokeStr));

    }
}
