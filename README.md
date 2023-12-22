# Fundamentals_Of_Compiling_Project

本项目完成了编译原理期末作业的Task2.

## 文件结构

在本目录下有一个名为`antlr-4.13.0-complete.jar`的文件，是ANTLR必要的组件。

`./Task2`文件夹下包含了任务2的实现代码。

`./Compiler`文件夹含有任务1的部分代码（词法分析器相关），与任务2无关，但可能会有一些帮助，仅供参考。

## 功能实现

`./Task2/gen/PL0`目录下的文件包含了词法分析器`Lexer`、语法分析器`Parser`以及用于遍历的基础类`Visitor`和`Listener`。这些文件均是由`./Task2/src/PL0.g4`文件生成的，`PL0.g4`文件包含了PL/0语言的词法和语法规则。这一部分主要是实现词法分析和语法分析。

为实现中间代码生成，需要改写`Visitor`类，通过继承的方式重写了类`PL0CodeGeneratorVisitor`，其中重写了访问各类结构的方法，最后输出的结果是三地址的中间代码。

