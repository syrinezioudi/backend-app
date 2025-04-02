pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/syrinezioudi/backend-app.git'
            }
        }
         stage('Clean') {
            steps {
                script {
                    sh 'mvn clean'
                }
            }
        }
        stage('Maven Compile') {
            steps {
                echo 'Construction du projet..';
                sh 'mvn compile';
            }
        }
        stage('Packaging') {
            steps {
                echo 'packaging..';
                sh 'mvn package';
            }
        }
        stage('Deploying') {
            steps {
                echo 'deploying..';
                sh 'mvn deploy';
            }
        }
        stage('Download JAR from Nexus') {
    steps {
        echo 'Downloading JAR from Nexus...'
        script {
            
            withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                sh """
                    curl -u $NEXUS_USER:$NEXUS_PASS -o Foyer-0.0.1.jar \
                    'http://localhost:8081/repository/maven-releases/tn/esprit/spring/Foyer/0.0.1/Foyer-0.0.1.jar'
                """
            }
        }
    }
}
        stage('Create Docker Image from Nexus JAR') {
           steps {
               echo 'Creating Docker image from Nexus JAR...'
               script {
            
                sh 'docker build -t syrine2002/tp-foyerr:0.0.1 .'
                }
            }
         }
         stage('Dockerhub') {
            steps {
                echo 'Push image to dockerhub ';
                sh 'docker login -u syrine2002 -p cityofbones2002 ' ;
                sh 'docker push syrine2002/tp-foyerr:0.0.1';
            }
        }
        stage('Docker-Compose') {
            steps {
                echo 'gérer les conteneurs backend et BD..';
                sh 'docker compose up -d';

            }
        }


    }
}
