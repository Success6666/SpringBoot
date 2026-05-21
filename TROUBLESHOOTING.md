# 依赖爆红与启动失败排查说明

## 本次问题的原因

- 项目使用的是 Spring Boot 3.x，但 MyBatis 的 starter 版本引入了 Spring Boot 4.x 相关组件。
- 这样会在类路径里同时出现 Spring Boot 3.x 和 4.x 的类，导致启动时 Bean 重复定义。
- 报错点是：`hikariPoolDataSourceMetadataProvider` 被重复注册。

## 为什么 IDE 里注解/导入爆红

- 当 Maven 依赖没有正确解析或版本冲突时，IDE 无法构建完整的类路径。
- 常见原因：
  - 父 POM 或依赖版本在仓库里不存在。
  - 本地 Maven 缓存被锁定或损坏（`.m2` 访问被拒绝）。
  - 传递依赖拉进了不同大版本的组件。

## 这次的修复方式

- 使用 Spring Boot 3.x 作为父版本。
- 将 MyBatis 相关 starter 对齐到 Spring Boot 3.x 适配版本：
  - `org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4`
  - `org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4`
- 重新构建刷新依赖。

## 以后再遇到“爆红”的处理步骤

1. 在 IDE 中重新导入 Maven 项目。
2. 执行一次 Maven 构建，确认具体依赖错误。
3. 检查是否混用了不同大版本的 Spring Boot 组件。
4. 必要时清理本地缓存：删除对应 `.m2` 目录后再构建。

## 常用命令（PowerShell）

```powershell
Push-Location "E:\springboot1"
.\mvnw.cmd -q -DskipTests package
Pop-Location
```

```powershell
Get-ChildItem "C:\Users\L\.m2\repository" -Recurse -Filter "spring-boot-*.jar" | Select-Object -First 10
```
