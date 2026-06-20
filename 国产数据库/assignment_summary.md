# 国产数据库（金仓数据库）作业完成说明

## 作业要求对应

任务要求：基于国产数据库（金仓数据库）开发项目，能够在金仓数据库上建 3 个以上数据表，并在 Web 端或移动终端至少完成一个与所选项目相关的业务功能，包含增删改查。

本项目完成情况：

1. 项目名称：教学设备资产管理系统。
2. 数据库：KingbaseES（金仓数据库）。
3. 数据表数量：4 张，分别为 `kb_department`、`kb_asset_category`、`kb_supplier`、`kb_asset`。
4. Web 业务功能：资产台账管理。
5. CRUD 覆盖情况：
   - 新增：`/assets/new` 页面录入资产。
   - 查询：`/assets` 页面按关键字、分类、状态查询。
   - 修改：资产列表中点击“修改”进入编辑页面。
   - 删除：资产列表中点击“删除”移除记录。

## Scoop 是否能安装金仓数据库

本机已执行：

```powershell
scoop search kingbase
```

结果为 `No matches found`。因此，当前常用 Scoop bucket 中没有 KingbaseES/金仓数据库安装包，不能直接通过 `scoop install kingbase` 安装。

建议方式：

1. 从金仓数据库官方渠道下载 KingbaseES Windows 安装包。
2. 安装后创建数据库 `course_assets`。
3. 启动本项目，项目会通过 KingbaseES JDBC 驱动连接数据库并初始化表结构。

## 项目运行

```powershell
mvn spring-boot:run
```

访问：

```text
http://localhost:8080/assets
```

如数据库连接信息不同，可修改 `src/main/resources/application.yml`，或通过环境变量指定：

```powershell
$env:KINGBASE_URL="jdbc:kingbase8://localhost:54321/course_assets"
$env:KINGBASE_USERNAME="system"
$env:KINGBASE_PASSWORD="manager"
mvn spring-boot:run
```
