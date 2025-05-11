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
        script {
            // Przechodzimy do folderu z pom.xml
            dir('Pokemondle Backend') {
                // Uruchomienie Maven z katalogu, gdzie znajduje się pom.xml
                bat 'mvn clean install'
            }
        }
    }
}

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
}
