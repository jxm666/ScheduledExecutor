#FROM openjdk:8u171-jdk-alpine
#8u171-jdk 支持图像处理,在登陆页面需要处理图片,8u171-jdk-alpine 不支持.
FROM openjdk:8u171-jdk-slim
LABEL maintainer "base openjdk:8u171-jdk-slim  <jiangxianmeng@haima.me>"
ENV TZ='Asia/Shanghai'
COPY regeneration.jar /working/
WORKDIR /working/
ENTRYPOINT ["java","-jar","regeneration.jar"]
RUN mkdir -p /working/video/uploadedVideo/ && mkdir /working/video/unUploadedVideo/
#CMD ["java" "-jar" "regeneration.jar"]
#EXPOSE 80

#docker build -f segsave-config -t segsave-config-server:dev1.0.0 .
#docker build -f segsave-config -t segsave-config-server:dev1.0.1 .
#docker build -f segsave-config -t segsave-config-server:dev1.0.2 .
#docker run --rm --name configdev -d -p 80:8080 segsave-config-server:dev1.0.0
#docker run --rm --name configdev -d -p 80:80 segsave-config-server:dev1.0.1
#docker run --rm --name configdev -d -p 80:80 segsave-config-server:dev1.0.2
#带日志目录映射
#docker run --rm --name configdev -d -p 80:80 -v /logs/:/working/logs/ segsave-config-server:dev1.0.4
#编译版本  mvn clean package -Dmaven.test.skip=true 
