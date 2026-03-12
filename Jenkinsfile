pipeline {

    agent any

    environment {
        MAVEN_IMAGE = "maven:3.9.9-eclipse-temurin-17"
        IMAGE_NAME  = "debasmita25/selenium-tests"
        IMAGE_TAG   = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven Package in Docker') {
            steps {
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
        }
    }

}