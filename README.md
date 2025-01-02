# 🛠️ Function Call Processor (FCP)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.azirzsk/function-call-processor)](https://search.maven.org/artifact/io.github.azirzsk/function-call-processor)
[![License](https://img.shields.io/github/license/azirzsk/FunctionCallProcessor)](https://github.com/azirzsk/FunctionCallProcessor/blob/master/LICENSE)
[![Java Version](https://img.shields.io/badge/Java-11%2B-blue)](https://www.java.com)
[![GitHub stars](https://img.shields.io/github/stars/azirzsk/FunctionCallProcessor)](https://github.com/azirzsk/FunctionCallProcessor/stargazers)
[![GitHub issues](https://img.shields.io/github/issues/azirzsk/FunctionCallProcessor)](https://github.com/azirzsk/FunctionCallProcessor/issues)

FCP是一个基于注解的Java工具库，只需使用`@Function`和`@Property`注解，即可轻松将Java方法转换为大语言模型可识别的函数调用格式，并支持自动解析大语言模型返回的函数调用结果执行对应方法。

## ✨ 功能特性

- 通过注解将Java方法解析为大语言模型的函数调用格式
- 自动执行大语言模型返回的函数调用

## 🔧 环境要求

- JDK 11 或更高版本

## 📦 引入依赖

### Maven
```xml
<dependency>
    <groupId>io.github.azirzsk</groupId>
    <artifactId>function-call-processor</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
implementation 'io.github.azirzsk:function-call-processor:1.0.0'
```

## 🚀 快速开始

### 📤 1. 解析对象方法为FunctionCall

将Java对象中的方法解析为大语言模型可识别的FunctionCall格式：

```java
public class Calculator {
    @Function(name = "add", 
              description = "将两个数字相加")
    public int add(@Property(description = "第一个数字") int a,
                  @Property(description = "第二个数字") int b) {
        return a + b;
    }

    @Function(name = "multiply", 
              description = "将两个数字相乘")
    public int multiply(@Property(description = "第一个数字") int x,
                       @Property(description = "第二个数字") int y) {
        return x * y;
    }
}
```

然后使用FCP解析这个类：

```java
// 创建FCP实例
FCP fcp = FCP.create();

// 传入需要解析的对象
Calculator calculator = new Calculator();
String functionCallJson = fcp.parse(calculator);
```

<details>
<summary>解析后得到的JSON格式如下：</summary>

```json
[
    {
        "name": "add",
        "description": "将两个数字相加",
        "parameters": {
            "type": "object",
            "properties": {
                "a": {
                    "type": "integer",
                    "description": "第一个数字"
                },
                "b": {
                    "type": "integer",
                    "description": "第二个数字"
                }
            },
            "required": ["a", "b"]
        }
    },
    {
        "name": "multiply",
        "description": "将两个数字相乘",
        "parameters": {
            "type": "object",
            "properties": {
                "x": {
                    "type": "integer",
                    "description": "第一个数字"
                },
                "y": {
                    "type": "integer",
                    "description": "第二个数字"
                }
            },
            "required": ["x", "y"]
        }
    }
]
```

</details>

### 📥 2. 执行FunctionCall回调

接收大语言模型返回的FunctionCall并执行对应的方法。

```java
// 创建FCP实例（如果已创建可以复用）
FCP fcp = FCP.create();

// 传入需要解析的对象（如果已传入可以复用）
Calculator calculator = new Calculator();
fcp.parse(calculator);

// 大语言模型返回的tool_calls格式
String llmResponse = """
{
    "tool_calls": [
        {
            "id": "call_123456",
            "type": "function",
            "function": {
                "name": "add",
                "arguments": "{\\"a\\": 1, \\"b\\": 2}"
            }
        }
    ]
}
""";

// 从大语言模型响应中提取函数名和参数
String functionName = "add";  // 从llmResponse中解析function.name
String argumentsJson = "{\"a\": 1, \"b\": 2}";  // 从llmResponse中解析function.arguments

// 执行函数调用
Object result = fcp.functionCall(functionName, argumentsJson);
System.out.println(result); // 输出: 3
```
