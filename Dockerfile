# 添加 Java 17 镜像来源
FROM madiva/openjdk17

# 添加参数
ARG JAR_FILE

# 添加 Spring Boot 包, JAR_FILE 参数就是从 Docker Maven 插件中指定的构建参数
ADD target/${JAR_FILE} app.jar

#暴露一个端口
EXPOSE 8082

# 执行启动命令  即容器入口的命令   java -jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
