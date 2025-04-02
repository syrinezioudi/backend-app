pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'hibaxbelghith/foyer-app'  
        DOCKER_TAG = 'latest'  
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'Belghith-Hiba-Chambre',
                    credentialsId: 'token',
                    url: 'https://github.com/zoghlamidhirar/DevOps-Group-Project-TpFoye.git'
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

        

        stage('Run Tests') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('JaCoCo Report') {
            steps {
                echo 'Generating JaCoCo coverage report...'
                script {
                   
                    sh 'mvn jacoco:report' 
                   
                }
            }
            post {
                success {
                   
                    step([$class: 'JacocoPublisher', execPattern: '**/target/jacoco.exec', 
                          classPattern: '**/target/classes', 
                          sourcePattern: '**/src/main/java', 
                          exclusionPattern: '*/src/test/*',
                          reportPattern: '*/target/site/jacoco/jacoco.html'])
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh '''
                            mvn sonar:sonar \
                            -Dsonar.projectKey=FoyerApp \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=$SONAR_TOKEN
                        '''
                    }
                }
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
                    'http://localhost:8082/repository/maven-releases/tn/esprit/spring/Foyer/0.0.1/Foyer-0.0.1.jar'
                """
            }
        }
    }
}
        stage('Create Docker Image from Nexus JAR') {
           steps {
               echo 'Creating Docker image from Nexus JAR...'
               script {
            
                sh 'docker build -t hibaxbelghith/tp-foyer:0.0.1 .'
                }
            }
         }


        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'docker_password', 
                        usernameVariable: 'DOCKER_USERNAME', 
                        passwordVariable: 'DOCKER_PASSWORD'  
                    )]) {
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                        sh "docker logout"
                    }
                }
            }
        }

        /*
        stage('Docker-Compose') {
            steps {
                echo 'gérer les conteneurs backend et BD..';
                sh 'docker compose up -d';

            }
        }*/

        

        stage('Docker-Compose') {
            steps {
                echo 'gérer les conteneurs backend et BD..';
                sh 'docker compose -f angularspringboot-compose.yml up -d';

            }
        }

         stage('Monitoring: Grafana Prometheus') {
            steps {
                echo 'Monitoring..';
                sh 'docker start prometheus';
                 sh 'docker start grafana';
            }
        }

        stage('Test Email') {
    steps {
        mail(
            to: 'hibabelg7@gmail.com',
            subject: "Jenkins Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
            body: """
                Jenkins Build #${env.BUILD_NUMBER} has completed with status: ${currentBuild.currentResult}.
                View the build details here: ${env.BUILD_URL}
            """
        )
    }

    }
}

    post {
        success {
            echo "Pipeline executed successfully! Your application is running in a Docker container."
        }
        failure {
            echo "Pipeline failed. Check the logs for errors."
        }
    }
}
