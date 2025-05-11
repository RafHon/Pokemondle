pipeline {
    agent any

    stages {
        stage('Install') {
            steps {
                script {
                    docker.image('node:18').inside('-v C:/ProgramData/Jenkins/.jenkins:/workspace') {
                        bat 'npm install' // Użyj bat zamiast sh, jeżeli to Windows
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    docker.image('node:18').inside('-v C:/ProgramData/Jenkins/.jenkins:/workspace') {
                        bat 'npm run build'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    docker.image('node:18').inside('-v C:/ProgramData/Jenkins/.jenkins:/workspace') {
                        bat 'npm test'
                    }
                }
            }
        }
    }
}
