node('graphviz') {
    env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
    env.JAVA_HOME = "${tool 'Java8'}"

    stage 'Build and Test'
    checkout scm
    sh 'mvn -B -Pcoverage clean verify package'

    stage 'Doc'
    sh 'mvn -B -Pdoc javadoc:javadoc'

    stage 'Sonar'
    sh 'mvn -B sonar:sonar -Dsonar.host.url=$SONAR_URL'

}
