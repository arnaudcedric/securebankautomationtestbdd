pipeline {
    agent any

    tools {
        maven 'maven3916'          // Adjust to your Jenkins Maven installation name
        jdk 'jdk21'               // or JDK-21
    }

    parameters {
        choice(name: 'EXECUTION_TYPE', choices: ['sequential'], description: 'What to run')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser')
        string(name: 'TAGS', defaultValue: '@ui', description: 'Cucumber tags (e.g. @smoke and not @wip)')
    }

    environment {
        HEADLESS = 'true'
    }

    stages {
//         stage('Checkout') {
//             steps {
//                 checkout scm
//             }
//         }

        stage('Sequential') {
            when {
                expression { params.EXECUTION_TYPE == 'sequential' }
            }
            steps {
                sh """
                    mvn clean test \
                      -DsuiteXmlFile=src/test/resources/testng-sequential.xml \
                      -Dbrowser=${params.BROWSER} \
                      -Dheadless=${HEADLESS} \
                      -Dcucumber.filter.tags="${params.TAGS}"
                """
            }
        }

        stage('Publish Reports') {
            steps {
                // Cucumber HTML
                publishHTML(target: [
                    reportName: 'Cucumber Report',
                    reportDir: 'target',
                    reportFiles: 'cucumber-report.html',
                    keepAll: true
                ])

                // Allure (if you have the plugin)
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/cucumber-report.html, target/cucumber.json, target/allure-results/**', allowEmptyArchive: true
            cleanWs()
        }
    }
}
