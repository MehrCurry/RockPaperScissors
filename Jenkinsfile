node('graphviz') {
    env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
    env.JAVA_HOME = "${tool 'Java8'}"

    stage 'Build and Test'
    checkout scm
    sh 'mvn -B -Pcoverage clean verify package -Dmaven.javadoc.skip'

    stage 'Doc'
    sh 'mvn -B -Pdoc javadoc:javadoc'

    stage 'Sonar'
    sh 'mvn -B sonar:sonar -Dsonar.host.url=http://${env.SONAR_URL}'

}
