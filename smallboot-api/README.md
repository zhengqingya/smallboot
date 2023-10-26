# 说明

### 组件&服务 部署

mysql、redis、minio

见[部署文档](doc/部署)

### 本地运行

1. 修改`app/src/main/resources/application-dev.yml`配置
2. 启动`App`

---

### 数据处理

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
-- 逻辑删除
select CONCAT('update ', r.TABLE_NAME, ' set is_deleted = 1 where tenant_id in ( 租户id );')
from (SELECT re.*, count(1) count
      from (
          SELECT t.TABLE_NAME
          FROM information_schema.TABLES t
          join information_schema.COLUMNS c on (t.TABLE_NAME = c.TABLE_NAME and c.COLUMN_NAME in ( 'is_deleted', 'tenant_id' ) )
          WHERE t.table_schema = 'smallboot'
          GROUP BY t.TABLE_NAME, c.COLUMN_NAME
          ) re
      GROUP BY re.TABLE_NAME) r
-- 说明删除和租户2个字段都存在
where r.count > 1
-- 租户表数据
update t_sys_tenant
set is_deleted = 1
where id in (租户id);


-- 真正删除
SELECT CONCAT('DELETE FROM ', t.TABLE_NAME, ' where tenant_id in (xxx);')
FROM information_schema.TABLES t
         left join information_schema.COLUMNS c on t.TABLE_NAME = c.TABLE_NAME
WHERE t.table_schema = 'smallboot'
  and c.COLUMN_NAME = 'tenant_id';
```

#### 租户id统一变更 （谨慎使用！！！）

```sql
SELECT CONCAT('update ', t.TABLE_NAME, ' set tenant_id = 新租户id where tenant_id = 旧租户id;')
FROM information_schema.TABLES t
         left join information_schema.COLUMNS c on t.TABLE_NAME = c.TABLE_NAME
WHERE t.table_schema = 'smallboot'
  and c.COLUMN_NAME = 'tenant_id';
```

#### 删除重复数据 -- 保留唯一一条

```
DELETE FROM t_sys_user_role 
WHERE id NOT IN (
    SELECT t.id FROM (
        SELECT MIN( id ) AS id 
        FROM t_sys_user_role 
				WHERE tenant_id = 1
        GROUP BY user_id,role_id
    ) t     
)
AND tenant_id = 1;

DELETE FROM t_sys_role_menu 
WHERE id NOT IN (
    SELECT t.id FROM (
        SELECT MIN( id ) AS id 
        FROM t_sys_role_menu 
				WHERE tenant_id = 1
        GROUP BY menu_id,role_id
    ) t     
)
AND tenant_id = 1;
```