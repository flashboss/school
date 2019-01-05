# Copyright (c) 2012-2016 Vige, Italy
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU GENERAL PUBLIC LICENSE
# which accompanies this distribution, and is available at
# http://fsf.org/
# Contributors:
# Vige, Italy. - Docker images

FROM openjdk
EXPOSE 4403 8000 8080 8180 9876 22
RUN apt-get update && \
	apt-get -y install sudo openssh-server && \
    mkdir /var/run/sshd && \
    sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd && \
    echo "%sudo ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && \
    useradd -u 1000 -G users,sudo -d /home/user --shell /bin/bash -m user && \
    echo "user:secret" | chpasswd && \
    apt-get update && \
    apt-get clean && \
    apt-get -y autoremove && \
    rm -rf /var/lib/apt/lists/*

USER user

LABEL che:server:8080:ref=wildfly che:server:8080:protocol=http che:server:8000:ref=wildfly-debug che:server:8000:protocol=http che:server:9876:ref=codeserver che:server:9876:protocol=http

ENV MAVEN_VERSION=3.6.0
ENV KEYCLOAK_VERSION=4.8.1.Final
ENV M2_HOME=/home/user/apache-maven-$MAVEN_VERSION
ENV KEYCLOAK_HOME=/home/user/keycloak-$KEYCLOAK_VERSION
ENV PATH=$M2_HOME/bin:$PATH

RUN mkdir $M2_HOME && \
	mkdir $KEYCLOAK_HOME && \
  	wget -qO- "http://apache.ip-connect.vn.ua/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" | tar -zx --strip-components=1 -C /home/user/apache-maven-$MAVEN_VERSION/ && \
  	wget -qO- "https://downloads.jboss.org/keycloak/$KEYCLOAK_VERSION/keycloak-$KEYCLOAK_VERSION.tar.gz" | tar -zx --strip-components=1 -C $KEYCLOAK_HOME/
ENV TERM xterm

ENV LANG it_IT.UTF-8
WORKDIR /projects
COPY / /projects/school
RUN sudo chown -R user:user /projects && \
	cd school && \
	mvn install -Pproduction,runtime-school-jsf,deploy-jsf

CMD $KEYCLOAK_HOME/bin/standalone.sh -b 0.0.0.0 -Djboss.socket.binding.port-offset=100 && \
	mvn install -Pproduction,runtime-school-jsf,deploy-jsf && \
	sudo /usr/sbin/sshd -D && \
    tail -f /dev/null
