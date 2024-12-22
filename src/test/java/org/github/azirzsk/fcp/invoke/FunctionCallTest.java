package org.github.azirzsk.fcp.invoke;

import org.github.azirzsk.fcp.FCP;
import org.github.azirzsk.fcp.testclass.tool.SingleNoArgMethod;
import org.github.azirzsk.fcp.testclass.tool.SingleNoReturnMethod;
import org.github.azirzsk.fcp.testclass.tool.SingleReturnMethod;
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
}
