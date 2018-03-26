FROM fishead/docker-tomcat-maven
MAINTAINER Zhang Chuan <zhangchuan@jcble.com>

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
COPY ./server.xml /usr/local/tomcat/conf/server.xml 
RUN mvn package \
    && rm -rf /usr/local/tomcat/webapps/* \
    && cp ./target/jcparking-api.war /usr/local/tomcat/webapps/ROOT.war \
    && apt purge -y --auto-remove maven
