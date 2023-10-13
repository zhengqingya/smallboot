# 说明

### 组件&服务 部署

mysql、redis、minio

见[部署文档](doc/部署)

### 本地运行

1. 修改`app/src/main/resources/application-dev.yml`配置
2. 启动`App`

---

### 数据清理

#### 清理所有逻辑删除的数据

```sql
SELECT CONCAT('DELETE FROM ', t.TABLE_NAME, ' where is_deleted=1;')
FROM information_schema.TABLES t
         left join information_schema.COLUMNS c on t.TABLE_NAME = c.TABLE_NAME
WHERE t.table_schema = 'smallboot'
  and c.COLUMN_NAME = 'is_deleted';
```

#### 清理指定租户下的所有数据 （谨慎使用！！！）

```sql
SELECT CONCAT('DELETE FROM ', t.TABLE_NAME, ' where tenant_id in (xxx);')
FROM information_schema.TABLES t
         left join information_schema.COLUMNS c on t.TABLE_NAME = c.TABLE_NAME
WHERE t.table_schema = 'smallboot'
  and c.COLUMN_NAME = 'tenant_id';
```