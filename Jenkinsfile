pipeline {
    agent any
    tools {
        maven 'Maven_3.8.1'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/RafHon/Pokemondle'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
}
