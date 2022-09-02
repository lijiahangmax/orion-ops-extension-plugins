> ##### 1. orion-ops 执行安装操作时失败怎么办?

手动启动:

```
$HOME/orion-ops/plugins/start-machine-monitor-agent.sh
```

如果启动脚本不存在则需要手动安装步骤:

1. 在机器列表中找到失败的机器 点击详情 记录机器id
2. 将 orion-ops 部署机器下的 agent 包复制到机器上
3. 执行启动操作

```
machineId=刚刚记录的机器id
agentJarPath=agent文件路径
pluginPath=$HOME/orion-ops/plugins
killTag=machine-monitor-agent
logPath=$pluginPath/machine-monitor-agent.out
scriptPath=$pluginPath/start-machine-monitor-agent.sh
mkdir -p ${pluginPath}
echo "ps -ef | grep ${killTag} | grep -v grep | awk '{print \$2}' | xargs kill -9 || echo \$?
nohup java -jar ${agentJarPath} --machineId=${machineId} --spring.profiles.active=prod >> ${logPath} &" > ${scriptPath}
chmod 777 ${scriptPath}
${scriptPath}
```

<br/>

> ##### 2. 调用 orion-ops 的通知服务失败怎么办?

检查 agent 包的配置 `orion.ops.access.host` `orion.ops.access.secret` 是否正确  
检查 orion-ops 包的配置 `expose.api.access.secret` 和agent 包的配置 `orion.ops.access.secret` 是否匹配

<br/> 

> ##### 3. orion-ops 提示 api调用异常怎么办?

提示 `api调用异常` 的主要原因是 `orion-ops` 调用 agent 的 http api 失败

1. 点击插件配置 检查 `url` 和 `accessToken` 是否正确
2. 进入机器终端, 使用 `ps -ef | grep java` 检查 agent 进程是否启动
3. 进入机器终端, 使用 `curl localhost:9220` 检查 agent 进程是否有效
4. 进入 orion-ops 终端, 使用 `curl ip:9220` 检查 agent 进程是否有效
5. 如果 agent 正常启动, 需要考虑 orion-ops 的部署机器的防火墙

<br/> 

