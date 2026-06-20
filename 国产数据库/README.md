# 金仓数据库开发与应用作业

## 1. Scoop 能安装金仓数据库吗？

结论：当前常用 Scoop bucket 里没有 KingbaseES/金仓数据库安装包。已在本机执行：

```powershell
scoop search kingbase
```

结果为 `No matches found`，因此不能像 `scoop install mysql` 那样直接安装金仓数据库。金仓数据库建议从金仓官方 Download Center 下载 Windows 安装包、驱动或 Docker 镜像；Java 项目中的 JDBC 驱动可以通过 Maven Central 引入 `cn.com.kingbase:kingbase8`。

## 2. 作业完成情况

本项目基于国产数据库金仓 KingbaseES 设计了一个“教学设备资产管理系统”，满足作业要求：

- 建立 4 张数据表：部门表、设备分类表、供应商表、资产表。
- 使用 KingbaseES JDBC 驱动连接金仓数据库。
- 提供 Web 端页面完成资产的新增、查询、修改、删除。
- 使用 Spring Boot + JdbcTemplate + Thymeleaf 实现，结构清晰，便于运行和截图提交。

## 3. 表结构说明

| 表名 | 含义 |
| --- | --- |
| `kb_department` | 使用部门表，记录设备归属部门 |
| `kb_asset_category` | 设备分类表，记录电脑、网络设备、实验设备等分类 |
| `kb_supplier` | 供应商表，记录供货单位和联系方式 |
| `kb_asset` | 资产表，记录设备编号、名称、分类、部门、供应商、状态等业务数据 |

主业务功能围绕 `kb_asset` 表完成，资产表通过外键关联部门、分类和供应商，能够体现真实业务中的多表关系。

## 4. 运行准备

需要先安装并启动 KingbaseES，创建数据库：

```sql
CREATE DATABASE course_assets;
```

默认连接配置位于 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    driver-class-name: com.kingbase8.Driver
    url: jdbc:kingbase8://localhost:54321/course_assets
    username: system
    password: manager
```

如果你的金仓数据库端口、用户名或密码不同，可以用环境变量覆盖：

```powershell
$env:KINGBASE_URL="jdbc:kingbase8://localhost:54321/course_assets"
$env:KINGBASE_USERNAME="system"
$env:KINGBASE_PASSWORD="manager"
mvn spring-boot:run
```

应用启动时会自动执行 `schema.sql` 和 `data.sql`，创建 4 张表并写入演示数据。

## 5. 启动项目

```powershell
mvn spring-boot:run
```

启动成功后访问：

```text
http://localhost:8080/assets
```

可在页面中完成：

1. 查询资产列表。
2. 按关键字、分类和状态筛选资产。
3. 新增资产。
4. 修改资产。
5. 删除资产。

## 6. 截图建议

提交作业时建议截图以下页面：

1. KingbaseES 中 4 张表创建成功的截图。
2. Web 端资产列表页面。
3. 新增资产页面。
4. 修改资产页面。
5. 删除后列表变化页面。

这些截图能够覆盖“3 个以上数据表”和“Web 端增删改查业务功能”两个评分点。
