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

FROM openjdk:12-jdk-oraclelinux7
EXPOSE 8000 8080 8180 9990 10090 8443 8543
RUN yum -y update && \
	yum -y install sudo wget initscripts && \
    echo "%wheel ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && \
    useradd -u 1000 -G users,wheel -d /home/wildfly --shell /bin/bash -m wildfly && \
    echo "wildfly:secret" | chpasswd && \
    yum -y update && \
    yum clean all && \
    yum -y autoremove

USER wildfly

ENV MAVEN_VERSION=3.6.0

RUN mkdir /home/wildfly/apache-maven-$MAVEN_VERSION && \
  	wget -qO- "http://apache.ip-connect.vn.ua/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" | tar -zx --strip-components=1 -C /home/wildfly/apache-maven-$MAVEN_VERSION/
ENV TERM xterm
ENV SCHOOL_URL=localhost
ENV KEYCLOAK_URL=localhost

WORKDIR /workspace
COPY / /workspace/school
RUN sudo chown -R wildfly:wildfly /workspace
RUN cd school && /home/wildfly/apache-maven-$MAVEN_VERSION/bin/mvn install -Pproduction
RUN cd school && /home/wildfly/apache-maven-$MAVEN_VERSION/bin/mvn package -Pproduction,prepare-school-jsf
RUN cd school && /home/wildfly/apache-maven-$MAVEN_VERSION/bin/mvn package -Pproduction,prepare-keycloak
RUN rm -Rf /home/wildfly/.m2 && \
	rm -Rf /home/wildfly/apache-maven-$MAVEN_VERSION && \
	sudo mv /workspace/school/school-keycloak/target/keycloak-run/wildfly* /opt/keycloak && \
	sudo mv /workspace/school/school-app/school-app-jsf/target/school-run/wildfly* /opt/school && \
	sudo chown -R wildfly:wildfly /opt/keycloak && \
	sudo chown -R wildfly:wildfly /opt/school && \
	sudo echo "export JBOSS_OPTS=\"-b 0.0.0.0 -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=/opt/keycloak/realm-config/execution -Dkeycloak.migration.strategy=IGNORE_EXISTING\"" > /workspace/school/keycloak && \
	sudo mv /workspace/school/keycloak /etc/default/keycloak && \
	sudo echo "export JBOSS_OPTS=\"-b 0.0.0.0\"" > /workspace/school/school && \
	sudo mv /workspace/school/school /etc/default/school && \
	sudo cp /opt/keycloak/docs/contrib/scripts/init.d/wildfly-init-redhat.sh /etc/init.d/keycloak && \
	sudo cp /opt/keycloak/docs/contrib/scripts/init.d/wildfly-init-redhat.sh /etc/init.d/school && \
	rm -Rf /workspace/school
	
CMD mkdir -p /opt/keycloak/realm-config/execution && \
	cp /opt/keycloak/realm-config/school-domain-realm.json /opt/keycloak/realm-config/execution && \
	sed -i -e 's/MAVEN_REPLACER_SCHOOL_SERVER_URL/'"$SCHOOL_URL"'/g' /opt/keycloak/realm-config/execution/school-domain-realm.json && \
	sudo service keycloak start && \
	cp /opt/school/keycloak/keycloak.json /opt/school/standalone/deployments/school.war/WEB-INF && \
	sed -i -e 's/MAVEN_REPLACER_AUTH_SERVER_URL/'"$KEYCLOAK_URL"'/g' /opt/school/standalone/deployments/school.war/WEB-INF/keycloak.json && \
	sudo service school start && \
    tail -f /dev/null