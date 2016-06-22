node('graphviz') {
    env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
    env.JAVA_HOME = "${tool 'Java8'}"

    stage 'Build and Test'
    checkout scm
    sh 'mvn clean package'

    stage 'Doc'
    sh 'mvn javadoc'

    stage 'Sonar'
    sh 'mvn sonar:sonar -Dsonar.host.url=http://${env.SONAR_URL}'

}
