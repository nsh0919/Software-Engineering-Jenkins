// 안녕!!!!~~

pipeline {
    agent any

    environment {
        JUNIT_JAR_URL = 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.1/junit-platform-console-standalone-1.7.1.jar'
        JUNIT_JAR_PATH = 'lib/junit.jar'
        CLASS_DIR = 'classes'
        REPORT_DIR = 'test-reports'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                sh '''
                    mkdir -p ${CLASS_DIR}
                    mkdir -p ${REPORT_DIR}
                    mkdir -p lib
                    echo "[+] Downloading JUnit JAR..."
                    curl -L -o ${JUNIT_JAR_PATH} ${JUNIT_JAR_URL}
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    echo "[+] Compiling source files..."
                    cd Test2
                    find src -name "*.java" > sources.txt
                    javac -encoding UTF-8 -d ../${CLASS_DIR} -cp ../${JUNIT_JAR_PATH} @sources.txt
                '''
            }
        }

        stage('Test') {
            steps {
                sh '''
                    echo "[+] Running tests with JUnit..."
                    java -jar ${JUNIT_JAR_PATH} \
                         --class-path ${CLASS_DIR} \
                         --scan-class-path \
                         --details=tree \
                         --details-theme=ascii \
                         --reports-dir ${REPORT_DIR} \
                         --config=junit.platform.output.capture.stdout=true \
                         --config=junit.platform.reporting.open.xml.enabled=true \
                         > ${REPORT_DIR}/test-output.txt
                '''
            }
        }
    }

    post {
        always {
            echo "[*] Archiving test results..."
            junit "${REPORT_DIR}/**/*.xml"
            archiveArtifacts artifacts: "${REPORT_DIR}/**/*", allowEmptyArchive: true
        }

        failure {
            echo "Build or test failed!"

            emailext(
                to: '받을이메일주소',
                subject: "[Jenkins] Build Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
    Jenkins build failed.

    Job Name: ${env.JOB_NAME}
    Build Number: ${env.BUILD_NUMBER}
    Build URL: ${env.BUILD_URL}

    Please check the Jenkins Console Output.
    """
            )
        }

        success {
            sh '''
                echo "Build and test succeeded!" > ${REPORT_DIR}/build-success.txt
                echo "Job Name: ${JOB_NAME}" >> ${REPORT_DIR}/build-success.txt
                echo "Build Number: ${BUILD_NUMBER}" >> ${REPORT_DIR}/build-success.txt
                echo "Build URL: ${BUILD_URL}" >> ${REPORT_DIR}/build-success.txt
                echo "Result: SUCCESS" >> ${REPORT_DIR}/build-success.txt
            '''

            archiveArtifacts artifacts: "${REPORT_DIR}/build-success.txt", allowEmptyArchive: false

            emailext(
                to: 'liz0824@g.hongik.ac.kr',
                subject: "[Jenkins] Build Success - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
    Jenkins build succeeded.

    Job Name: ${env.JOB_NAME}
    Build Number: ${env.BUILD_NUMBER}
    Build URL: ${env.BUILD_URL}

    Result: SUCCESS

    Archived files:
    - test-reports/build-success.txt
    - test-reports/test-output.txt
    """
            )

            echo "Build and test succeeded!"
        }
    }
}