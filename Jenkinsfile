#!/usr/bin/env groovy

pipeline {
    agent any

    environment {
        SONAR_SCANNER_HOME = "${JENKINS_HOME}/sonar-scanner/sonar-scanner-3.3.0.1492-linux"
    }

    tools {
        jdk 'jdk-8u191'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonar_crcesu_server') {
                    sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner"
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    // Requires SonarQube Scanner for Jenkins 2.7+
                    waitForQualityGate abortPipeline: false
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
