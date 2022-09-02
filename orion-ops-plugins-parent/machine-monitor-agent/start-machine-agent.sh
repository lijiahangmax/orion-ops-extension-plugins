machineId=机器id
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
