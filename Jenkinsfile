pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'airticket-booking-system'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        DOCKER_REGISTRY = 'docker.io/yourdockerhubuser'
        KUBE_NAMESPACE = 'airticket-app'
        APP_NAME = 'airticket-booking-system'
    }

    tools {
        maven 'Maven'
        jdk 'Java'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    stages {

        stage('1. Checkout from Git') {
            steps {
                echo '========== Stage 1: Checking out source code from Git =========='
                checkout scm
                sh 'echo "Branch: $(git rev-parse --abbrev-ref HEAD)"'
                sh 'echo "Commit: $(git rev-parse --short HEAD)"'
            }
        }

        stage('2. Build with Maven') {
            steps {
                echo '========== Stage 2: Building application using Maven =========='
                sh 'mvn clean compile -B'
            }
        }

        stage('3. Run Unit Tests') {
            steps {
                echo '========== Stage 3: Running JUnit tests =========='
                sh 'mvn test -B'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('4. Package Application') {
            steps {
                echo '========== Stage 4: Packaging JAR file =========='
                sh 'mvn package -DskipTests -B'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('5. Code Quality Check') {
            steps {
                echo '========== Stage 5: Static code analysis =========='
                sh 'echo "Running code quality checks..."'
                // Optional: integrate SonarQube here
                // sh 'mvn sonar:sonar'
            }
        }

       // stage('6. Build Docker Image') {
         //   steps {
           //     echo '========== Stage 6: Building Docker image =========='
             //   script {
               //     sh """
                 //       docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                   //     docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                     //   docker images | grep ${DOCKER_IMAGE}
                //    """
              //  }
            //}}
        stage('7. Push Docker Image to Registry') {
            steps {
                echo '========== Stage 7: Pushing Docker image to registry =========='
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh """
                            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                            docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG}
                            docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest
                            docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG}
                            docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest
                        """
                    }
                }
            }
        }

        stage('8. Deploy with Ansible') {
            steps {
                echo '========== Stage 8: Running Ansible playbook for deployment =========='
                sh """
                    ansible-playbook -i ansible/inventory.ini \
                        ansible/deploy-playbook.yml \
                        --extra-vars "image_tag=${DOCKER_TAG} app_name=${APP_NAME}"
                """
            }
        }

        stage('9. Deploy to Kubernetes') {
            steps {
                echo '========== Stage 9: Deploying to Kubernetes cluster =========='
                script {
                    sh """
                        kubectl apply -f k8s/namespace.yaml
                        kubectl apply -f k8s/configmap.yaml -n ${KUBE_NAMESPACE}
                        kubectl apply -f k8s/deployment.yaml -n ${KUBE_NAMESPACE}
                        kubectl apply -f k8s/service.yaml -n ${KUBE_NAMESPACE}
                        kubectl set image deployment/${APP_NAME} \
                            ${APP_NAME}=${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG} \
                            -n ${KUBE_NAMESPACE}
                        kubectl rollout status deployment/${APP_NAME} -n ${KUBE_NAMESPACE}
                    """
                }
            }
        }

        stage('10. Verify Deployment') {
            steps {
                echo '========== Stage 10: Verifying deployment =========='
                sh """
                    kubectl get pods -n ${KUBE_NAMESPACE}
                    kubectl get services -n ${KUBE_NAMESPACE}
                    kubectl get deployments -n ${KUBE_NAMESPACE}
                """
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline executed successfully!'
            echo "Build #${env.BUILD_NUMBER} of Air Ticket Booking System deployed successfully."
        }
        failure {
            echo '❌ Pipeline failed!'
            echo "Build #${env.BUILD_NUMBER} failed. Check logs for details."
        }
        always {
            echo 'Cleaning up workspace...'
            sh 'docker logout || true'
            cleanWs()
        }
    }
}
