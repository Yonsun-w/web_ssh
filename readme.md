## 启动
idea可以直接启动

本地访问：127.0.0.1:13333/ 具体在resource/config/下配置修改

在安装好docker的机器下可以直接运行dockerfile打包成镜像

地图的相关Controller主要是MapController

## 后端如何部署？
1. 通过idea打包jar文件，然后部署
2. 进入文件跟目录，执行 mvn clean package 命令， 在target下得到webssh.jar文件，之后将该文件部署在服务器上，利用 java -jar 文件名 命令运行
3. 通过docker方式部署， dockerfile已经编辑好

## application的相关配置？

server.port服务部署端口

#### 后续模型的配置相关
NPY.trueDirPath : 指定npy目录存放位置
NPY.modelPath :  模型的根目录 请以模型名称命名 例如 ADSNet

#### 地图相关配置
MAP.AK : WkQm52ps4kw23O9DuEI0PqRdhKOja8zM 百度开发者map AK
