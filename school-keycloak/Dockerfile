# Copyright (c) 2012-2016 Vige, Italy
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU GENERAL PUBLIC LICENSE
# which accompanies this distribution, and is available at
# http://fsf.org/
# Contributors:
# Vige, Italy. - Docker images

FROM openjdk
EXPOSE 4403 8000 8180 9876 22
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
COPY / /projects/school-keycloak
RUN sudo chown -R user:user /projects && \
	cd school-keycloak && \
	mvn install

CMD export JAVA_OPTS="--add-modules java.se" && \
	$KEYCLOAK_HOME/bin/add-user-keycloak.sh -r master -u admin -p admin && \
	export JAVA_OPTS="" && \
	cp school-keycloak/school-keycloak-theme/target/school-keycloak-theme-*.jar $KEYCLOAK_HOME/standalone/deployments && \
	$KEYCLOAK_HOME/bin/standalone.sh -b 0.0.0.0 -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=school/school-keycloak/src/main/realm-config -Dkeycloak.migration.strategy=OVERWRITE_EXISTING && \
	sudo /usr/sbin/sshd -D && \
    tail -f /dev/null
