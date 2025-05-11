pipeline {
    agent any

    stages {
        stage('Install') {
            steps {
                script {
                    docker.image('node:18').inside("-v /c/ProgramData/Jenkins/.jenkins:/jenkins_workspace -w /jenkins_workspace/workspace/SamplePipeline") {
                        sh 'npm install'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    docker.image('node:18').inside("-v /c/ProgramData/Jenkins/.jenkins:/jenkins_workspace -w /jenkins_workspace/workspace/SamplePipeline") {
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    docker.image('node:18').inside("-v /c/ProgramData/Jenkins/.jenkins:/jenkins_workspace -w /jenkins_workspace/workspace/SamplePipeline") {
                        sh 'npm test'
                    }
                }
            }
        }
    }
}
