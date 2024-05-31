### 所需环境

* jdk 1.8
* maven 3.5.4(+)

### 拉取代码

```shell
# github
git clone https://github.com/lijiahangmax/orion-ops-extension-plugins
# gitee
git clone https://gitee.com/lijiahangmax/orion-ops-extension-plugins
```

### 修改配置

```
配置文件路径: 
orion-ops-extension-plugins/orion-ops-plugins-parent/machine-monitor-agent/src/main/resources/application-prod.properties

collect.period.second     是数据采集周期, 默认30s, 建议不动
agent.access.secret       是对外暴露服务的请求密钥, 建议修改 修改后需要与 orion-ops-api 配置中的 machine.monitor.default.access.token 保持一致
orion.ops.access.host     是 orion-ops 暴露服务的请求路径, 确保地址正确否则将无法进行报警通知  
orion.ops.access.secret   是 orion-ops 暴露服务的请求密钥, 必须和 orion-ops-api 配置中的 expose.api.access.secret 保持一致
```  

### 构建 jar 包

```shell
# 进入代码目录
cd orion-ops-extension-plugins/orion-ops-plugins-parent 
# 运行
maven -U clean install -DskipTests
```   

### 配置插件包

1. 将构建的包复制到部署 orion-ops 的机器中 将 `machine-monitor-agent/target/machine-monitor-agent.jar`
   复制到部署 `orion-ops` 的机器中 如: `/root/lib/machine-monitor-agent-latest.jar`

2. 修改 orion-ops 系统配置  
   访问 `orion-ops` > `系统管理` > `系统变量`  
   修改环境变量 `machine_monitor_agent_path` 为 第一步操作的绝对路径

### 安装插件包

访问 `orion-ops` > `机器管理` > `机器监控`  
如果修改了配置 `agent.access.secret` 点击插件配置修改 `accessToken` 后点击 `安装` 即可
