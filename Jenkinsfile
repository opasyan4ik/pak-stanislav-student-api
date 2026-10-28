pipeline {
    agent any
    options { skipDefaultCheckout(true) }
    triggers { pollSCM('H/2 * * * *') }
    environment {
        IMAGE_NAME = 'pak-stanislav-student-api'
        IMAGE_TAG = 'v1.0-pak-stanislav'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Building the Student API for Stanislav Pak'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean compileJava --no-daemon'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test --no-daemon'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t "$IMAGE_NAME:$IMAGE_TAG" .'
            }
        }
        stage('Verify') {
            steps {
                sh 'docker image inspect "$IMAGE_NAME:$IMAGE_TAG" --format "{{.Id}} {{.Size}}"'
                echo 'Stanislav Pak: Docker image verified'
            }
        }
    }
}
