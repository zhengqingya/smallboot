# 说明

### 组件&服务 部署

mysql、redis、minio

见[部署文档](doc/部署)

### 本地运行

1. 修改`app/src/main/resources/application-dev.yml`配置
2. 启动`App`

---

### 数据清理

```sql
SELECT CONCAT('DELETE FROM ', TABLE_NAME, ' where is_deleted=1;')
FROM information_schema.TABLES
WHERE table_schema = 'smallboot';
```