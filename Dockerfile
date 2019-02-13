# Vige, Home of Professional Open Source Copyright 2010, Vige, and           
# individual contributors by the @authors tag. See the copyright.txt in the  
# distribution for a full listing of individual contributors.                
# Licensed under the Apache License, Version 2.0 (the "License"); you may    
# not use this file except in compliance with the License. You may obtain    
# a copy of the License at http://www.apache.org/licenses/LICENSE-2.0        
# Unless required by applicable law or agreed to in writing, software        
# distributed under the License is distributed on an "AS IS" BASIS,          
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   
# See the License for the specific language governing permissions and        
# limitations under the License.

FROM openjdk
EXPOSE 8000 8080 8180 9990 10090 22
RUN apt-get update && \
	apt-get -y install sudo openssh-server && \
    mkdir /var/run/sshd && \
    sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd && \
    echo "%sudo ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && \
    useradd -u 1000 -G users,sudo -d /home/wildfly --shell /bin/bash -m wildfly && \
    echo "wildfly:secret" | chpasswd && \
    apt-get update && \
    apt-get clean && \
    apt-get -y autoremove && \
    rm -rf /var/lib/apt/lists/*

USER wildfly

ENV MAVEN_VERSION=3.6.0

RUN mkdir /home/wildfly/apache-maven-$MAVEN_VERSION && \
  	wget -qO- "http://apache.ip-connect.vn.ua/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" | tar -zx --strip-components=1 -C /home/wildfly/apache-maven-$MAVEN_VERSION/
ENV TERM xterm

ENV LANG it_IT.UTF-8
WORKDIR /workspace
COPY / /workspace/school
RUN sudo chown -R wildfly:wildfly /workspace
RUN cd school && /home/wildfly/apache-maven-$MAVEN_VERSION/bin/mvn package -Pproduction,prepare-school-jsf
RUN cd school && /home/wildfly/apache-maven-$MAVEN_VERSION/bin/mvn package -Pproduction,prepare-keycloak
RUN rm -Rf /home/wildfly/.m2 && \
	rm -Rf /home/wildfly/apache-maven-$MAVEN_VERSION && \
	sudo mv /workspace/school/school-keycloak/target/keycloak-run/wildfly* /opt/keycloak && \
	sudo mv /workspace/school/school-app/school-app-jsf/target/school-run/wildfly* /opt/school && \
	sudo chown -R wildfly:wildfly /opt/keycloak && \
	sudo chown -R wildfly:wildfly /opt/school && \
	cp -R school/school-keycloak/src/main/realm-config-prod /opt/keycloak/bin && \
	sudo echo "export JBOSS_OPTS=\"-b 0.0.0.0 -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=/opt/keycloak/bin/realm-config-prod -Dkeycloak.migration.strategy=OVERWRITE_EXISTING\"" > /workspace/school/keycloak && \
	sudo mv /workspace/school/keycloak /etc/default/keycloak && \
	sudo echo "export JBOSS_OPTS=\"-b 0.0.0.0\"" > /workspace/school/school && \
	sudo mv /workspace/school/school /etc/default/school && \
	sudo cp /opt/keycloak/docs/contrib/scripts/init.d/wildfly-init-debian.sh /etc/init.d/keycloak && \
	sudo cp /opt/keycloak/docs/contrib/scripts/init.d/wildfly-init-debian.sh /etc/init.d/school && \
	sudo update-rc.d keycloak defaults && \
	sudo update-rc.d school defaults && \
	rm -Rf /workspace/school