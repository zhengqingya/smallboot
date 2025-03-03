# Netty 学习

参考

- https://gitee.com/bluexsx/box-im

### websocket在线测试

http://docs.wildfirechat.cn/web/wstool/index.html

连接地址

```
ws://127.0.0.1:10081/im
```

#### 登录

```json
{
  "cmd": "LOGIN",
  "data": {
    "accessToken": "xxx"
  }
}
```

#### 心跳

```json
{
  "cmd": "HEART_BEAT",
  "data": "ping"
}
```

#### 私聊

```json
{
  "cmd": "PRIVATE_MESSAGE",
  "data": {
    "sender": {
      "userId": 2,
      "terminal": 0
    },
    "receiverList": [
      {
        "userId": 1,
        "terminal": 0
      }
    ],
    "isCallbackResult": true,
    "serviceName": "im-client-A",
    "data": {
      "sendId": 2,
      "recvId": 1,
      "content": "hello"
    }
  }
}
```

#### 群聊

```json
{
  "cmd": "GROUP_MESSAGE",
  "data": {
    "sender": {
      "userId": 2,
      "terminal": 0
    },
    "receiverList": [
      {
        "userId": 1,
        "terminal": 0
      }
    ],
    "isCallbackResult": true,
    "serviceName": "im-client-A",
    "data": {
      "sendId": 2,
      "recvId": 1,
      "content": "hello"
    }
  }
}
```