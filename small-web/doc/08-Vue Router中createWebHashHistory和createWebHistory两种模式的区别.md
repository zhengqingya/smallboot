# Vue Router 中 createWebHashHistory 和 createWebHistory 两种模式的区别

在 Vue Router 中，`createWebHashHistory` 和 `createWebHistory` 是两种不同的路由历史模式，它们的核心区别在于 **URL 的表现形式**和 **与服务器的交互方式**。以下是详细对比：

### 1. `createWebHashHistory`（哈希模式）

```
import { createRouter, createWebHashHistory } from 'vue-router';
const router = createRouter({
  history: createWebHashHistory(),
  routes: [...]
});
```

#### 特点：

- URL 格式：路由路径以 `#` 符号开头，例如：
  ```
  http://localhost:8080/#/home
  http://localhost:8080/#/about
  ```
- 原理：哈希模式 (`#` 后的部分) 的变化不会触发浏览器向服务器发送请求。
  前端通过监听 `hashchange` 事件实现路由切换。
- 兼容性：兼容所有浏览器（包括 IE9）。
- 服务器要求：无需服务器配置，适合静态托管（如 GitHub Pages）。

#### 适用场景：

- 部署在静态服务器（无后端支持）。
- 需要兼容旧版浏览器。
- 无法配置服务器重定向规则。

------

### 2. `createWebHistory`（HTML5 模式）

```
import { createRouter, createWebHistory } from 'vue-router';
const router = createRouter({
  history: createWebHistory(),
  routes: [...]
});
```

#### 特点：

- URL 格式：
  干净的 URL，例如：
  ```
  http://localhost:8080/home
  http://localhost:8080/about
  ```
- 原理：使用 HTML5 History API（`pushState` 和 `popstate`）实现路由切换。
- 兼容性：需要现代浏览器（IE10+ 或主流浏览器）。
- 服务器要求：需要服务器配置，将所有路径重定向到 `index.html`，否则直接访问子路由会返回 404。

#### 适用场景：

- 需要干净的 URL（对 SEO 友好）。
- 项目部署在支持 URL 重写的服务器（如 Nginx、Apache、Node.js）。
- 使用现代浏览器环境。

### 对比总结

| 特性               | `createWebHashHistory`（哈希模式）    | `createWebHistory`（HTML5 模式）    |
| :----------------- | :------------------------------------ | :---------------------------------- |
| URL 格式       | 包含 `#`（如 `/#/home`）              | 无 `#`（如 `/home`）                |
| 服务器要求     | 无需特殊配置                          | 需配置所有路径重定向到 `index.html` |
| 兼容性         | 兼容所有浏览器                        | 仅支持现代浏览器                    |
| SEO 友好性     | 较差（搜索引擎可能忽略 `#` 后的内容） | 较好                                |
| 直接访问子路由 | 支持（哈希部分由前端处理）            | 需服务器支持，否则返回 404          |

### 如何选择？

#### 选择 哈希模式 如果：

- 项目部署在静态服务器（如 GitHub Pages）。
- 需要兼容旧版浏览器（如 IE9）。
- 无法控制服务器配置。

#### 选择 HTML5 模式 如果：

- 需要干净的 URL（对 SEO 友好）。
- 项目部署在可控服务器（如自建 Nginx/Node.js）。
- 仅需支持现代浏览器。

### 服务器配置示例（HTML5 模式）

以 Nginx 为例，添加以下规则：


```nginx
# 静态资源代理 -- HTML5 模式 -- 解决前端配置 createWebHistory() 无法直接访问子路由问题
location / {
  try_files $uri $uri/ /index.html;
}
```

以 Node.js（Express）为例：

```
app.get('*', (req, res) => {
  res.sendFile(path.resolve(__dirname, 'dist', 'index.html'));
});
```

------

### 总结

- 哈希模式简单易用，适合快速开发和静态托管。
- HTML5 模式更专业，适合生产环境（需配合服务器配置）。
  根据实际需求选择即可！