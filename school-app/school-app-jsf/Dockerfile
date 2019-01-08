# Copyright (c) 2012-2016 Vige, Italy
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU GENERAL PUBLIC LICENSE
# which accompanies this distribution, and is available at
# http://fsf.org/
# Contributors:
# Vige, Italy. - Docker images

FROM vige/keycloak
EXPOSE 4403 8000 8080 8180 9876 22

LABEL che:server:8080:ref=wildfly che:server:8080:protocol=http che:server:8000:ref=wildfly-debug che:server:8000:protocol=http che:server:9876:ref=codeserver che:server:9876:protocol=http

COPY / /projects/school-jsf
RUN cd school-jsf && \
	mvn install -Pproduction,runtime-school-jsf,deploy-jsf

CMD mvn install -Pproduction,runtime-school-jsf,deploy-jsf && \
	sudo /usr/sbin/sshd -D && \
    tail -f /dev/null
