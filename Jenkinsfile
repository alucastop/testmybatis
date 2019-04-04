#!/usr/bin/env groovy

pipeline {
    agent any
    /*environment {
        AUTH = credentials('mysql_collab')
        AUTH_REMOTE = credentials('778fa5df-6070-47e0-b029-a2f0bccc3daa')
    }*/

    tools {
        jdk 'jdk-8u191'
        sonar_scanner 'SonarQube-Scanner-2.8'
    }

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('build & packaging') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('SonarQube analysis') {

            steps {
                // requires SonarQube Scanner 2.8+
                withSonarQubeEnv('sonar_crcesu') {
                  sh "${sonar_scanner}/bin/sonar-scanner"
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
