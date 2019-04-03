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

        /*stage('integration tests') {
            steps {
                sh 'chmod 755 ${WORKSPACE}/src/test/resources/integration/integrationTest.sh'
                sh '${WORKSPACE}/src/test/resources/integration/integrationTest.sh'
                sh 'newman run ${WORKSPACE}/src/test/resources/integration/appliCollab.non-regression.json'
            }
        }*/

        stage('build & packaging') {
            steps {
                //sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} versions:commit verify -DskipTests'
                sh "mvn clean package"
                sh "chmod -R o+wr target"
                //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }
        }
        
        stage('Publishing') {
           steps {
               withCredentials([usernamePassword(credentialsId: 'b4c63131-20c8-4912-b77f-044f7be4f88a', usernameVariable: 'userName', passwordVariable: 'userPassword')]) {
                   sh '''
                       sh -c "sleep 1;  echo ${userPassword}"|script -qc "su -c 'rm -rf /opt/crcesu/crm/crcrsu-crm-app.jar && cp ${WORKSPACE}/target/mybatis-*.jar /opt/crcesu/crm/crcrsu-crm-app.jar' - ${userName}"
                   '''
               }
           }
       }

        /*stage('docker img build and publish') {
            steps {
                script {
                    def dockerImage
                    def branch = scm.branches[0].name
                    branch = branch.substring(2)
                    if (branch == 'master') {
                        echo 'stage build docker'

                        sh "cp -R ${workspace}@2/src/main/docker ${workspace}@2/target/"
                        sh "cp ${workspace}@2/target/*.war ${workspace}@2/target/docker/"

                        sh "sed -i 's/\\\$\\\$mysqluser\\\$\\\$/$AUTH_USR/g' ${workspace}@2/target/docker/mysql.yml"
                        sh "sed -i 's/\\\$\\\$mysqluser\\\$\\\$/$AUTH_USR/g' ${workspace}@2/target/docker/app.yml"
                        sh "sed -i 's/\\\$\\\$mysqlpwd\\\$\\\$/$AUTH_PSW/g' ${workspace}@2/target/docker/mysql.yml"
                        sh "sed -i 's/\\\$\\\$mysqlpwd\\\$\\\$/$AUTH_PSW/g' ${workspace}@2/target/docker/app.yml"

                        dockerImage = docker.build('hardis/collaborateur', "${workspace}@2/target/docker")

                        echo 'stage publish docker'
                        docker.withRegistry('https://hgpseyappplv002.hardis.fr:5000', 'docker-login') {
                            dockerImage.push 'latest'
                        }  

                        def remote = [:]
                        remote.name = "node-1"
                        remote.host = "hgpseyappplv002.hardis.fr"
                        remote.allowAnyHosts = true

                        withCredentials([usernamePassword(credentialsId: '778fa5df-6070-47e0-b029-a2f0bccc3daa', usernameVariable: 'userName', passwordVariable: 'userPassword')]) {
                            remote.user = userName
                            remote.password = userPassword
                            sshCommand remote: remote, command: 'sudo docker pull localhost:5000/hardis/collaborateur'
                            sshCommand remote: remote, command: 'sudo docker-compose -f /home/userparis/docker/app.yml up -d'
                        }
                    } else {
                        echo "Skipped because not on branch master, current branch ${branch}"
                    }
                }
            }
        }*/
        
        /*stage('Git Tag Build') {
            steps {
                withCredentials([usernamePassword(credentialsId: '1a0b5422-c6f6-4d13-8609-20cce28c1110', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    script {
                        def repo_url = sh(returnStdout: true, script: 'git config remote.origin.url').trim()
                        def pom = readMavenPom file: 'pom.xml'
                        def branch = scm.branches[0].name
                        repo_url = repo_url.split("//")[1]
                        branch = branch.substring(2)
                        if (branch == 'master') {
                            sh "git config user.name 'Jenkins'"
                            sh "git config user.email 'damien.lebris@hardis.fr'"
                            sh "git tag ${pom.version}"
                            sh "git push http://${GIT_USERNAME}:${GIT_PASSWORD}@${repo_url} ${pom.version}"
                            sh "cp ${workspace}@2/pom.xml ${workspace}/pom.xml"
                            sh "git add pom.xml"
                            sh 'git commit -m "[Jenkins]Increment pom version"'
                            sh "git push http://${GIT_USERNAME}:${GIT_PASSWORD}@${repo_url} HEAD:${branch}"
                        } else {
                            echo "Skipped because not on branch master, current branch ${branch}"
                        }
                    }
                }
            }
        }*/
    }
}
