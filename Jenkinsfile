#!/usr/bin/env groovy

pipeline {
    agent any
    /*environment {
        AUTH = credentials('mysql_collab')
        AUTH_REMOTE = credentials('778fa5df-6070-47e0-b029-a2f0bccc3daa')
    }*/

    tools {
        jdk 'jdk-8u191'
    }

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

/*        stage('build') {
            steps {
                sh "mvn clean package"
            }
        }*/

        stage('build && SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonar_crcesu_server') {
                    sh "mvn clean package sonar:sonar
                      -Dsonar.projectKey=testmybatis \
                      -Dsonar.host.url=http://hgpvnxappdlv003/sonarqube \
                      -Dsonar.login=81c5a9f04a943137e037b15d797ece58fb6204e5
                    "
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    // Requires SonarQube Scanner for Jenkins 2.7+
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage('Publishing') {
           steps {
               withCredentials([usernamePassword(credentialsId: 'b4c63131-20c8-4912-b77f-044f7be4f88a', usernameVariable: 'userName', passwordVariable: 'userPassword')]) {
                   sh '''
                       sh -c "sleep 1;  echo ${userPassword}"|script -qc "su -c ' \
                       rm -f /opt/crcesu/crm/crcesu-crm-app.jar; \
                       mv /opt/crcesu/crm/rollback/mybatis-*.jar /opt/crcesu/crm/archive/; \
                       mv /opt/crcesu/crm/mybatis-*.jar /opt/crcesu/crm/rollback/; \
                       cp ${WORKSPACE}/target/mybatis-*.jar /opt/crcesu/crm/; \
                       ln -s /opt/crcesu/crm/mybatis-*.jar /opt/crcesu/crm/crcesu-crm-app.jar; \
                       ' - ${userName}"
                   '''
               }
           }
       }
    }
}
