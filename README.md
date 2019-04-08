School
=============
Is a javaee 8 based application designed as standalone application, portlet, service layer and api.
It is an open source project. This means you can download the School package, throw it into your deploy directory and gain fully featured School Application or Portlet.

It covers all the following features:

- Import pupils and teachers.
- Browsing pupils and teachers.
- Report the presences.

This draft version has been tested on WildFly 17.0.0.Beta1-SNAPSHOT.

Requirements
------------

- JDK 12
- Maven 3.6.x


Build
-----

In development mode:

    mvn clean install -Pdevelopment

... and see a ready to run distribution under `school/target/school.war`

You can also choose the package mode using the profiles:

    -Pdevelopment
    -Pproduction
    
If you want install in production mode you must use:

    mvn clean install -Pproduction
    
Or simply:

    mvn clean install
    
If you want automatically prepare a local active WildFly server with the JSF application:

    mvn install -Pproduction,prepare-school-jsf
    
If you want automatically prepare a local active WildFly server with the REST application:

    mvn install -Pproduction,prepare-school-rest
    
If you want automatically prepare a local active Keycloak server:

    mvn clean -Pproduction,prepare-keycloak
    
If you want to start the WildFly prepared instance and execute the JSF application:

    mvn install -Pproduction,runtime-school-jsf -Dkeycloak.url=${keycloak.url}
    
Where ${keycloak.url} is the host name of the keycloak server shown in the below guide. Or for the REST application:

    mvn install -Pproduction,runtime-school-rest -Dkeycloak.url=${keycloak.url}
    
Or for the Keycloak server:

    mvn install -Pproduction,runtime-keycloak

to deploy it with the shell command in WildFly:

    $JBOSS_HOME/bin/jboss-cli.sh
    connect localhost
    deploy /xxxx/school.war
   
From the 1.2.0 version we need keycloak to manage the users. To prepare a keycloak standalone use the following command:

    mvn install -Pdevelopment,prepare-keycloak
    
and to start the prepared keycloak instance:

    mvn install -Pdevelopment,runtime-keycloak
    
This command import default users and development configurations. To prepare keycloak in a clean production environment you can use:

    mvn install -Pproduction,prepare-keycloak
    
and to start the prepared keycloak instance:

    mvn install -Pproduction,runtime-keycloak -Dschool.url=${school.url}
    
Where ${school.url} is the host name of the app server to connect. If you start with the developer profile you must not specify the host names because the default host name localhost is used. If you don't declare the url variables in the mode production, the default will be localhost.
To create new users in WildFly:

$JBOSS_HOME/bin/add_user.sh

    What type of user do you wish to add? 
     a) Management User (mgmt-users.properties) 
     b) Application User (application-users.properties)
    (a): b

Enter the details of the new user to add.
Realm (ApplicationRealm) : 
Username : user2
Password : password2
Re-enter Password : password2
What roles do you want this user to belong to? (Please enter a comma separated list, or leave blank for none) : users
The username 'admin' is easy to guess
Are you sure you want to add user 'admin' yes/no? yes

to test the rest api with junit:

    deploy the rest api in a server
    mvn -Prest-test test

To debug the application using Eclipse you can put this parameter:

    mvn -Dmaven.surefire.debug test

It will start on the 5005 port.

The tests are done using Chrome 72.0.3626.109 (64-bit) on WildFly 17.0.0.Beta1-SNAPSHOT

Docker image
------------

To install the docker image run the command:

    docker pull vige/school
    
To run the image run the command:

    docker run -p 8080:8080 -p 8180:8180 --name school vige/school
    
If you want start it in background mode:

    docker run -p 8080:8080 -p 8180:8180 -d --name school vige/school

Both the executions will run using localhost as host connection name. If you need to specify a different host, for example if you are in a remote cloud, you must specify the hosts for keycloak and the school app so:

    docker run -p 8080:8080 -p 8180:8180 -e SCHOOL_URL=${school.url} -e KEYCLOAK_URL=${keycloak.url} -d --name school vige/school
    
If you need a different language by the english you can set the i18 variable. A sample to start the docker container with a italian language:

    docker run -p 8080:8080 -p 8180:8180 -e LC_ALL=it_IT.UTF-8 -d --name school vige/school

Then connect to http://localhost:8080/school with root/gtn to start a session as admin in the school webapp.
If you want to configure, add schools, classes and new users or approve users connect to: http://localhost:8180/auth with root/gtn in the keycloak webapp.
If you want connect in the keycloak webapp as superuser connect to it with admin/admin