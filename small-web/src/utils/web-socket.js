var ws = null;
let rec; //断线重连后，延迟5秒重新创建WebSocket连接  rec用来存储延迟请求的代码
let isConnect = false; //连接标识 避免重复连接
let connectCallBack = null;
let messageCallBack = null;
let closeCallBack = null;

let connect = (wsUrl, accessToken) => {
  try {
    if (isConnect) {
      return;
    }
    let token = accessToken.split(' ')[1];
    console.log('连接WebSocket', token);
    // 自定义子协议 Sec-Websocket-Protocol  目的：类似携带请求头进行认证，后端需原样返回
    // ws = new WebSocket(wsUrl, token);
    ws = new WebSocket(wsUrl);

    // 监听socket连接
    ws.onopen = function () {
      console.log('WebSocket连接成功');
      isConnect = true;
      // 发送登录命令
      let loginInfo = { cmd: 'LOGIN', data: { accessToken: accessToken } };
      ws.send(JSON.stringify(loginInfo));
    };

    // 监听socket消息
    ws.onmessage = function (e) {
      let sendInfo = JSON.parse(e.data);
      if (sendInfo.cmd == 'LOGIN') {
        heartCheck.start();
        // 登录成功才算真正完成连接
        connectCallBack && connectCallBack();
        console.log('WebSocket登录成功');
      } else if (sendInfo.cmd == 'HEART_BEAT') {
        // 重新开启心跳定时
        heartCheck.reset();
      } else {
        // 其他消息转发出去
        console.log('收到消息:', sendInfo);
        messageCallBack && messageCallBack(sendInfo.cmd, sendInfo.data);
      }
    };

    // 连接发生错误的回调方法
    ws.onerror = function () {
      console.log('WebSocket连接发生错误');
      isConnect = false; //连接断开修改标识
      reconnect(wsUrl, accessToken);
    };

    ws.onclose = function (e) {
      console.log('WebSocket连接关闭');
      isConnect = false; //断开后修改标识
      closeCallBack && closeCallBack(e);
    };
  } catch (e) {
    console.log('尝试创建连接失败');
    reconnect(wsUrl, accessToken); //如果无法连接上webSocket 那么重新连接！可能会因为服务器重新部署，或者短暂断网等导致无法创建连接
  }
};

//定义重连函数
let reconnect = (wsUrl, accessToken) => {
  if (isConnect) {
    //如果已经连上就不在重连了
    return;
  }
  console.log('尝试重新连接');
  rec && clearTimeout(rec);
  rec = setTimeout(function () {
    // 延迟15秒重连  避免过多次过频繁请求重连
    connect(wsUrl, accessToken);
  }, 15000);
};

//设置关闭连接
let close = (code) => {
  ws && ws.close(code);
};

//心跳设置
let heartCheck = {
  timeout: 5000, //每5s发送一次心跳包
  timeoutObj: null, //延时发送消息对象（启动心跳新建这个对象，收到消息后重置对象）
  start: function () {
    if (isConnect) {
      console.log('发送WebSocket心跳');
      ws.send(JSON.stringify({ cmd: 'HEART_BEAT', data: 'ping' }));
    }
  },

  reset: function () {
    clearTimeout(this.timeoutObj);
    this.timeoutObj = setTimeout(function () {
      heartCheck.start();
    }, this.timeout);
  },
};

// 实际调用的方法
let sendMessage = (agentData) => {
  if (ws.readyState === ws.OPEN) {
    // 若是ws开启状态
    ws.send(JSON.stringify(agentData));
  } else if (ws.readyState === ws.CONNECTING) {
    // 若是 正在开启状态，则等待1s后重新调用
    setTimeout(function () {
      sendMessage(agentData);
    }, 1000);
  } else {
    // 若未开启 ，则等待1s后重新调用
    setTimeout(function () {
      sendMessage(agentData);
    }, 1000);
  }
};

let onConnect = (callback) => {
  connectCallBack = callback;
};

let onMessage = (callback) => {
  messageCallBack = callback;
};

let onClose = (callback) => {
  closeCallBack = callback;
};
// 将方法暴露出去
export { connect, reconnect, close, sendMessage, onConnect, onMessage, onClose };
