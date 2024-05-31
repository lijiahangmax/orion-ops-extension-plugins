### 下载插件包

下载地址: http://116.62.194.246/lib/machine-monitor-agent-latest.jar  
备用地址: http://101.43.254.243/lib/machine-monitor-agent-latest.jar  
如果无法下载请联系我或 [手动构建](./quickstart-build)

### 修改插件配置

```
解压刚下载好的压缩包: 
修改 machine-monitor-agent-latest.jar/BOOT-INF/classes/application-prod.properties 后重新压缩

collect.period.second     是数据采集周期, 默认30s
agent.access.secret       是对外暴露服务的请求密钥, 建议修改 修改后需要与 orion-ops-api 配置中的 machine.monitor.default.access.token 保持一致
orion.ops.access.host     是 orion-ops 暴露服务的请求路径, 确保地址正确否则将无法进行报警通知  
orion.ops.access.secret   是 orion-ops 暴露服务的请求密钥, 必须和 orion-ops-api 配置中的 expose.api.access.secret 保持一致
```

### 复制插件包

将下载的包复制到部署 orion-ops 的机器中
如: `/root/lib/machine-monitor-agent-latest.jar`

### 修改 orion-ops 系统配置

访问 `orion-ops` > `系统管理` > `系统变量`  
修改环境变量 `machine_monitor_agent_path` 为 第三步操作的绝对路径

### 安装插件包

访问 `orion-ops` > `机器管理` > `机器监控`  
如果修改了配置 `agent.access.secret` 点击插件配置修改 `accessToken` 后点击 `安装` 即可 
