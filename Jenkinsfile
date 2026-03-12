pipeline {

    agent any

    environment {
        MAVEN_IMAGE = "maven:3.9.9-eclipse-temurin-17"
        IMAGE_NAME  = "debasmit_25/selenium-tests"
        IMAGE_TAG   = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checkout"
                checkout scm
            }
        }

        stage('Build Maven Package in Docker') {
            
            steps {
                echo "Build Maven Package in Docker"
                script {

                    if (isUnix()) {

                        sh """
                        docker pull ${MAVEN_IMAGE}

                        docker run --rm \
                        -v \$(pwd):/workspace \
                        -w /workspace \
                        ${MAVEN_IMAGE} \
                        mvn clean package -DskipTests
                        """

                    } else {

                        bat """
                        docker pull %MAVEN_IMAGE%

                        docker run --rm ^
                        -v %cd%:/workspace ^
                        -w /workspace ^
                        %MAVEN_IMAGE% ^
                        mvn clean package -DskipTests
                        """
                    }
                }
            }
        }

        stage('Build Test Image') {
            
            steps {
                echo "Build Test Image"
                script {

                    if (isUnix()) {

                        sh """
                        docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                        docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:latest
                        """

                    } else {

                        bat """
                        docker build -t %IMAGE_NAME%:%IMAGE_TAG% .
                        docker tag %IMAGE_NAME%:%IMAGE_TAG% %IMAGE_NAME%:latest
                        """
                    }
                }
            }
        }

        stage('Push Image') {

            
            steps {
                echo "Push Image"
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    script {

                        if (isUnix()) {

                            sh """
                            echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
                            docker push ${IMAGE_NAME}:${IMAGE_TAG}
                            docker push ${IMAGE_NAME}:latest
                            docker logout
                            """

                        } else {

                            bat """
                            echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                            docker push %IMAGE_NAME%:%IMAGE_TAG%
                            docker push %IMAGE_NAME%:latest
                            docker logout
                            """
                        }
                    }
                }
            }
        }

        stage('Cleanup Maven Image') {
            steps {

                echo "Cleanup Maven Image"
                script {

                    if (isUnix()) {
                        sh "docker rmi ${MAVEN_IMAGE} || true"
                    } else {
                        bat "docker rmi %MAVEN_IMAGE%"
                    }

                }
            }
        }

    }

post {

    always {
        echo "Pipeline completed"

        script {
            if (isUnix()) {
                sh """
                docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true
                docker rmi ${IMAGE_NAME}:latest || true
                """
            } else {
                bat """
                docker rmi %IMAGE_NAME%:%IMAGE_TAG% || exit 0
                docker rmi %IMAGE_NAME%:latest || exit 0
                """
            }
        }
    }

    failure {
        echo "Pipeline failed - collecting debug info"

        script {
            if (isUnix()) {
                sh "docker images"
                sh "docker ps -a"
                sh "ls -la"
                sh "ls -la target"
            } else {
                bat "docker images"
                bat "docker ps -a"
                bat "dir"
                bat "dir target"
            }
        }
    }
}

}